package me.planetguy.ore.content.lavawell;

public class TileEntityBurntBlock extends TEThermal {

	public TileEntityBurntBlock(){
	}
	
	public TileEntityBurntBlock(float heat) {
		super(heat);
	}

	@Override
	public int getMass() {
		return BLMBalance.MASS_BURNT_BLOCK;
	}

	@Override
	public float getRadiationToEnvironment() {
		return BLMBalance.RADIATION_BURNT_BLOCK;
	}

	@Override
	public int meltingTime() {
		return BLMBalance.MTIME_BURNT_BLOCK;
	}

	@Override
	public int meltingTemp() {
		return BLMBalance.MTEMP_BURNT_BLOCK;
	}

	@Override
	public float getHeatAvailable() {
		return BLMBalance.AVAILABLE_BURNT_BLOCK;
	}

	
	public void melt() {
		worldObj.setBlockToAir(xCoord, yCoord, zCoord);
	}

}
