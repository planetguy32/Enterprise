package me.planetguy.ore.content.lavawell;

public class BLMBalance {

	public static final float AVAILABLE_BURNT_BLOCK = 0.05f;
	public static final float AVAILABLE_CRYO_PUMP = 0.2f;
	public static final float AVAILABLE_GRAPHITE = 0.3f;
	public static final float AVAILABLE_HEAT_DISSIPATOR = 0.2f;
	public static final float AVAILABLE_HEAT_SINK = 0.5f;
	public static final float AVAILABLE_LAVA_PIPE = 0.2f;
	public static final float AVAILABLE_LAVA_POOL = 0.5f;
	public static final float AVAILABLE_MINI_WELL = 0.7f;
	
	public static final float RADIATION_BURNT_BLOCK = 0.0001f;
	public static final float RADIATION_CRYO_PUMP = 0.0008f / 1000f; //this is per-RF
	public static final float RADIATION_GRAPHITE = 0.0001f;
	public static final float RADIATION_HEAT_DISSIPATOR = 0.0005f;
	public static final float RADIATION_HEAT_SINK = 0.0002f;
	public static final float RADIATION_LAVA_PIPE = 0.0001f;
	public static final float RADIATION_LAVA_POOL = 0.0001f;
	public static final float RADIATION_MINI_WELL = 0.00015f;
	
	public static final int MASS_BURNT_BLOCK = 900;
	public static final int MASS_CRYO_PUMP = 1000;
	public static final int MASS_GRAPHITE = 1000;
	public static final int MASS_HEAT_DISSIPATOR = 1000;
	public static final int MASS_HEAT_SINK = 2500;
	public static final int MASS_LAVA_PIPE = 1200;
	public static final int MASS_LAVA_POOL = 1000;
	public static final int MASS_MINI_WELL = 2000;
	
	public static final int MTEMP_BURNT_BLOCK = 8000;
	public static final int MTEMP_CRYO_PUMP = 2000;
	public static final int MTEMP_GRAPHITE = 6000;
	public static final int MTEMP_HEAT_DISSIPATOR = 3000;
	public static final int MTEMP_HEAT_SINK = 3000;
	public static final int MTEMP_LAVA_PIPE = 3000;
	public static final int MTEMP_LAVA_POOL = Integer.MAX_VALUE;
	public static final int MTEMP_MINI_WELL = 4000;

	public static final int MTIME_BURNT_BLOCK = 1200;
	public static final int MTIME_CRYO_PUMP = 100;
	public static final int MTIME_GRAPHITE = 200;
	public static final int MTIME_HEAT_DISSIPATOR = 100;
	public static final int MTIME_HEAT_SINK = 400;
	public static final int MTIME_LAVA_PIPE = 300;
	public static final int MTIME_LAVA_POOL = 300; //never relevant
	public static final int MTIME_MINI_WELL = 300;

}
/*Copy the next code...
@Override
public int getMass() {
	return BLMBalance.MASS_HEAT_SINK;
}

@Override
public int getHeatRadiated() {
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
*/

