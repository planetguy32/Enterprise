package me.planetguy.ore.content.lavawell;

public class TileEntityHeatDissipator extends TileEntityHeatSink{
	
	public int meltingTime(){
		return 150;
	}
	
	public int meltingTemp(){
		return 400;
	}
	
	public int getHeatRadiated(){
		return 8;
	}

}
