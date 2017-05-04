package me.planetguy.enterprise.disposal;

import me.planetguy.enterprise.disposal.shred.GuiDisposer;

public class ProxyClient extends ProxyCommon{
	
	public Class[] getClientClasses(){
		return new Class[]{
				//client
				GuiDisposer.class,
				GuiDisposer.class,
				GuiDisposer.class,
		};
	}

}
