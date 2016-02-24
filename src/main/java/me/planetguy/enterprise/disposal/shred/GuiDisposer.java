package me.planetguy.enterprise.disposal.shred;

import me.planetguy.enterprise.disposal.EnterpriseDisposal;
import me.planetguy.lib.prefab.GuiPrefab;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiDisposer extends GuiPrefab {
	
	private static final ResourceLocation rl=new ResourceLocation(EnterpriseDisposal.modID+":"+"textures/gui/container/disposer.png");

	public GuiDisposer (InventoryPlayer inventoryPlayer, TileEntity tileEntity) {
		super(new ContainerDisposer(inventoryPlayer, tileEntity), rl);
	}

	@Override
	public String getLabel() {
		return "Disposer";
	}

}
