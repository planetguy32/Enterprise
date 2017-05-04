package me.planetguy.ore.gen;

import java.util.ArrayList;

import me.planetguy.lib.include.it.unimi.dsi.util.XorShift;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;

public class ODTCommand extends CommandBase{


	@Override
	public String getCommandName() {
		return "odt";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/odt <repop, info, reload, prospect, help (command)>";
	}

	@Override
	public void processCommand(ICommandSender sender,
			String[] astring) {
		if(sender instanceof EntityPlayerMP){
			if(astring.length==1){
				int px = sender.getPlayerCoordinates().posX/16 - 1;
				int pz = sender.getPlayerCoordinates().posZ/16 - 1;
				String c=astring[0];
				boolean isPlayerOp=((EntityPlayerMP) sender).mcServer.getConfigurationManager().func_152607_e(((EntityPlayerMP) sender).getGameProfile());
				if(c.equals("repop")&&isPlayerOp){
					ODTGen.repopulateOres((WorldServer) sender.getEntityWorld(), px, pz);
					tell("Repopulated ores.",sender);
				}else if(c.equals("info")&&isPlayerOp){
					ItemStack hand=((EntityPlayer)sender).getCurrentEquippedItem();
					tell("Held item: "+(hand.getUnlocalizedName()+":"+hand.getItemDamage()), sender);
				}else if(c.equals("reload")&&isPlayerOp){
					OreDistributionTool.updateConfig();
					tell("Reloaded ODT config.",sender);
				}else if(c.equals("prospect")&&
						(OreDistributionTool.allowProspecting||isPlayerOp)){ //can prospect if allowed, or force if op
					prospect((WorldServer) sender.getEntityWorld(), px, pz, (EntityPlayer) sender);
				}else if(c.equals("map") &&isPlayerOp){
					tell(ODTGen.map(sender.getEntityWorld(), 10, px, pz), sender);
				}else{
					System.out.println("cmd "+c+" not found");
					tell(getCommandUsage(sender), sender);
				}
			}else{
				tell(getCommandUsage(sender), sender);
			}
		}
	}
	
	
	
	public static void prospect(WorldServer world, int cx, int cz, EntityPlayer chargee){
		if(!playerSuccessfullyCharged(chargee)){
			tell("You don't have the supplies for prospecting!",chargee);
		}
		prospectAlreadyPaid(world, cx, cz, chargee);
	}
	
	public static final int range=0;
	public static void prospectAlreadyPaid(WorldServer world, int cx, int cz, EntityPlayer player){
		//tell("You examine the geology of the area nearby.", chargee);
		
		ArrayList<String> names=new ArrayList<String>();
		for(int dx=-range; dx<=range; dx++){
			for(int dz=-range; dz<=range; dz++){
				BlockMeta ore=ODTGen.getOre(world, cx+dx, cz+dz);
				double rand=XorShift.genericPRNG.nextDouble();
				if(ore!=null && rand>OreDistributionTool.chanceOfProspectingFailure)
					names.add(OreDistributionTool.getDisplayName(ore));
			}
		}
		if(names.size()>0){
			tell("You find traces of "+names.get(XorShift.genericPRNG.nextInt(names.size()))+".",player); //choose one
		}else{
			tell("You don't find anything special.",player);
		}
	}
	
	public static void tell(String text, ICommandSender sender){
		for(String line:text.split("\n")){
			IChatComponent msg=new ChatComponentText(line);
			sender.addChatMessage(msg);
		}
	}
	
	public static boolean playerSuccessfullyCharged(EntityPlayer player){
		return true;
	}
	
}