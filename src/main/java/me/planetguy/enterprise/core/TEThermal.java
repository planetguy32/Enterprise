package me.planetguy.enterprise.core;

import me.planetguy.ore.content.ODTContentPlugin;
import me.planetguy.ore.content.lavawell.BlockPassive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TEThermal extends TileEntity implements IHeatConductive,IFluidHandler {
	
	public int heat=295;
	public int timeOverMeltingPoint;
	
	public void updateEntity(){
		for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS){
			TileEntity nearbyTE=worldObj.getTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ);
			if(nearbyTE instanceof IHeatConductive){
				IHeatConductive thermalTE=(IHeatConductive) nearbyTE;
				int heats=redistributeHeat(this.getMass(), this.getHeat(), thermalTE.getMass(), thermalTE.getHeat());
				this.heat=heats;
				thermalTE.setHeat(heats);
			}
		}
		heat=Math.max(295, heat-this.getHeatRadiated());
		if(this.heat>meltingTemp())
			timeOverMeltingPoint++;
		else
			timeOverMeltingPoint=Math.max(timeOverMeltingPoint-1, 0);
		if(timeOverMeltingPoint>meltingTime()){
			worldObj.setBlock(xCoord, yCoord, zCoord, BlockPassive.instance, 2, 0x02);
		}
	}
	
	public static int redistributeHeat(int mass, int heat, int mass2, int heat2){
		int totalMass=mass2+mass;
		double totalHeat=heat*mass+heat2*mass2;
		double heatPerUnitMass=totalHeat/totalMass;
		return (int) Math.ceil(heatPerUnitMass);
	}
	
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		tag.setInteger("heat", heat);
		
	}
	
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		heat=tag.getInteger("heat");
	}

	@Override
	public int getHeat() {
		return heat;
	}

	@Override
	public void setHeat(int heat) {
		this.heat=heat;
	}
	
	public abstract int getHeatRadiated();
	
	public abstract int meltingTime();
	public abstract int meltingTemp();

	
	//allow players to splash with water at any time
	public boolean playerRightClick(EntityPlayer player) {
		ItemStack i=player.getHeldItem();
		if(i!=null &&i.getItem()==Items.water_bucket){
			i.damageItem(1, player);
			this.setHeat(redistributeHeat(this.getMass(), this.getHeat(), 1000, 295));
			return true;
		}
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
			heat=TEThermal.redistributeHeat(this.getMass(), this.heat, resource.amount, ftemp);
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
		return fluid.getTemperature()<heat;
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
