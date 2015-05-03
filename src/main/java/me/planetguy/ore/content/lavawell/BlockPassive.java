package me.planetguy.ore.content.lavawell;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import me.planetguy.lib.prefab.BlockBase;
import me.planetguy.ore.content.ODTContentPlugin;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.IIcon;

public class BlockPassive extends BlockBase {

	public static BlockPassive instance;
	
	public IIcon[] icons;
	
	public BlockPassive() {
		super(ODTContentPlugin.graphite, "graphite");
		instance=this;
		//this.setNames("en_US", "Graphite", "Magma pipe", "Burned block");
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Blocks.coal_block, 1, 0), new ItemStack(this, 1, 0), 0.0f);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Blocks.coal_block, 1, 1), new ItemStack(this, 1, 0), 0.0f);
		GameRegistry.addRecipe(new ItemStack(this, 6, 1),
				new Object[]{
			"glg",
			"g g",
			"glg",
			Character.valueOf('g'),new ItemStack(this, 1, 0),
			Character.valueOf('l'),new ItemStack(Blocks.glass),
		});
		
		//TODO Registry.registerTooltip(this.blockID, 0, "Very heat-resistant.");
		//Registry.registerTooltip(this.blockID, 1, "Carries magma from below the chunk.");
		//Registry.registerTooltip(this.blockID, 2, "Destroyed by the heat from a lava pool.");
		
		
	}
	
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List items){
		for(int i=0; i<icons.length; i++){
			items.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister ir){
		icons=new IIcon[]{
			ir.registerIcon(ODTContentPlugin.modID+":graphiteBlock"),
			ir.registerIcon(ODTContentPlugin.modID+":magmaPipeTop"),
			ir.registerIcon(ODTContentPlugin.modID+":burnedBlock"),
		};
	}
	
	public IIcon getIcon(int side, int meta){
		return icons[meta];
	}

}
