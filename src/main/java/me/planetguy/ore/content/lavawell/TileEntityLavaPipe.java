package me.planetguy.ore.content.lavawell;

public class TileEntityLavaPipe extends TEThermal {

	@Override
	public int getMass() {
		return BLMBalance.MASS_LAVA_PIPE;
	}

	@Override
	public float getRadiationToEnvironment() {
		return BLMBalance.RADIATION_LAVA_PIPE;
	}

	@Override
	public int meltingTime() {
		return BLMBalance.MTIME_LAVA_PIPE;
	}

	@Override
	public int meltingTemp() {
		return BLMBalance.MTEMP_LAVA_PIPE;
	}

	@Override
	public float getHeatAvailable() {
		return BLMBalance.AVAILABLE_LAVA_PIPE;
	}
	

}
