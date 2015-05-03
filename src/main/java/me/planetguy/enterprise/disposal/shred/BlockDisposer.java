package me.planetguy.enterprise.disposal.shred;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import me.planetguy.lib.prefab.BlockContainerBase;

public class BlockDisposer extends BlockContainerBase {

	public static Block instance;

	public BlockDisposer() {
		super(Material.iron, "disposer", new Class[]{
				TileEntityDisposer.class, 
				TileEntityDisposerAdv.class,
				TileEntitySAMCondenser.class
				});
		instance=this;
	}
	
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int px, float py, float pz, float p_149727_9_) {
    	TileEntity te=w.getTileEntity(x, y, z);
    	if(te instanceof TileEntityBase)
    		return ((TileEntityBase) te).onInteract(p);
		return true;
	}
	
	//(t^(t%255)|15)^t>>6|(t>>8)/(t>>7-t&-t>>4+5)
	
}
