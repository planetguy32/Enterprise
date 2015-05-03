package me.planetguy.ore.content.lavawell;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import me.planetguy.lib.prefab.ItemBase;
import me.planetguy.lib.util.Debug;
import me.planetguy.ore.gen.ODTCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemThermometer extends ItemBase {

	public ItemThermometer() {
		super("thermometer");
		this.setMaxDamage(5000);
		//TODO LanguageRegistry.instance().addNameForObject(this, "en_US", "Portable Thermometer");
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World w, int x, int y, int z, int side, float par8, float par9, float par10){
		if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT)
			return false;
		int temp=getTemperature(w, x, y, z, side);
		if(temp!=-1){
			double overheating=getOverHeatedTime(w,x,y,z,side);
			boolean stillOverheating=isActivelyOverheating(w,x,y,z,side);
			ODTCommand.tell("The temperature reads "+temp+" degrees."+" Overheated to "+(int)(overheating*100)+"% of melting"+ (stillOverheating ? ", and counting.": ""), player);
			return true;
		}else{
			return false;
		}
	}
	
	public static int getTemperature(World w, int x, int y, int z, int side){
		TileEntity te=w.getTileEntity(x,y,z);
		if(te instanceof IHeatConductive){
			return ((IHeatConductive)te).getHeat();
		}else
			return -1;
	}
	
	public static double getOverHeatedTime(World w, int x, int y, int z, int side){
		TileEntity te=w.getTileEntity(x,y,z);
		if(te instanceof TEThermal){
			return (((TEThermal)te).getOverheatLevel()/((double)((TEThermal)te).meltingTime()));
		}else
			return -1;
	}
	
	public static boolean isActivelyOverheating(World w, int x, int y, int z, int side){
		TileEntity te=w.getTileEntity(x,y,z);
		if(te instanceof TEThermal){
			return ((TEThermal)te).getHeat()>((TEThermal)te).meltingTemp();
		}else
			return false;
	}
	
	public void onUpdate(ItemStack stk, World w, Entity e, int par4, boolean par5) {
		if(e instanceof EntityPlayer&&FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
			if(Minecraft.getMinecraft().objectMouseOver!=null)  	{
	    		int xm=Minecraft.getMinecraft().objectMouseOver.blockX;
	    		int ym=Minecraft.getMinecraft().objectMouseOver.blockY;
	    		int zm=Minecraft.getMinecraft().objectMouseOver.blockZ;
	    		TileEntity te=w.getTileEntity(xm,ym,zm);
	    		if(te instanceof IHeatConductive){
	    			stk.setItemDamage(this.getMaxDamage()-((IHeatConductive)te).getHeat());
	    		}
	    	}
		}else
			stk.setItemDamage(this.getMaxDamage());
	}

}
