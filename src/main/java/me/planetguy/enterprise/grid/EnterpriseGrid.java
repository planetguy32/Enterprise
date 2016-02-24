package me.planetguy.enterprise.grid;

import java.util.HashMap;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import me.planetguy.lib.PLHelper;
import me.planetguy.lib.prefab.CreativeTabPrefab;
import me.planetguy.lib.prefab.IPrefabItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

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
	
	//TODO make the grid collect all supply and all demand, then distribute at tick end?

}
