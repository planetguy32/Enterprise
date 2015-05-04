package me.planetguy.ore.content.lavawell;

import cpw.mods.fml.common.registry.GameRegistry;
import me.planetguy.enterprise.core.TEThermal;
import me.planetguy.ore.content.ODTContentPlugin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityMiniWell extends TEThermal {
	
	public int heat=295;
	
	public int timeOverMeltingPoint=0;
	
	public static final int MASS=5000;
	
	public static final int meltingPoint=2000;
	
	public static final int meltingTime=300;
	
	public static final int coolingPerTick=1;
	
	public void updateEntity(){
		super.updateEntity();
		TileEntity lavaPuddle=worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
		if(!(lavaPuddle instanceof TileEntityLavaPuddle)){
			if(		worldObj.isAirBlock(xCoord, yCoord+1, zCoord)
					&&this.isCorrectlyPlaced()
					&&this.isUnpowered()
					)
				worldObj.setBlock(xCoord, yCoord+1, zCoord, ODTContentPlugin.tileEntity, 2, 0x02);
		}else{
			((TileEntityLavaPuddle)lavaPuddle).extrudeLava(30);//heat/500);
		}
	}
	
	private boolean isCorrectlyPlaced(){
		int posY=this.yCoord;
		while(posY>0){
			posY--;
			Block block=worldObj.getBlock(xCoord, posY, zCoord);
			int meta=worldObj.getBlockMetadata(xCoord, posY, zCoord);
			if( (block!=BlockPassive.instance && meta!=1) //BlockPassive
					&& block != Blocks.bedrock){ //Bedrock
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int getMass() {
		return MASS;
	}

	@Override
	public int meltingTime() {
		return this.meltingTime;
	}

	@Override
	public int meltingTemp() {
		return meltingPoint;
	}

	@Override
	public int getHeatRadiated() {
		return 1;
	}

}
