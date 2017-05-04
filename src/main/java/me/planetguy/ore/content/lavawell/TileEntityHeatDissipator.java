package me.planetguy.ore.content.lavawell;

public class TileEntityHeatDissipator extends TEThermal {
	
	@Override
	public int getMass() {
		return BLMBalance.MASS_HEAT_DISSIPATOR;
	}

	@Override
	public float getRadiationToEnvironment() {
		return BLMBalance.RADIATION_HEAT_DISSIPATOR;
	}

	@Override
	public int meltingTime() {
		return BLMBalance.MTIME_HEAT_DISSIPATOR;
	}

	@Override
	public int meltingTemp() {
		return BLMBalance.MTEMP_HEAT_DISSIPATOR;
	}

	@Override
	public float getHeatAvailable() {
		return BLMBalance.AVAILABLE_HEAT_DISSIPATOR;
	}
	
}
