package me.planetguy.enterprise.core.computercraft;

/*
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

@Optional.InterfaceList({
	@Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft")
})
*/
public class SimplePeripheral /*implements IPeripheral*/{
	
	/*
	public static HashMap<Class, SPReflection> spMap=new HashMap<Class, SPReflection>();
	*/
	public static void registerSimplePeripheral(Class c){
		//spMap.put(c, new SPReflection(c));
	}
	/*
	SPReflection spr;
	Object target;
	
	SimplePeripheral(Object o){
		Class c=o.getClass();
		this.spr=spMap.get(c);
		this.target=o;
	}
	
	@Override
	public String getType() {
		return spr.target.getCanonicalName();
	}

	@Override
	public String[] getMethodNames() {
		String[] a=spr.methodMap.keySet().toArray(new String[0]);
		Arrays.sort(a);
		return a;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arguments) {
		try{
			return (Object[]) spr.methodMap.get(getMethodNames()[method]).invoke(target, arguments);
		}catch(Exception e){
			return new Object[]{e.getMessage()};
		}
	}

	@Override
	public void attach(IComputerAccess computer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detach(IComputerAccess computer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(IPeripheral other) {
		// TODO Auto-generated method stub
		return false;
	}
	*/

}
