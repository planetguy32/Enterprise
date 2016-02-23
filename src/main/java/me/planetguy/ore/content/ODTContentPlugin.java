package me.planetguy.ore.content;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import me.planetguy.gizmos.GuiHandler;
import me.planetguy.lib.PLHelper;
import me.planetguy.lib.prefab.CreativeTabPrefab;
import me.planetguy.lib.prefab.IPrefabItem;
import me.planetguy.ore.content.lavawell.BlockLavaMachinery;
import me.planetguy.ore.content.lavawell.ItemCleanupTool;
import me.planetguy.ore.content.lavawell.ItemThermometer;
import me.planetguy.ore.content.prospecting.ProspectingTool;
import me.planetguy.ore.gen.OreDistributionTool;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=ODTContentPlugin.modID, version="0.1.1", dependencies="required-after:enterpriseores")
public class ODTContentPlugin {

	public static final String modID="enterpriseores_content";
	
	public static HashMap<String, IPrefabItem> content=new HashMap<String, IPrefabItem>();
	
	public static Item prospectingTool, thermometer, cleanupTool;
	public static Block tileEntity;
	
	PLHelper helper;
	
	public static Material graphite=new Material(MapColor.stoneColor);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		helper=new PLHelper(modID);
		Configuration conf=new Configuration(OreDistributionTool.confFile); //share ODT's config
		conf.load();
		prospectingTool=(Item) helper.loadItem(ProspectingTool.class, content);
		cleanupTool=(Item) helper.loadItem(ItemCleanupTool.class, content);
		thermometer=(Item) helper.loadItem(ItemThermometer.class, content);
		
		tileEntity=(Block) helper.loadContainer(BlockLavaMachinery.class, content);

		
		GameRegistry.addShapedRecipe(new ItemStack(thermometer),
				"g g",
				"grg",
				"gbg",
				Character.valueOf('g'),new ItemStack(Blocks.glass),
				Character.valueOf('r'),new ItemStack(Items.redstone),
				Character.valueOf('b'),new ItemStack(Items.glass_bottle)
				);
		
		conf.save();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent ie){
		CreativeTabs tab=new CreativeTabPrefab("odtTab", new ItemStack((Item) content.get("thermometer"), 1, 1));
		for(IPrefabItem item:content.values()){
			item.setCreativeTab(tab);
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent pie){
		for(IPrefabItem item:content.values()){
			item.loadCrafting();
		}
	}
	
}
