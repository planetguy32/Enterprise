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
	
	public static final int MASS=2000;
	
	public static final int MAXHEAT=10000;
	
	public static final int BREAKHEAT=1000;
	
	public boolean isHot=false;
	
	public void updateEntity(){
		super.updateEntity();
		if(Math.random()<0.05)
			doBreaking();
	}
	
	public void doBreaking(){
		if(heat>BREAKHEAT){
			for(int dx=-1; dx<=1; dx++){
				for(int dy=-1; dy<=2; dy++){
					for(int dz=-1; dz<=1; dz++){
						if(!(dx==0&&dy==0&&dz==0)) //if not in its own place
							doBlockDisruption(xCoord+dx, yCoord+dy, zCoord+dz);
					}
				}
			}
		}else{
			BlockMeta imp=ODTGen.getOre(worldObj, xCoord/16, zCoord/16);
			if(imp!=null)
				worldObj.setBlock(xCoord, yCoord, zCoord, imp.block, imp.meta, 0x02);
			else
				worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.stone, 0, 0x02);
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
				worldObj.setBlock(x, y, z, ODTContentPlugin.deco, 2, 2);//burned block
			}else if(canReplace(x,y,z))
				worldObj.setBlock(x, y, z, Blocks.flowing_lava, 2, 0x02);
	}

	public void validate(){ //dirty hack to make it get hot
		if(!isHot)
			this.heat=10000;
	}

	@Override
	public int meltingTime() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int meltingTemp() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMass() {
		return MASS;
	}

	@Override
	public int getHeatRadiated() {
		return 1;
	}
	
	public void extrudeLava(int max){
		if(!worldObj.isRemote)
			Debug.dbg("");
		if(max<=0)
			return;
		if(canReplace(xCoord, yCoord-1, zCoord)){
			worldObj.setBlock(xCoord, yCoord-1, zCoord, ODTContentPlugin.tileEntity, 2, 0x02);
			return;
		}
		Collections.shuffle(sides);
		for(ForgeDirection fd:sides){
			if(canReplace(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ)){
				worldObj.setBlock(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ, ODTContentPlugin.tileEntity, 2, 0x02);
				return;
			}
		}
		if(canReplace(xCoord, yCoord+1, zCoord)){
			worldObj.setBlock(xCoord, yCoord+1, zCoord, ODTContentPlugin.tileEntity, 2, 0x02);
			return;
		}
		List c=Arrays.asList(ForgeDirection.VALID_DIRECTIONS);
		Collections.shuffle(c);
		ForgeDirection fd=(ForgeDirection) c.get(0);
		TileEntity te=worldObj.getTileEntity(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ);
		if(te instanceof TileEntityLavaPuddle){
			((TileEntityLavaPuddle) te).extrudeLava(max-1);
		}

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
				
				ODTContentPlugin.deco,
				ODTContentPlugin.tileEntity,
		})
		heatProof.add(b);
	}
	
	private void selectDirection(){
		
	}
}
