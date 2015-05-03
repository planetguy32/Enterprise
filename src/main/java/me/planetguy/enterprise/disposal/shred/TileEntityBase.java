package me.planetguy.enterprise.disposal.shred;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TileEntityBase extends TileEntity implements ISidedInventory, IFluidHandler, IEnergyHandler {
	
	protected ItemStack inProgressStack;
	
	protected FluidTank tank=new FluidTank(500);
	protected int storedEnergy;
	
	@Override
	public int getSizeInventory() {
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		return inProgressStack;
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int size) {
		if(inProgressStack==null)
			return null;
		int i=Math.min(size, inProgressStack.stackSize);
		if(i==inProgressStack.stackSize){
			ItemStack s=inProgressStack;
			inProgressStack=null;
			return s;
		}else{
			return inProgressStack.splitStack(size);
		}
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack s) {
		if(ItemStack.areItemStacksEqual(inProgressStack, s) && inProgressStack != null && s != null)
			inProgressStack.stackSize+=s.stackSize;
		else
			inProgressStack=s;
	}
	
	@Override
	public String getInventoryName() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void markDirty() {
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return false;
	}
	
	@Override
	public void openInventory() {
	}
	
	@Override
	public void closeInventory() {
	}
	
	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack stk) {
		return inProgressStack==null || ItemStack.areItemStacksEqual(stk, inProgressStack);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack fstk,
			boolean doDrain) {
		return tank.drain(10, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		int get=maxReceive;
		if(get+storedEnergy>getMaxEnergyStored(null))
			get=getMaxEnergyStored(null)-storedEnergy;
		if(!simulate)
			storedEnergy+=get;
		return get;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storedEnergy;
	}
	
	public boolean onInteract(EntityPlayer p){
		return false;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item,
			int size) {
		return inProgressStack==null || item==null || item.getItem() == inProgressStack.getItem();
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_) {
		return true;
	}

}
