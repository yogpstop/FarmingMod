/*
 * Copyright (C) 2012,2013 yogpstop
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the
 * GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.yogpstop.fm;

import java.util.List;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import static org.yogpstop.fm.DB.ims;

public class Items extends Item {
	public Items(int id, int stack) {
		super(id);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setMaxStackSize(stack);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return ims.get(meta).getIcon();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		for (ItemMeta im : ims.values())
			im.registerIcons(ir);
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		return ims.get(is.getItemDamage()).getUnlocalizedName();
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int direction, float par8, float par9, float par10) {
		return ims.get(is.getItemDamage()).onItemUse(is, ep, world, x, y, z, direction, par8, par9, par10);
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer ep) {
		return ims.get(is.getItemDamage()).onEaten(is, world, ep);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is) {
		return ims.get(is.getItemDamage()).getMaxItemUseDuration();
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is) {
		return ims.get(is.getItemDamage()).getItemUseAction();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		return ims.get(is.getItemDamage()).onItemRightClick(is, world, ep);
	}

	@Override
	public void getSubItems(int par1, CreativeTabs ct, List list) {
		Set<Integer> i = ims.keySet();
		String label = ct.getTabLabel();
		for (Integer j : i) {
			if (j != null) {
				ItemMeta im = ims.get(j);
				if (im.getMaxStackSize() != this.getItemStackLimit()) continue;
				if (label.equals("food") && im.isEatable()) {
					list.add(new ItemStack(par1, 1, j));
				}
				if (label.equals("cookwares") && im.isCookware()) {
					list.add(new ItemStack(par1, 1, j));
				}
				if (label.equals("plants") && im.isPlantable()) {
					list.add(new ItemStack(par1, 1, j));
				}
				if (label.equals("foodmaterials") && !im.isPlantable() && !im.isCookware() && !im.isEatable()) {
					list.add(new ItemStack(par1, 1, j));
				}

			}
		}
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { FarmingMod.tabCookwares, FarmingMod.tabMaterials, FarmingMod.tabPlants, CreativeTabs.tabFood };
	}
}
