package me.planetguy.ore.content.prospecting;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.planetguy.lib.prefab.ItemBase;
import me.planetguy.ore.content.ODTContentPlugin;
import me.planetguy.ore.gen.ODTCommand;
import me.planetguy.ore.gen.ODTGen;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ProspectingTool extends ItemBase {

	public IIcon brokenIcon;
	
	public ProspectingTool() {
		super("prospectingTool");
		this.setMaxDamage(64);
		this.setMaxStackSize(1);
		LanguageRegistry.instance().addStringLocalization("item."+ProspectingTool.class.getCanonicalName()+".name", "en_US", "Prospecting Supplies");
		GameRegistry.addShapelessRecipe(
			new ItemStack(this), 
				new ItemStack(Items.book), 
				new ItemStack(Items.feather),
				new ItemStack(Items.dye),
				new ItemStack(Items.glass_bottle),
				new ItemStack(Items.stone_pickaxe), 
				new ItemStack(Items.stone_shovel),
				new ItemStack(Items.compass));
		GameRegistry.addRecipe(new ProspectingRepairRecipe());
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
    public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer player){
    	if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER){
    		if(stack.getItemDamage()<64){
    			if(stack.hasTagCompound()&&stack.getTagCompound().hasKey("otpCooldownExpiry")&&stack.getTagCompound().getLong("otpCooldownExpiry")-System.currentTimeMillis()>0){
    				ODTCommand.tell("You are still recording your findings!", player);
    			}else{
    				ODTCommand.prospectAlreadyPaid((WorldServer) w, player.chunkCoordX, player.chunkCoordZ, player);
    				if(!(player instanceof EntityPlayerMP && ((EntityPlayerMP)player).theItemInWorldManager.isCreative())){
    					stack.setItemDamage(stack.getItemDamage()+1);
    					NBTTagCompound tag=new NBTTagCompound();
    					tag.setLong("otpCooldownExpiry", System.currentTimeMillis()+10000);
    					stack.setTagCompound(tag);
    				}
    			}
    		}else{
    			ODTCommand.tell("Your prospecting supplies are worn out! Replace the pick and shovel!", player);
    		}
    	}
        return stack;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir){
    	super.registerIcons(ir);
    	brokenIcon=ir.registerIcon(ODTContentPlugin.modID+":prospectingToolBroken");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stk){
    	if(stk.getItemDamage()<64)
    		return itemIcon;
    	else
    		return brokenIcon;
    }
    
    
}
