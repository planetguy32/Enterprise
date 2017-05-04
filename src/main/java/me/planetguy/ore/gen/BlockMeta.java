package me.planetguy.ore.gen;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockMeta {
	
	public Block block;
	public int meta;
	
	public BlockMeta(Block block, int meta){
		this.block=block;
		this.meta=meta;
	}
	
	public BlockMeta(String s){
		this(Block.getBlockFromName(s.split("@")[0]), Integer.parseInt(s.split("@")[1]));
	}
	
	public String toString(){
		return new ItemStack(block, 1, meta).getDisplayName();
	}
	
	public int hashCode(){
		return block.hashCode() * Integer.hashCode(meta);
	}
	
	public boolean equals(Object o){
		if(o instanceof BlockMeta)
			return equals((BlockMeta)o);
		return false;
	}
	
	public boolean equals(BlockMeta bm){
		return bm.block == block && bm.meta == meta;
	}
	
}
