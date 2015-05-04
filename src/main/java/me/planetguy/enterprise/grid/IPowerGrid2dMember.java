package me.planetguy.enterprise.grid;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPowerGrid2dMember {
	
	public boolean canConnectEnergy(ForgeDirection dir);
	
	/**
	 * Callback for when energy is transported to, from or across
	 * 
	 * @return true if something bad happens
	 */
	public boolean onTransport(int volts, int amps);
	
	public boolean isDestination();

}
