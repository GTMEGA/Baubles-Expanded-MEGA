package baubles.common;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.expanded.BaubleExpandedSlots;
import baubles.api.expanded.BaubleItemHelper;
import baubles.api.expanded.IBaubleExpanded;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDebugger extends Item implements IBaubleExpanded {

	public ItemDebugger() {
		super();
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		setCreativeTab(BaublesConfig.hideDebugItem ? null : CreativeTabs.tabTools);
	}

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir) {
		icons = new IIcon[BaubleExpandedSlots.getCurrentlyRegisteredTypes().size()];
		for(int i = 0; i < icons.length; i++) {
		  icons[i] = ir.registerIcon("baubles:empty_bauble_slot_" + BaubleExpandedSlots.getCurrentlyRegisteredTypes().get(i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int meta) {
		return icons[meta >= icons.length ? 0 : meta];
	}

    @SuppressWarnings({"unchecked", "rawtypes"})
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < icons.length; i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        return BaubleItemHelper.onBaubleRightClick(itemStackIn, worldIn, player);
    }

	@Override
	public String[] getBaubleTypes(ItemStack itemStack) {
	  String type;
	  int meta = itemStack.getItemDamage();
	  if(meta <= 0 || meta > icons.length) {
		 type = BaubleExpandedSlots.unknownType;
	  } else {
		 type = BaubleExpandedSlots.getCurrentlyRegisteredTypes().get(meta);
	  }
	  return new String[] {type};
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemStack) {
	  return null;
	}

    @SuppressWarnings({"unchecked", "rawtypes"})
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean debug) {
        BaubleItemHelper.addSlotInformation(tooltip, getBaubleTypes(stack));
	}

	@Override
	public void onWornTick(ItemStack itemStack, EntityLivingBase player) {
	}

	@Override
	public boolean hasEffect(ItemStack itemStack, int a) {
	  return true;
	}

	@Override
	public void onEquipped(ItemStack itemStack, EntityLivingBase player) {
	}

	@Override
	public void onUnequipped(ItemStack itemStack, EntityLivingBase player) {
	}

	@Override
	public boolean canEquip(ItemStack itemStack, EntityLivingBase player) {
	  return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemStack, EntityLivingBase player) {
	  return true;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getBackgroundIconForSlotType(String type) {
		if(type != null && BaubleExpandedSlots.isTypeRegistered(type)) {
			return icons[BaubleExpandedSlots.getIndexOfTypeInRegisteredTypes(type)];
		} else {
			return icons[0];
		}
	}

}
