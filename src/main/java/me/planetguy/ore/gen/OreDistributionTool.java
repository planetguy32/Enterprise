package me.planetguy.ore.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

@Mod(modid = "enterpriseores", version="0.1")
public class OreDistributionTool {

	public static OreDistributionTool instance;
	
	public static HashSet<BlockMeta> oreIMPsSet=new HashSet<BlockMeta>();

	public static HashMap<BlockMeta, OreEntry> oreMap=new HashMap<BlockMeta, OreEntry>();

	public static File confFile;
	
	public static HashSet<String> trustedUsers=new HashSet<String>();

	public static boolean allowProspecting;
	
	public static double chanceOfProspectingFailure=0.5;

	public static boolean removeTraceOres;
	
	public static double traceOreFactor=0.0;
	
	public static List<String> oresStr=new ArrayList<String>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		confFile=e.getSuggestedConfigurationFile();
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
	}

	@EventHandler
	public void PostInit(FMLPostInitializationEvent pie){
		updateConfig();
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent sse){
		sse.registerServerCommand(new ODTCommand());
	}

	public static void updateConfig(){
		Configuration cfg=new Configuration(confFile);
		cfg.load();
		trustedUsers=new HashSet<String>(Arrays.asList(cfg.get("general", "TrustedUsers", "").getString().split(",")));
		chanceOfProspectingFailure=cfg.get("prospecting", "ChanceOfProspectingFalseNegative", chanceOfProspectingFailure).getDouble(chanceOfProspectingFailure);
		allowProspecting=cfg.get("prospecting", "AllowProspectingChatCommand", true).getBoolean(true);
		oresStr=Arrays.asList(cfg.get("general", "oreIDs", oresStr.toArray(new String[0])).getStringList());
		removeTraceOres=cfg.get("general", "removeAllExistingOreSeams", false).getBoolean(false);
		traceOreFactor=cfg.get("general", "globalProbabilityFactorTraceOres", 0.0).getDouble(0.0);
		for(String id:oresStr){
			id=id.replace("tile.", "");
			try{
				BlockMeta bm=new BlockMeta(id);
				Property prop=cfg.get("ores", ""+id, "0.0% of stone, in 0.0% of chunks");
				//add block name to config file by decoding ID-meta pair
				prop.comment=getDisplayName(bm);
				oreMap.put(bm, new OreEntry(prop.getString()));
				oreIMPsSet.add(bm);
			}catch(Exception e){
				RuntimeException re= new RuntimeException(id);
				re.addSuppressed(e);
			}
		}
		cfg.save();
		if(instance!=null)
			MinecraftForge.EVENT_BUS.unregister(instance);
		instance=new OreDistributionTool();
		MinecraftForge.EVENT_BUS.register(instance);
	}
	
	@SubscribeEvent
	public void handle(OreRegisterEvent e){
		if(e.Name.contains("ore"))
			oresStr.add(e.Ore.getUnlocalizedName()+"@"+e.Ore.getItemDamage());
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onChunkGenDone(PopulateChunkEvent.Post evt){
		if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER)
			ODTGen.repopulateOres((WorldServer)evt.world, evt.chunkX, evt.chunkZ);
	}

	public static String getDisplayName(BlockMeta bm){
		return new ItemStack(bm.block, 1, bm.meta).getDisplayName();
	}

}
