package me.planetguy.enterprise.disposal.shred;

import org.lwjgl.opengl.GL11;

import me.planetguy.enterprise.disposal.EnterpriseDisposal;
import me.planetguy.gizmos.Properties;
import me.planetguy.lib.prefab.GuiPrefab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

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
