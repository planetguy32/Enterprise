package me.planetguy.ore.content.lavawell;

import me.planetguy.lib.util.Debug;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TEThermal extends TileEntity implements IFluidHandler {
	
	//Heat energy units
	public float thermalEnergy = getMass() * 300;
	
	//Heat energy units per degree temperature
	public float thermalMass = getMass();
	public int timeOverMeltingPoint;
	
	public TEThermal(){
		this.thermalMass=getMass();
	}
	
	public TEThermal(float heat){
		this();
		this.thermalEnergy = heat;
	}
	
	public strictfp void updateEntity(){
		if(worldObj.isRemote)
			return;
		// 1/6 of the heat*mass units, times the available heat
		for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS){
			TileEntity nearbyTE=worldObj.getTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ);
			if(nearbyTE instanceof TEThermal){
				TEThermal thermalTE=(TEThermal) nearbyTE;
				float deltaTemperature = thermalTE.getTemperature() - getTemperature();
				//Only handle they're hotter than us - chosen arbitrarily
				if(deltaTemperature < 0)
					continue;
				float tuToTransfer = deltaTemperature * getHeatAvailable() * thermalTE.getHeatAvailable();
				float averageTemperature = (this.thermalEnergy + thermalTE.thermalEnergy) / (this.thermalMass + thermalTE.thermalMass);
				float maxTransferred = averageTemperature * thermalTE.thermalMass 
						- averageTemperature * this.thermalMass;
				
				float actualTransferred = Math.max(0,Math.min(maxTransferred, maxTransferred * tuToTransfer));
				
				thermalEnergy += actualTransferred;
				thermalTE.thermalEnergy -= actualTransferred;
				
				continue;
			}
			float energyAbove300 = thermalEnergy - thermalMass*300;
			thermalEnergy -= getRadiationToEnvironment() * energyAbove300;
		}
		boolean overheating = getTemperature() > meltingTemp();
		if(overheating)
			timeOverMeltingPoint++;//=this.heat - meltingTemp();
		else
			timeOverMeltingPoint=Math.max(timeOverMeltingPoint-1, 0);
		if(timeOverMeltingPoint>meltingTime()){
			melt();
		}
	}
	
	public float getTemperature() {
		return thermalEnergy / thermalMass;
	}
	
	public void melt() {
		worldObj.setBlock(xCoord, yCoord, zCoord, BlockLavaMachinery.instance, 8, 0x03);
		worldObj.setTileEntity(xCoord, yCoord, zCoord, new TileEntityBurntBlock(thermalEnergy));
	}

	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setFloat("heat", thermalEnergy);
	}
	
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		thermalEnergy=tag.getFloat("heat");
	}

	public abstract float getRadiationToEnvironment();
	public abstract float getHeatAvailable();
	public abstract int getMass();
	
	public abstract int meltingTime();
	public abstract int meltingTemp();

	
	//allow players to splash with water at any time
	public boolean playerRightClick(EntityPlayer player) {
		return false;
	}
	
	public int getOverheatLevel(){
		return timeOverMeltingPoint;
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		Fluid f=resource.getFluid();
		int ftemp=f.getTemperature(resource);
		int num=resource.amount;
		if(doFill){
			
		}
		return resource.amount;
	}

	public boolean isUnpowered(){
		for(int i=0; i<6; i++){
			if(worldObj.getIndirectPowerLevelTo(xCoord, yCoord, zCoord, i)!=0)
				return false;
		}
		return true;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid.getTemperature()<getTemperature();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[0];//no tanks
	}
	
}
