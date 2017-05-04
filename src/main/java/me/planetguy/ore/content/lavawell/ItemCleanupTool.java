package me.planetguy.ore.content.lavawell;

import me.planetguy.lib.prefab.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCleanupTool extends ItemBase {
	
	public ItemCleanupTool(){
		super("lavaCleanupTool");
	}
	
	int radius=2;
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World w, int x, int y, int z, int side, float par8, float par9, float par10){
		for(int dx=-radius; dx<=radius; dx++){
			for(int dy=-radius; dy<=radius; dy++){
				for(int dz=-radius; dz<=radius; dz++){
					if(w.getBlock(x+dx,y+dy,z+dz) == Blocks.flowing_lava
							|| (w.getBlock(x+dx,y+dy,z+dz)==BlockLavaMachinery.instance && w.getBlockMetadata(x+dx,y+dy,z+dz)==2)){
						w.setBlockToAir(x+dx,y+dy,z+dz);
					}
				}
			}
		}
		return true;
	}

}
