package org.yogpstop.fm;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCooking extends Slot {

	public SlotCooking(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack is) {
		if (!(is.getItem() instanceof Items))
			return false;
		return DB.isCookware(is.getItemDamage());
	}

}
