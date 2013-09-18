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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(name = "FarmingMod", modid = "farmingmod", version = "@VERSION@")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class FarmingMod {

	@Mod.Instance("farmingmod")
	public static FarmingMod instance;

	public static BlockCookingTable cookingTable;

	public static Blocks plant;
	public static final Items[] items = new Items[64];

	public static final CreativeTabs tabCookwares = new NewCreativeTabs("cookwares", 272);
	public static final CreativeTabs tabMaterials = new NewCreativeTabs("foodmaterials", 353);
	public static final CreativeTabs tabPlants = new NewCreativeTabs("plants", 360);

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		instance = this;
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try {
			cfg.load();
			cookingTable = new BlockCookingTable(cfg.getBlock("CookingTable", 1063).getInt());
			int itemStart = cfg.getItem("ItemStart", 9653).getInt();
			for (int i = 0; i < 64; i++) {
				items[i] = new Items(itemStart + i, i + 1);
			}
		} catch (Exception e) {} finally {
			cfg.save();
		}
		GameRegistry.registerBlock(cookingTable, "cookingtable");
		for (Items i : items) {
			GameRegistry.registerItem(i, "FarmingModItem".concat(Integer.toString(i.getItemStackLimit())));
		}
		ConfigParse.parse(event.getModConfigurationDirectory());
	}
}
