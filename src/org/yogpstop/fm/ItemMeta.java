package org.yogpstop.fm;

import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemMeta {
    public static final ItemMeta defaultValue = new ItemMeta();

    private ItemMeta() {
        ItemBase("default",(byte) 64,"default");
    }

    public ItemMeta(HashMap<String, String> data) {
        if (!data.containsKey("stacksize"))
            data.put("stacksize", "64");
        ItemBase(data.get("name"), Byte.parseByte(data.get("stacksize")), data.get("icon"));

        if (data.containsKey("cookware")) {
            if (!data.containsKey("isneedfuel"))
                data.put("isneedfuel", "false");
            ItemCookware(Boolean.parseBoolean(data.get("isneedfuel")));
        }

        if (data.containsKey("eatable")) {
            if (!data.containsKey("hunger"))
                data.put("hunger", "0");
            if (!data.containsKey("health"))
                data.put("health", "0");
            if (!data.containsKey("saturationmodifier"))
                data.put("saturationmodifier", "60");
            if (!data.containsKey("container"))
                data.put("container", "");
            if (!data.containsKey("potions"))
                data.put("potions", "");
            if (!data.containsKey("isdrink"))
                data.put("isdrink", "false");
            if (!data.containsKey("alwaysedible"))
                data.put("alwaysedible", "false");
            ItemEatable(Byte.parseByte(data.get("hunger")), Byte.parseByte(data.get("health")), data.get("potions"),
                    Integer.parseInt(data.get("saturationmodifier")), data.get("container"), Boolean.parseBoolean(data.get("alwaysedible")),
                    Boolean.parseBoolean(data.get("isdrink")));
        }

        if (data.containsKey("plantable"))
            ItemPlantable(DB.getBlock(data.get("plantblock")));
    }

    private int plantMeta;
    private boolean plantable;

    private void ItemPlantable(int plant) {
        plantMeta = plant;
        plantable = true;
    }

    private String itemName;
    private byte stackSize;
    private Icon icon;

    private void ItemBase(String name, byte stacksize, String textureFile) {
        itemName = name;
        stackSize = stacksize;
        icon = FarmingMod.texMap.func_94245_a(textureFile);
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

    private void ItemEatable(byte hunger, byte health, String potions, float satMod, String container, boolean alwayEble, boolean isDrink) {
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
                    rcontainer = new ItemStack(FarmingMod.items[DB.getMaxStackSize(i) - 1], 1, i);
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
    public Icon getIcon() {
        return icon;
    }

    public String getUnlocalizedName() {
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

    public boolean onItemUse(ItemStack is, EntityPlayer ep, World world, int x, int y, int z, int direction, float par8, float par9, float par10) {
        if (plantable && ep.canPlayerEdit(x, y + 1, z, direction, is) && direction == 1 && world.isAirBlock(x, y + 1, z)
                && DB.canPlaceBlockWithMetadataAt(world, x, y + 1, z, plantMeta)) {
            world.setBlockAndMetadataWithNotify(x, y + 1, z, FarmingMod.plant.blockID, plantMeta, 3);
            --is.stackSize;
            return true;
        }
        return false;
    }

    public ItemStack onEaten(ItemStack is, World world, EntityPlayer ep) {
        if (!ep.capabilities.isCreativeMode) {
            --is.stackSize;
        }
        ep.getFoodStats().addStats(healAmount, saturationModifier);
        if (!drink)
            world.playSoundAtEntity(ep, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
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
