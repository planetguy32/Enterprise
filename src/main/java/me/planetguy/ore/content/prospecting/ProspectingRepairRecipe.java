package me.planetguy.ore.content.prospecting;

import me.planetguy.ore.content.ODTContentPlugin;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ProspectingRepairRecipe implements IRecipe{

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		boolean pick=false, pkit=false, shovel=false, extra=false;
		int slot=0;
		while(slot<inv.getSizeInventory()&&!extra){
			ItemStack stk=inv.getStackInSlot(slot);
			if(stk!=null){
				Item i=stk.getItem();
				if(i==Items.stone_pickaxe&&!pick){ //require a pick
					pick=true;
				}else if(i==Items.stone_shovel&&!shovel){    //require a shovel
					shovel=true;
				}else if(i==ODTContentPlugin.prospectingTool&&!pkit)
					pkit=true;
				else
					extra=true;
				if(pick&&pkit&&shovel&&!extra)
					return true;
			}
			slot++;

		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		return new ItemStack(ODTContentPlugin.prospectingTool);
	}

	@Override
	public int getRecipeSize() {
		return 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ODTContentPlugin.prospectingTool);
	}

}
