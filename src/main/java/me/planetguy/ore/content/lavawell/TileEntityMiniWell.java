package me.planetguy.ore.content.lavawell;

import me.planetguy.ore.content.ODTContentPlugin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMiniWell extends TEThermal {
	
	public int heat=295;
	
	public int timeOverMeltingPoint=0;
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)
			return;
		TileEntity lavaPuddle=worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
		if(!(lavaPuddle instanceof TileEntityLavaPuddle)){
			if(		worldObj.isAirBlock(xCoord, yCoord+1, zCoord)
					&&this.isCorrectlyPlaced()
					&&this.isUnpowered()
					)
				worldObj.setBlock(xCoord, yCoord+1, zCoord, ODTContentPlugin.tileEntity, 2, 0x02);
		}else{
			((TileEntityLavaPuddle)lavaPuddle).extrudeLava(5);
		}
	}
	
	private boolean isCorrectlyPlaced(){
		int posY=this.yCoord;
		while(posY>0){
			posY--;
			Block block=worldObj.getBlock(xCoord, posY, zCoord);
			int meta=worldObj.getBlockMetadata(xCoord, posY, zCoord);
			if( (block!=BlockLavaMachinery.instance && meta!=8)
					&& block != Blocks.bedrock){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int getMass() {
		return BLMBalance.MASS_MINI_WELL;
	}

	@Override
	public float getRadiationToEnvironment() {
		return BLMBalance.RADIATION_MINI_WELL;
	}

	@Override
	public int meltingTime() {
		return BLMBalance.MTIME_MINI_WELL;
	}

	@Override
	public int meltingTemp() {
		return BLMBalance.MTEMP_MINI_WELL;
	}

	@Override
	public float getHeatAvailable() {
		return BLMBalance.AVAILABLE_MINI_WELL;
	}

}
