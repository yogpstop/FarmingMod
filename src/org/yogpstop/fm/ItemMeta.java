package org.yogpstop.fm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemMeta {
	public ItemMeta(HashMap<String, String> data, File f) {
		if (!data.containsKey("stacksize"))
			data.put("stacksize", "64");
		try {
			if (data.containsKey("texture")) {
				data.put("texture",
						(new File(f.getParentFile(), data.get("texture")))
								.getCanonicalPath());
			} else {
				data.put(
						"texture",
						f.getCanonicalPath().substring(0,
								f.getCanonicalPath().length() - 3)
								+ "png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!data.containsKey("iconresolution"))
			data.put("iconresolution", "16");
		if (data.containsKey("iconindex")) {
			int index = Integer.parseInt(data.get("iconindex"));
			int res = Integer.parseInt(data.get("iconresolution"));
			int[] wh = GLHandler.getWH(data.get("texture"));
			int w = wh[0] / res;
			data.put("iconx", Integer.toString((index % w)));
			data.put("icony", Integer.toString(index / w));
		} else {
			if (!data.containsKey("iconx"))
				data.put("iconx", "0");
			if (!data.containsKey("icony"))
				data.put("icony", "0");
		}
		if (data.containsKey("cookware")) {
			if (!data.containsKey("isneedfuel")) {
				data.put("isneedfuel", "false");
			}
			ItemCookware(Boolean.parseBoolean(data.get("isneedfuel")));
		}
		if (data.containsKey("eatable")) {
			if (!data.containsKey("hunger")) {
				data.put("hunger", "0");
			}
			if (!data.containsKey("health")) {
				data.put("health", "0");
			}
			if (!data.containsKey("saturationmodifier")) {
				data.put("saturationmodifier", "60");
			}
			if (!data.containsKey("container")) {
				data.put("container", "");
			}
			if (!data.containsKey("potions")) {
				data.put("potions", "");
			}
			if (!data.containsKey("isdrink")) {
				data.put("isdrink", "false");
			}
			if (!data.containsKey("alwaysedible")) {
				data.put("alwaysedible", "false");
			}
			ItemEatable(Byte.parseByte(data.get("hunger")),
					Byte.parseByte(data.get("health")), data.get("potions"),
					Integer.parseInt(data.get("saturationmodifier")),
					data.get("container"),
					Boolean.parseBoolean(data.get("alwaysedible")),
					Boolean.parseBoolean(data.get("isdrink")));
		}
		if (data.containsKey("plantable")) {
			ItemPlantable(DB.getBlock(data.get("plantblock")));
		}
		ItemBase(data.get("name"), Byte.parseByte(data.get("stacksize")),
				data.get("texture"));
		texture(Integer.parseInt(data.get("iconresolution")),
				Integer.parseInt(data.get("iconx")),
				Integer.parseInt(data.get("icony")));
	}

	private int plantMeta;
	private boolean plantable;

	private void ItemPlantable(int plant) {
		plantMeta = plant;
		plantable = true;
	}

	private String textureFile;
	private String itemName;
	private byte stackSize;

	private void ItemBase(String name, byte stacksize, String texture) {
		itemName = name;
		stackSize = stacksize;
		textureFile = texture;
	}

	private int iconResolution;
	private int iconXCoord, iconYCoord;

	private void texture(int r, int x, int y) {
		iconResolution = r;
		iconXCoord = x;
		iconYCoord = y;
	}

	private boolean needFuel;
	private boolean cookware;

	private void ItemCookware(boolean isNeedFuel) {
		needFuel = isNeedFuel;
		cookware = true;
	}

	private int itemUseDuration;
	private int healAmount;
	private float saturationModifier;
	private boolean alwaysEdible;
	private int healthup;
	private ItemStack rcontainer;
	private ArrayList<Potions> effects;
	private boolean drink;
	private boolean eatable;

	private void ItemEatable(byte hunger, byte health, String potions,
			float satMod, String container, boolean alwayEble, boolean isDrink) {
		eatable = true;
		healthup = health;
		if (container.trim() != "") {
			if (container.startsWith("v.")) {
				if (container.equals("v.bucket")) {
					rcontainer = new ItemStack(Item.itemsList[325]);
				} else if (container.equals("v.bowl")) {
					rcontainer = new ItemStack(Item.itemsList[281]);
				}
			} else {
				Integer i = DB.getItem(container);
				if (i != null)
					rcontainer = new ItemStack(
							FarmingMod.items[DB.getMaxStackSize(i) - 1], 1, i);
			}
		}
		effects = Potions.setPotion(potions);
		this.itemUseDuration = 32;
		this.healAmount = hunger;
		this.saturationModifier = satMod;
		alwaysEdible = alwayEble;
		drink = isDrink;
	}

	@SideOnly(Side.CLIENT)
	public int[] getIcon() {
		return new int[] { iconXCoord, iconYCoord, iconResolution };
	}

	@SideOnly(Side.CLIENT)
	public String getTextureFile() {
		return textureFile;
	}

	public String getItemName() {
		return itemName;
	}

	public byte getMaxStackSize() {
		return stackSize;
	}

	public int getMaxItemUseDuration() {
		return itemUseDuration;
	}

	public EnumAction getItemUseAction() {
		return drink ? EnumAction.drink : EnumAction.eat;
	}

	public boolean isNeedFuel() {
		return needFuel;
	}

	public boolean isCookware() {
		return cookware;
	}

	public boolean isPlantable() {
		return plantable;
	}

	public boolean isEatable() {
		return eatable;
	}

	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer ep) {
		if (eatable && ep.canEat(alwaysEdible)) {
			ep.setItemInUse(is, getMaxItemUseDuration());
		}
		return is;
	}

	public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x,
			int y, int z, int direction, float par8, float par9, float par10) {
		if (plantable
				&& ep.canPlayerEdit(x, y + 1, z, direction, is)
				&& direction == 1
				&& world.isAirBlock(x, y + 1, z)
				&& DB.canPlaceBlockWithMetadataAt(world, x, y + 1, z, plantMeta)) {
			world.setBlockAndMetadataWithNotify(x, y + 1, z,
					FarmingMod.plant.blockID, plantMeta);
			--is.stackSize;
			return true;
		}
		return false;
	}

	public ItemStack onFoodEaten(ItemStack is, World world, EntityPlayer ep) {
		if (!ep.capabilities.isCreativeMode) {
			--is.stackSize;
		}
		ep.getFoodStats().addStats(healAmount, saturationModifier);
		if (!drink)
			world.playSoundAtEntity(ep, "random.burp", 0.5F,
					world.rand.nextFloat() * 0.1F + 0.9F);
		Potion(ep, world);
		if (rcontainer != null) {
			ep.inventory.addItemStackToInventory(rcontainer);
		}
		if (healthup > 0) {
			ep.heal(healthup);
		} else if (healthup < 0) {
			ep.attackEntityFrom(DamageSource.magic, Math.abs(healthup));
		}
		return is;
	}

	private void Potion(EntityPlayer ep, World world) {
		if (!world.isRemote) {
			for (Potions p : effects) {
				p.usePotion(ep, world);
			}
		}
	}
}
