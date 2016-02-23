package me.planetguy.enterprise.disposal.shred;

import me.planetguy.enterprise.disposal.EnterpriseDisposal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileEntitySAMCondenser extends TileEntityBase {

	int workDone=0;

	public static final int maxWorkDone=64;

	//It takes 3000 RF/t to sustain this's operation - that's a lot! (intentionally)
	//Takes 192000 RF each, delivered all at once
	public static final int energyUsedPerWork=3000;
	//125*64=8000 MB of QGP per item
	public static final int fluidUsedPerWork=125;

	public TileEntitySAMCondenser(){
		tank=new FluidTank(maxWorkDone*fluidUsedPerWork);
	}

	public void updateEntity() {
		if(worldObj.isRemote)
			return;
		if(storedEnergy < energyUsedPerWork){
			workDone=0;
		} else{
			FluidStack stk=tank.drain(fluidUsedPerWork, false);
			if(stk!=null && stk.amount >= fluidUsedPerWork){
				workDone++;
				tank.drain(fluidUsedPerWork, true);
			}
			if(workDone>maxWorkDone){
				addSAM();
				workDone=0;
			}
		}
		storedEnergy=0;
	}

	public boolean addSAM(){
		if(inProgressStack==null){
			inProgressStack=new ItemStack(ItemSubatomicMatter.instance, 1);
		}else if(inProgressStack.stackSize<64){
			inProgressStack.stackSize++;
		}else{
			return false;
		}
		return true;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energyUsedPerWork*maxWorkDone/10;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	//Not allowed to put stuff in
	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack s) {}

	public boolean onInteract(EntityPlayer p){
		p.openGui(EnterpriseDisposal.instance, 0, worldObj, xCoord, yCoord, zCoord);
		return true;
	}

}
