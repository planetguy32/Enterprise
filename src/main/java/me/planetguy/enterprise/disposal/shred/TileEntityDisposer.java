package me.planetguy.enterprise.disposal.shred;

import me.planetguy.enterprise.disposal.EnterpriseDisposal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityDisposer extends TileEntityBase {
	
	//Here this means progress
	protected int tileState;
	
	public int maxProgress=100;
	
	public static int ENERGY_PER_TICK=160;
		
    public void updateEntity() {
    	if(inProgressStack!=null){
    		if(storedEnergy>=ENERGY_PER_TICK){
    			storedEnergy-=ENERGY_PER_TICK;
    			tileState++;
    		}else{
    			tileState=0;
    		}
    		if(tileState>=maxProgress){
    			tileState=0;
    			tank.fill(new FluidStack(EnterpriseDisposal.qgp, 1), true);
    			if(inProgressStack.stackSize<=1)
    				inProgressStack=null;
    			else
    				inProgressStack.stackSize--;
    		}
    	}
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return ENERGY_PER_TICK*maxProgress/10;
	}
	
	public boolean onInteract(EntityPlayer p){
		p.openGui(EnterpriseDisposal.instance, 0, worldObj, xCoord, yCoord, zCoord);
		return true;
	}
	
}
