package me.planetguy.ore.content.lavawell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import me.planetguy.lib.util.Debug;
import me.planetguy.ore.content.ODTContentPlugin;
import me.planetguy.ore.gen.BlockMeta;
import me.planetguy.ore.gen.ODTGen;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLavaPuddle extends TEThermal{

	public static final int MAXHEAT=10000;

	public static final int BREAK_HEAT=5000;
	
	public static final int FREEZING_POINT=3000;

	
	public TileEntityLavaPuddle(){
		super(MAXHEAT);
	}

	public void updateEntity(){
		super.updateEntity();
		if(Math.random()<0.05)
			doBreaking();
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
				heat--;
			}else if(worldObj.getTileEntity(x,y,z)!=null){
				worldObj.setBlock(x, y, z, ODTContentPlugin.tileEntity, 6, 2);//burned block
			}else if(canReplace(x,y,z))
				worldObj.setBlock(x, y, z, Blocks.flowing_lava, 2, 0x02);
	}

	public void extrudeLava(int oldMax){
		try{
			int leastHeat=Math.min(oldMax, (int)(heat/1000));
			heat += (oldMax-leastHeat);
			int max=leastHeat + ((int)heat) % MAXHEAT;
			if(max<=0)
				return;
			if(canReplace(xCoord, yCoord-1, zCoord)){
				worldObj.setBlock(xCoord, yCoord-1, zCoord, ODTContentPlugin.tileEntity, 2, 0x03);
				((TileEntityLavaPuddle)worldObj.getTileEntity(xCoord, yCoord-1, zCoord)).extrudeLava(max);
				return;
			}
			Collections.shuffle(sides);
			for(ForgeDirection fd:sides){
				if(canReplace(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ)){
					worldObj.setBlock(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ, ODTContentPlugin.tileEntity, 2, 0x03);
					((TileEntityLavaPuddle)worldObj.getTileEntity(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ)).extrudeLava(max-1);
					return;
				}
			}
			if(canReplace(xCoord, yCoord+1, zCoord)){
				worldObj.setBlock(xCoord, yCoord+1, zCoord, ODTContentPlugin.tileEntity, 2, 0x03);
				((TileEntityLavaPuddle)worldObj.getTileEntity(xCoord, yCoord+1, zCoord)).extrudeLava(max-2);
				return;
			}
			Collections.shuffle(sides);
			ForgeDirection fd=(ForgeDirection) sides.get(0);
			TileEntity te=worldObj.getTileEntity(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ);
			if(te instanceof TileEntityLavaPuddle){
				((TileEntityLavaPuddle) te).extrudeLava(max-2);
			}
		}catch(NullPointerException e){}
	}
	
	public boolean canReplace(int x, int y, int z){
		return !heatProof.contains(worldObj.getBlock(x, y, z));
	}


	private static final HashSet<Block> heatProof=new HashSet<Block>();
	private static final ArrayList<ForgeDirection> sides=new ArrayList<ForgeDirection>();

	static{
		sides.addAll(Arrays.asList(new ForgeDirection[]{
				ForgeDirection.NORTH,
				ForgeDirection.SOUTH,
				ForgeDirection.EAST,
				ForgeDirection.WEST
		}));
		for(Block b:new Block[]{
				Blocks.stone,
				Blocks.cobblestone,
				Blocks.gravel,
				Blocks.sand,
				Blocks.sandstone,

				ODTContentPlugin.tileEntity,
				Blocks.bedrock
		})
			heatProof.add(b);
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
