package me.planetguy.enterprise.grid;

import java.util.HashMap;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class PowerGridProviderRF implements IPowerGridProvider {

	public static HashMap<Class, Restriction> restrictionMap=new HashMap<Class, Restriction>();
	
	private static class Restriction{
		public int volts, amps;
	}
	
	public static void restrict(Class<? extends TileEntity> target, int maxVolts, int maxAmps){
		Restriction r=new Restriction();
		r.volts=maxVolts;
		r.amps=maxAmps;
		restrictionMap.put(target, r);
	}
	
	@Override
	public IPowerGrid2dMember get(TileEntity teGeneric) {
		if(teGeneric instanceof IEnergyReceiver){
			final IEnergyReceiver te=(IEnergyReceiver) teGeneric;
			return new IPowerGrid2dMember(){

				@Override
				public boolean canConnectEnergy(ForgeDirection dir) {
					return te.canConnectEnergy(dir);
				}

				@Override
				public boolean onTransport(int volts, int amps) {
					try{
						//overload
						if(te.receiveEnergy(ForgeDirection.NORTH, volts*amps, true)<volts*amps){
							return true;
						}else{
							te.receiveEnergy(ForgeDirection.NORTH, volts*amps, false);
						}
					}catch(NullPointerException ignored){
						
					}
					return false;
				}

				@Override
				public boolean isDestination() {
					return true;
				}
				
			};
		}
		return null;
	}

}
