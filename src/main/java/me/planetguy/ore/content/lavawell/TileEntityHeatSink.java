package me.planetguy.ore.content.lavawell;

public class TileEntityHeatSink extends TEThermal {

	@Override
	public int getMass() {
		return TileEntityMiniWell.MASS*2;
	}

	@Override
	public int getHeatRadiated() {
		return 0;
	}

	@Override
	public int meltingTime() {
		return TileEntityMiniWell.meltingTime;
	}

	@Override
	public int meltingTemp() {
		return TileEntityMiniWell.meltingPoint;
	}

}
