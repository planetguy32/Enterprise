package me.planetguy.enterprise.grid;

import net.minecraft.tileentity.TileEntity;

public interface IPowerGridProvider {
	
	public IPowerGrid2dMember get(TileEntity te);

}
