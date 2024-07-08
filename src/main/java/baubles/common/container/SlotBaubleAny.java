package baubles.common.container;

import baubles.api.IBauble;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotBaubleAny extends SlotBauble {
    public SlotBaubleAny(IInventory inventory, int slot, int x, int y) {
        super(inventory, "any", slot, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        InventoryBaubles baubles = (InventoryBaubles) this.inventory;
        EntityPlayer player = baubles.player.get();

        Item item = stack.getItem();
        if (!(item instanceof IBauble) || !((IBauble) item).canEquip(stack, player)) {
            return false;
        }

        for (int i = 0; i < baubles.getSizeInventory(); i++) {
            ItemStack stackInSlot = baubles.getStackInSlot(i);

            if (stackInSlot == null) {
                continue;
            }

            if (stack.isItemEqual(stackInSlot)) {
                return false;
            }
        }

        return true;
    }
}
