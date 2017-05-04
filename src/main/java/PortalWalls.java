import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

@Mod(modid = "nowyourebuilding")
public class PortalWalls {
	
	private static IIcon white,gray;
	
	public class PWBlock extends Block{

		protected PWBlock() {
			super(Material.iron);
			this.setBlockName("nowyourebuilding:block");
		}
		
		public IIcon getIcon(int side, int meta){
			return meta==0?white:gray;
		}
		
		public void registerBlockIcons(IIconRegister ir){
			white=ir.registerIcon("nowyourebuilding:white");
			gray=ir.registerIcon("nowyourebuilding:gray");
		}
		
	}
	
	public class PWSlab extends BlockSlab{

		public PWSlab() {
			super(false, Material.iron);
			this.setBlockName("nowyourebuilding:slab");
		}

		@Override
		public String func_150002_b(int p_150002_1_) {
			return "nowyourebuilding:slab";
		}
		
		public IIcon getIcon(int side, int meta){
			return meta==0?white:gray;
		}
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e){
		Block b=new PWBlock();
		b.setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(b, b.getUnlocalizedName());
		
		Block slab=new PWSlab();
		slab.setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(slab, slab.getUnlocalizedName());
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e){
		Blocks.iron_bars.setBlockUnbreakable().setResistance(6000000.0F);
	}
}
