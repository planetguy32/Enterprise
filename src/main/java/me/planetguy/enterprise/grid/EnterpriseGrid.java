package me.planetguy.enterprise.grid;

import java.util.HashMap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.planetguy.lib.PLHelper;
import me.planetguy.lib.prefab.CreativeTabPrefab;
import me.planetguy.lib.prefab.IPrefabItem;

@Mod(modid="enterprisegrid")
public class EnterpriseGrid {
	
	PLHelper h;
	
	HashMap<String, IPrefabItem> map=new HashMap<String, IPrefabItem>();
	
	CreativeTabs tab;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		h=new PLHelper("enterprisegrid");
		h.load(BlockGrid.class, map);
		PowerGrid2dRegistry.registerHandler(new PowerGridProviderRF());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		tab=new CreativeTabPrefab("tabEnterpriseGrid", new ItemStack(BlockGrid.instance));
		for(IPrefabItem i:map.values()){
			i.setCreativeTab(tab);
		}
	}

}
