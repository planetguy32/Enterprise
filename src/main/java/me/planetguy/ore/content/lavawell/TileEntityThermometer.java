package me.planetguy.ore.content.lavawell;


import java.lang.reflect.InvocationTargetException;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import me.planetguy.enterprise.core.computercraft.SPMethod;
import me.planetguy.enterprise.core.computercraft.SimplePeripheral;
import me.planetguy.enterprise.core.computercraft.SimplePeripheralProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityThermometer extends TileEntity{
	
	static{
		SimplePeripheral.registerSimplePeripheral(TileEntityThermometer.class);
	}

	private volatile float cachedTemperature;
	
	public void updateEntity(){
		cachedTemperature=0;
		for(ForgeDirection facingSide:ForgeDirection.VALID_DIRECTIONS){
		cachedTemperature=Math.max(cachedTemperature,ItemThermometer.getTemperature(worldObj, 
				xCoord+facingSide.offsetX, 
				yCoord+facingSide.offsetY, 
				zCoord+facingSide.offsetZ, 
				facingSide.getOpposite().flag));
		}
	}
	
    public boolean canUpdate()
    {
        return true;
    }

    @SPMethod
	public Object[] getTemperature() throws Exception {
		return new Object[]{cachedTemperature};
	}

}
