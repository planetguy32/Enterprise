package me.planetguy.enterprise.disposal.shred;

import me.planetguy.lib.prefab.FluidPrefab;
import net.minecraft.init.Blocks;

public class FluidQGP extends FluidPrefab {

	public FluidQGP() {
		super("quarkGluonPlasma");
		this.stillIcon=Blocks.stone.getIcon(0, 0);
		this.flowingIcon=Blocks.cobblestone.getIcon(0, 0);
	}

}
