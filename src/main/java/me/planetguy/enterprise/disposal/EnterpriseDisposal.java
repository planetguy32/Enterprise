package me.planetguy.enterprise.disposal;

import java.util.HashMap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import me.planetguy.enterprise.disposal.shred.BlockDisposer;
import me.planetguy.enterprise.disposal.shred.ContainerDisposer;
import me.planetguy.enterprise.disposal.shred.FluidQGP;
import me.planetguy.enterprise.disposal.shred.GuiDisposer;
import me.planetguy.enterprise.disposal.shred.ItemSubatomicMatter;
import me.planetguy.lib.PLHelper;
import me.planetguy.lib.prefab.GuiHandlerPrefab;
import me.planetguy.lib.prefab.IPrefabItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = EnterpriseDisposal.modID)
public class EnterpriseDisposal {
	
	public static final String modID="enterprisedisposal";
	
	public static Fluid qgp;
	
	public static HashMap<String, IPrefabItem> map=new HashMap<String, IPrefabItem>();
	
	public static CreativeTabs tab=new CreativeTabs("disposal"){
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(BlockDisposer.instance);
		}
	};
	
	public static PLHelper helper;

	public static EnterpriseDisposal instance;
	
	public EnterpriseDisposal(){
		instance=this;
	}
	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent e){
		helper=new PLHelper(modID);
		helper.load(BlockDisposer.class, map);
		qgp=(Fluid) helper.load(FluidQGP.class, map);
		helper.load(ItemSubatomicMatter.class, map);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e){
		GuiHandlerPrefab.create(this, new Class[]{
				//server
				ContainerDisposer.class,
				ContainerDisposer.class,
				ContainerDisposer.class,
		}, new Class[]{
				//client
				GuiDisposer.class,
				GuiDisposer.class,
				GuiDisposer.class,
		});
	}
	
	@EventHandler
	public void postLoad(FMLPostInitializationEvent e){
		for(IPrefabItem i:map.values()){
			i.setCreativeTab(tab);
		}
	}

}