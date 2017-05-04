package me.planetguy.ore.content.lavawell;

public class TileEntityGraphite extends TEThermal {
	
	@Override
	public int getMass() {
		return BLMBalance.MASS_GRAPHITE;
	}

	@Override
	public float getRadiationToEnvironment() {
		return BLMBalance.RADIATION_GRAPHITE;
	}

	@Override
	public int meltingTime() {
		return BLMBalance.MTIME_GRAPHITE;
	}

	@Override
	public int meltingTemp() {
		return BLMBalance.MTEMP_GRAPHITE;
	}

	@Override
	public float getHeatAvailable() {
		return BLMBalance.AVAILABLE_GRAPHITE;
	}

}
