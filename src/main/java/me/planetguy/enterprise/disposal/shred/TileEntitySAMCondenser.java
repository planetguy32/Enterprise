package me.planetguy.enterprise.disposal.shred;

import me.planetguy.enterprise.disposal.EnterpriseDisposal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileEntitySAMCondenser extends TileEntityBase {
	
	int workDone=0;
	public static final int maxWorkDone=64;
	public static final int energyUsedPerWork=3000;
	
	public void updateEntity() {
		if(storedEnergy>=energyUsedPerWork && tank.drain(1, false)!=null){
			storedEnergy-=energyUsedPerWork;
			tank.drain(1, true);
			workDone++;
			if(workDone>maxWorkDone){
				addSAM();
				workDone=0;
			}
		}else{
			workDone=0;
		}
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
