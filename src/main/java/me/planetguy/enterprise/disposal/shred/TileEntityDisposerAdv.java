package me.planetguy.enterprise.disposal.shred;

import me.planetguy.enterprise.disposal.EnterpriseDisposal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityDisposerAdv extends TileEntityDisposer {
	
	//Here this means speed
	//protected int tileState;
	
	public static int maxSpeed=650;
	
	public static int speedPerOperation=10;
	
    public void updateEntity() {
    	boolean isStuck=false;
    	if(inProgressStack!=null){
    		if(speedPerOperation*inProgressStack.stackSize>tileState){
    			int fullyPossibleOperations=tileState/speedPerOperation;
    			tileState=0; //speed
    			isStuck=true;
    			inProgressStack.stackSize-=fullyPossibleOperations;
    			tank.fill(new FluidStack(EnterpriseDisposal.qgp, fullyPossibleOperations), true);
    		}else{
    			int size=inProgressStack.stackSize;
    			inProgressStack=null;
    			tileState-=speedPerOperation*size;
    			tank.fill(new FluidStack(EnterpriseDisposal.qgp, size), true);;
    		}
    		
    	}
    	if(storedEnergy>=TileEntityDisposer.ENERGY_PER_TICK/2 && !isStuck){
    		if(tileState<maxSpeed)
    			tileState++;
    		storedEnergy-=TileEntityDisposer.ENERGY_PER_TICK/2;
    	}else if(!isStuck && tileState>0){
    		tileState--;
    	}
	}
    
	public boolean onInteract(EntityPlayer p){
		p.openGui(EnterpriseDisposal.instance, 1, worldObj, xCoord, yCoord, zCoord);
		return true;
	}

}
