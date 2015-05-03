package me.planetguy.enterprise.disposal.shred;

import me.planetguy.lib.prefab.ContainerPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ContainerDisposer extends ContainerPrefab {

	public ContainerDisposer(InventoryPlayer inventoryPlayer, TileEntity tileEntity) {
		super(inventoryPlayer, tileEntity);
	}

	@Override
	public void makeSlots(TileEntity te) {
		addSlotToContainer(new Slot((IInventory) te, 1, 62+18, 17+18));
	}

}
