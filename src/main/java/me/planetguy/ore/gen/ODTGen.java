package me.planetguy.ore.gen;

import java.util.ArrayList;

import me.planetguy.lib.include.it.unimi.dsi.util.XorShift;
import me.planetguy.lib.util.Debug;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ODTGen {
	
	public static String map(World w, int squareSize, int cx0, int cz0){
		String result="======Ore seams======\n";
		ArrayList<Integer> ints=new ArrayList<Integer>();
		for(int i=squareSize; i>=-squareSize; i--){
			for(int j=-squareSize; j<=squareSize; j++){
				BlockMeta ore=getOre(w, cz0+i, cx0+j);
				if(ore != null)
					result += "X";
				else if(i==0 || j==0)
					result += "#";
				else
					result += "_";
			}
			result+="\n";
		}
		return result;
	}

	static void repopulateOre(WorldServer w, int cx, int cz, BlockMeta ores, XorShift r){
		for(int x=cx*16; x<cx*16+16; x++){
			for(int y=0; y<256; y++){
				for(int z=cz*16; z<cz*16+16; z++){
					String blockIMP=w.getBlock(x, y, z)+":"+w.getBlockMetadata(x, y, z);
					if(w.getBlock(x, y, z)==Blocks.stone
							&&r.nextDouble()<((OreEntry)OreDistributionTool.oreMap.get(ores)).mineDensity){
						w.setBlock(x, y, z, ores.block, ores.meta, 6);
					}
				}
			}
		}
	}

	public static void repopulateOres(WorldServer w, int cx, int cz){
		prePopulate(w,cx,cz, new XorShift(cx, cz, w.getSeed()));
		BlockMeta ore=getOre(w, cx, cz);
 		Debug.dbg(cx+" "+cz+": Generating "+ore);
		if(ore!=null)
			repopulateOre(w,cx, cz, ore, new XorShift(cx, cz, w.getSeed()));
	}
	
	public static void prePopulate(WorldServer w, int cx, int cz, XorShift r){
		BlockMeta blockIMP=new BlockMeta("minecraft:stone@0"); //we reuse this one
		for(int x=cx*16; x<cx*16+16; x++){
			for(int y=0; y<256; y++){
				zIterLoop:
				for(int z=cz*16; z<cz*16+16; z++){
					blockIMP.block=w.getBlock(x, y, z);
					blockIMP.meta=w.getBlockMetadata(x, y, z);
					if(OreDistributionTool.removeTraceOres &&OreDistributionTool.oreIMPsSet.contains(blockIMP)){ //remove ore if it should be generated elsewhere
						w.setBlock(x, y,z, Blocks.stone);
					}
					
					//Trace ores - they're available everywhere, in seams of one
					if(OreDistributionTool.traceOreFactor!=0&&w.getBlock(x, y, z)==Blocks.stone){
						double value=r.nextDouble()/OreDistributionTool.traceOreFactor;
						for(BlockMeta ore:OreDistributionTool.oreIMPsSet){
							OreEntry shareConsumed=((OreEntry)OreDistributionTool.oreMap.get(ore));
							value-=shareConsumed.mineFrequency;
							if(value < 0){
								w.setBlock(x, y, z, ore.block,ore.meta, 2 );
								continue zIterLoop;//don't keep trying ores
							}
						}
					}
				}
			}
		}
	}
	
	public static BlockMeta getOre(World w, int cx, int cz){
		double value=new XorShift(cx, cz, w.getSeed()).nextDouble();
		for(BlockMeta ore:OreDistributionTool.oreIMPsSet){
			OreEntry shareConsumed=(OreDistributionTool.oreMap.get(ore));
			value-=shareConsumed.mineFrequency;
			if(value < 0){
				return ore;
			}
		}
		return null;
	}

}
