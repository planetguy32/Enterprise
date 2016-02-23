package me.planetguy.ore.content.lavawell;


public class TileEntityHeatSink extends TEThermal {

	@Override
	public int getMass() {
		return BLMBalance.MASS_HEAT_SINK;
	}

	@Override
	public float getFractionRadiated() {
		return BLMBalance.RADIATION_HEAT_SINK;
	}

	@Override
	public int meltingTime() {
		return BLMBalance.MTIME_HEAT_SINK;
	}

	@Override
	public int meltingTemp() {
		return BLMBalance.MTEMP_HEAT_SINK;
	}

	@Override
	public float getHeatAvailable() {
		return BLMBalance.AVAILABLE_HEAT_SINK;
	}

}
