package me.planetguy.ore.content.lavawell;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import me.planetguy.lib.prefab.BlockContainerBase;
import me.planetguy.ore.content.ODTContentPlugin;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockLavaMachinery extends BlockContainerBase{

	public IIcon[][]  icons;

	public static BlockLavaMachinery instance;

	private static Class[] tileEntities=new Class[]{
		TileEntityThermometer.class,
		TileEntityMiniWell.class,
		TileEntityLavaPuddle.class,
		TileEntityCryoPump.class, //heat sink, ice, rs?
		TileEntityHeatSink.class, //graphite corners, + of iron blocks in middle -> several
		TileEntityHeatDissipator.class, //heat sinks + iron bars
		TileEntityGraphite.class,
		TileEntityLavaPipe.class,
		TileEntityBurntBlock.class,
	};

	public BlockLavaMachinery() {
		super(ODTContentPlugin.graphite, "tileEntities", tileEntities);

		this.setLightLevel(1.0f);

		GameRegistry.addShapedRecipe(new ItemStack(this, 1, 1),
				"gbg",
				"l|l",
				"g|g",
				Character.valueOf('g'),new ItemStack(this, 1,7),
				Character.valueOf('|'),new ItemStack(this, 1,8),
				Character.valueOf('b'),new ItemStack(Items.bucket),
				Character.valueOf('l'),new ItemStack(Blocks.glass)
				);

		GameRegistry.addRecipe(new ItemStack(this, 1, 3),
				"sis",
				"iri",
				"sis",
				Character.valueOf('s'),new ItemStack(this, 1, 4),
				Character.valueOf('i'),new ItemStack(Blocks.ice),
				Character.valueOf('r'),new ItemStack(Blocks.redstone_block)
				);
		GameRegistry.addRecipe(new ItemStack(this, 4, 4),
				"gig",
				"iii",
				"gig",
				Character.valueOf('g'),new ItemStack(this, 1, 7),
				Character.valueOf('i'),new ItemStack(Blocks.iron_block)
				);
		GameRegistry.addRecipe(new ItemStack(this, 1, 5),
				"bbb",
				"bsb",
				"bbb",
				Character.valueOf('b'),new ItemStack(Blocks.iron_bars),
				Character.valueOf('s'),new ItemStack(this, 1, 4)
				);

		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Blocks.coal_block, 1, 0), new ItemStack(this, 1, 7), 0.0f);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(Blocks.coal_block, 1, 1), new ItemStack(this, 1, 7), 0.0f);
		GameRegistry.addRecipe(new ItemStack(this, 6, 8),
				new Object[]{
			"glg",
			"g g",
			"glg",
			Character.valueOf('g'),new ItemStack(this, 1, 7),
			Character.valueOf('l'),new ItemStack(Blocks.glass),
		});

		instance=this;
	}

	@Override
	public void registerBlockIcons(IIconRegister ir){
		IIcon thermometerBlock, magmaPipeTop, magmaValve, lava, cryoPump, heatSink, dissipator, graphiteBlock, burnedBlock;

		thermometerBlock=ir.registerIcon(ODTContentPlugin.modID+":thermometerBlock");
		magmaPipeTop=ir.registerIcon(ODTContentPlugin.modID+":magmaPipeTop");
		magmaValve=ir.registerIcon(ODTContentPlugin.modID+":magmaValve");
		lava=ir.registerIcon(ODTContentPlugin.modID+":lava");
		cryoPump=ir.registerIcon(ODTContentPlugin.modID+":cryoPump");
		heatSink=ir.registerIcon(ODTContentPlugin.modID+":heatSink");
		dissipator=ir.registerIcon(ODTContentPlugin.modID+":dissipator");
		graphiteBlock=ir.registerIcon(ODTContentPlugin.modID+":graphiteBlock");
		burnedBlock=ir.registerIcon(ODTContentPlugin.modID+":burnedBlock");

		icons=new IIcon[][]{{thermometerBlock, thermometerBlock, thermometerBlock, thermometerBlock,thermometerBlock, thermometerBlock},
				{magmaPipeTop, magmaPipeTop, magmaValve, magmaValve,magmaValve, magmaValve},		
				{lava, lava, lava, lava,lava, lava},
				{cryoPump, cryoPump, cryoPump, cryoPump,cryoPump, cryoPump},//TODO
				{heatSink, heatSink, heatSink, heatSink,heatSink, heatSink},//TODO
				{dissipator, dissipator, dissipator, dissipator,dissipator, dissipator},//TODO
				{graphiteBlock, graphiteBlock, graphiteBlock, graphiteBlock, graphiteBlock, graphiteBlock, },
				{magmaPipeTop,magmaPipeTop,magmaPipeTop,magmaPipeTop,magmaPipeTop,magmaPipeTop,},
				{burnedBlock,burnedBlock,burnedBlock,burnedBlock,burnedBlock,burnedBlock,},
		};
	}

	public IIcon getIcon(int side, int meta){
		return icons[meta][side];
	}

	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{	
		for (int var4 = 0; var4 <tileEntities.length; ++var4)
		{
			par3List.add(new ItemStack(this, 1, var4));
		}
	}

	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
		TileEntity te=w.getTileEntity(x,y,z);
		return te instanceof TEThermal && ((TEThermal)te).playerRightClick(player);
	}

	public int countTooltipLines(){
		return 2;
	}


}
