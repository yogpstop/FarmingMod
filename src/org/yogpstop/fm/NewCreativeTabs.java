package org.yogpstop.fm;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public final class NewCreativeTabs extends CreativeTabs {
	private final int itemindex;

	public NewCreativeTabs(String label, int Aitemindex) {
		super(label);
		itemindex = Aitemindex;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex() {
		return itemindex;
	}
}
