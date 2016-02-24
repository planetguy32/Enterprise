package me.planetguy.ore.gen;

class OreEntry{

	public final double mineDensity,mineFrequency;

	public OreEntry(double density, double frequency){
		mineDensity=density;
		mineFrequency=frequency;
	}

	public OreEntry(String cfgProp){
		String[] props=cfgProp.split("% of stone, in ");
		mineDensity=Double.parseDouble(props[0])/100.0;
		mineFrequency=Double.parseDouble(props[1].split("% of chunks")[0])/100.0;
	}

}