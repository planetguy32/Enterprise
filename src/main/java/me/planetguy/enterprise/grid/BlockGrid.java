package me.planetguy.enterprise.grid;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import me.planetguy.lib.prefab.BlockContainerBase;
import me.planetguy.lib.util.Debug;

public class BlockGrid extends BlockContainerBase {

	public static Block instance;

	public BlockGrid() {
		super(Material.iron, "powerGrid", new Class[]{
				TileRFGrid.class,
		});
		Debug.bp();
		instance=this;
	}
	
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List ls){	
		for (int i = 0; i < 4; ++i){
			ls.add(new ItemStack(this, 1, i));
		}
	}
	

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileRFGrid();
	}
	
}
