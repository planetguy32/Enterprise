package me.planetguy.ore.content.lavawell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import me.planetguy.ore.content.ODTContentPlugin;
import me.planetguy.ore.gen.BlockMeta;
import me.planetguy.ore.gen.ODTGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLavaPuddle extends TEThermal{

	public static final int MAXHEAT=10000;

	public static final int BREAK_HEAT=5000;

	public static final int FREEZING_POINT=3000;

	private static class PossibleExpandPlace implements Comparable<PossibleExpandPlace>{
		public ChunkCoordinates coords;
		public int resistance;
		@Override
		public int compareTo(PossibleExpandPlace arg0) {
			return resistance-arg0.resistance;
		}

	}

	private static PossibleExpandPlace[] paths=new PossibleExpandPlace[6];
	static{
		for(int i=0; i<paths.length; i++){
			paths[i]=new PossibleExpandPlace();
		}
	}

	public TileEntityLavaPuddle(){
		super(MAXHEAT);
	}

	public void updateEntity(){
		super.updateEntity();
		if(Math.random()<0.05) {
			doBreaking();
			heat--;
		}
	}

	public void melt(){
		//Lava doesn't melt. Just... No.
	}

	public void doBreaking(){
		if(heat>BREAK_HEAT){
			for(int dx=-1; dx<=1; dx++){
				for(int dy=-1; dy<=2; dy++){
					for(int dz=-1; dz<=1; dz++){
						if(!(dx==0&&dy==0&&dz==0)) //if not in its own place
							doBlockDisruption(xCoord+dx, yCoord+dy, zCoord+dz);
					}
				}
			}
		}else if(heat<FREEZING_POINT){
			BlockMeta imp=ODTGen.getOre(worldObj, xCoord/16, zCoord/16);
			if(imp!=null)
				worldObj.setBlock(xCoord, yCoord, zCoord, imp.block, imp.meta, 0x03);
			else
				worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.stone, 0, 0x03);
			for(int dx=-1; dx<=1; dx++){
				for(int dy=-1; dy<=2; dy++){
					for(int dz=-1; dz<=1; dz++){
						if(!(dx==0&&dy==0&&dz==0)) //if not in its own place
							coolNeighbour(xCoord+dx, yCoord+dy, zCoord+dz);
					}
				}
			}
		}
	}

	private void coolNeighbour(int x, int y, int z) {
		Block b=worldObj.getBlock(x, y, z);
		if(b==Blocks.cobblestone){
			worldObj.setBlock(x, y, z, Blocks.stone);
		}else if(b==Blocks.flowing_lava 
				|| b==Blocks.lava
				|| b==Blocks.gravel
				|| b==Blocks.sand
				|| b==Blocks.air){
			worldObj.setBlock(x,y,z,Blocks.cobblestone);
		}
	}

	public void doBlockDisruption(int x, int y, int z){
		Block id=worldObj.getBlock(x, y, z);
		if(worldObj.getBlock(x, y, z).getMaterial()!=ODTContentPlugin.graphite) //don't damage graphite
			if(id==Blocks.stone)
				worldObj.setBlock(x, y, z, Blocks.cobblestone);
			else if(id==Blocks.cobblestone)
				worldObj.setBlock(x, y, z, Blocks.gravel);
			else if(id==Blocks.dirt)
				worldObj.setBlock(x, y, z, Blocks.sand);
			else if(id==Blocks.stonebrick)
				worldObj.setBlockMetadataWithNotify(x, y, z, 2, 0x02);
			else if(id==Blocks.flowing_water || id==Blocks.water){
				worldObj.setBlock(x, y, z, Blocks.air);
				heat-=10;
			}else if(worldObj.getTileEntity(x,y,z)!=null){
				worldObj.setBlock(x, y, z, ODTContentPlugin.tileEntity, 6, 2);//burned block
			}else {
				int heatResistance=heatResistance(x,y,z);
				if(heatResistance*3000 < heat)
					worldObj.setBlock(x, y, z, Blocks.flowing_lava, 2, 0x02);
				heat-=heatResistance;
			}
	}

	public void extrudeLava(int oldMax){
		int leastHeat=Math.min(oldMax, (int)(heat/1000));
		heat += (oldMax-leastHeat);
		int max=leastHeat + ((int)heat) % MAXHEAT;
		if(max<=0)
			return;
		paths[0].coords=new ChunkCoordinates(xCoord, yCoord-1, zCoord);
		paths[1].coords=new ChunkCoordinates(xCoord, yCoord+1, zCoord);
		paths[2].coords=new ChunkCoordinates(xCoord, yCoord, zCoord+1);
		paths[3].coords=new ChunkCoordinates(xCoord, yCoord, zCoord-1);
		paths[4].coords=new ChunkCoordinates(xCoord+1, yCoord, zCoord);
		paths[5].coords=new ChunkCoordinates(xCoord-1, yCoord, zCoord);

		for(PossibleExpandPlace e:paths)
			addHeatResistance(e);
		
		paths[0].resistance--;
		paths[1].resistance++;

		Collections.shuffle(Arrays.asList(paths));
		Arrays.sort(paths);

		ChunkCoordinates coords=paths[0].coords;
		worldObj.setBlock(coords.posX, coords.posY, coords.posZ, ODTContentPlugin.tileEntity, 2, 0x03);
		((TileEntityLavaPuddle)worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ))
			.extrudeLava(max-paths[0].resistance);
	}

	public void addHeatResistance(PossibleExpandPlace pep){
		pep.resistance=heatResistance(pep.coords.posX, pep.coords.posY, pep.coords.posZ);
	}

	public int heatResistance(int x, int y, int z){
		Block b=worldObj.getBlock(x, y, z);
		if(b==BlockLavaMachinery.instance
				|| b.getExplosionResistance(null) > 10000
				|| b.getBlockHardness(worldObj, x, y, z) > 10000)
			return Integer.MAX_VALUE;
		if(b.isAir(worldObj, x, y, z))
			return 0;
		if(melty.contains(worldObj.getBlock(x, y, z)))
			return 4;
		if(Blocks.fire.getFlammability(b) != 0)
			return 3;
		return 2;
	}


	private static final HashSet<Block> melty=new HashSet<Block>();

	static{
		for(Block b:new Block[]{
				Blocks.stone,
				Blocks.cobblestone,
				Blocks.gravel,
				Blocks.sand,
				Blocks.sandstone,
				Blocks.dirt,
				Blocks.clay,
				Blocks.hardened_clay,
				Blocks.stained_hardened_clay,
				Blocks.glass,
				Blocks.water,
				Blocks.flowing_water,
				Blocks.flowing_lava,
		})
			melty.add(b);
	}

	@Override
	public int getMass() {
		return BLMBalance.MASS_LAVA_POOL;
	}

	@Override
	public float getFractionRadiated() {
		return BLMBalance.RADIATION_LAVA_POOL;
	}

	@Override
	public int meltingTime() {
		return BLMBalance.MTIME_LAVA_POOL;
	}

	@Override
	public int meltingTemp() {
		return BLMBalance.MTEMP_LAVA_POOL;
	}

	@Override
	public float getHeatAvailable() {
		return BLMBalance.AVAILABLE_LAVA_POOL;
	}

}
