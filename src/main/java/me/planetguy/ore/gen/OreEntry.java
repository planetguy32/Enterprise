package me.planetguy.ore.gen;

class OreEntry{

	public final double mineDensity,mineFrequency;
	public final int floor, ceiling;

	public OreEntry(double density, double frequency, int floor, int ceiling){
		mineDensity=density;
		mineFrequency=frequency;
		this.floor=floor;
		this.ceiling=ceiling;
	}

	public OreEntry(String cfgProp){
		String[] props=cfgProp.split("% of stone, in ");
		mineDensity=Double.parseDouble(props[0])/100.0;
		
		cfgProp=props[1];
		props = cfgProp.split("% of chunks, layers ");
		mineFrequency=Double.parseDouble(props[0])/100.0;
		
		cfgProp = props[1];
		props=cfgProp.split(" to ");
		
		floor = Integer.parseInt(props[0]);
		ceiling=Integer.parseInt(props[1]);
	}
	
	public String toString(){
		return mineDensity + " * "+mineFrequency;
	}

}