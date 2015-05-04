package me.planetguy.enterprise.grid;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;

public class PowerGrid2dRegistry {
	
	private static List<IPowerGridProvider> providers=new ArrayList<IPowerGridProvider>();
	
	public static IPowerGrid2dMember getGrid(TileEntity te){
		return null;
	}
	
	public static void registerHandler(IPowerGridProvider prov){
		providers.add(prov);
	}

	public static IPowerGrid2dMember getBinding(TileEntity te){
		if(te instanceof IPowerGrid2dMember){
			return (IPowerGrid2dMember) te;
		}else for(IPowerGridProvider prov:providers){
			IPowerGrid2dMember m=prov.get(te);
			if(m!=null)
				return m;
		}
		return null;
	}

}
