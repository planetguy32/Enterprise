package me.planetguy.enterprise.disposal.shred;

import me.planetguy.lib.prefab.ItemBase;

public class ItemSubatomicMatter extends ItemBase {

	public static ItemSubatomicMatter instance;
	
	public ItemSubatomicMatter() {
		super("subatomicMatter");
		instance=this;
	}

}
