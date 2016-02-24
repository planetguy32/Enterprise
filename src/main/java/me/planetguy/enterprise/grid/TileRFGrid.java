package me.planetguy.enterprise.grid;

import cofh.api.energy.IEnergyReceiver;
import me.planetguy.ore.content.lavawell.TEThermal;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRFGrid extends TEThermal implements IEnergyReceiver, IPowerGrid2dMember {

	int meta=0;
	
	int voltsTransportedLastTick=0;
	
	public void updateEntity(){
		super.updateEntity();
		voltsTransportedLastTick=0;
	}
	
	public void setup(int meta){
		this.meta=meta;
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		if(!simulate){
			postEnergy1d(maxReceive);
		}
		return maxReceive;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return Integer.MAX_VALUE;
	}
	
	public void postEnergy1d(int energy){
		int vecEnergy=(int) Math.sqrt(energy);
		Search.dispatchEnergy(worldObj, xCoord, yCoord, zCoord, vecEnergy, vecEnergy);
	}

	@Override
	public int getPowerAmountToConsume() {
		return 0;
	}

	@Override
	public boolean onTransport(int volts, int amps) {
		voltsTransportedLastTick+=volts;
		if(voltsTransportedLastTick>meta*30){
			return true;
		}
		heat+=amps/30;
		return false;
	}

	@Override
	public int getMass() {
		return 3000;
	}

	@Override
	public float getFractionRadiated() {
		return 0;
	}

	@Override
	public int meltingTime() {
		return 300;
	}

	@Override
	public int meltingTemp() {
		return 600;
	}

	@Override
	public int getMaximumCurrent(int volts) {
		return 0;
	}

	@Override
	public float getHeatAvailable() {
		// TODO Auto-generated method stub
		return 0.5f;
	}

}
