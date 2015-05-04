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
					return false;
				}

				@Override
				public int getMaximumCurrent(int volts) {
					return (te.getMaxEnergyStored(ForgeDirection.DOWN)-te.getEnergyStored(ForgeDirection.DOWN))/volts;
				}

				@Override
				public int getPowerAmountToConsume() {
					return te.receiveEnergy(ForgeDirection.DOWN, Integer.MAX_VALUE, true);
				}

			};
		}
		return null;
	}

}
