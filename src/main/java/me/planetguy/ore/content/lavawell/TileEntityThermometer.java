package me.planetguy.ore.content.lavawell;


import me.planetguy.enterprise.core.computercraft.SPMethod;
import me.planetguy.enterprise.core.computercraft.SimplePeripheral;
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
