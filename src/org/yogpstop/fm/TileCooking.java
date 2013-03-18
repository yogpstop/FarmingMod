package org.yogpstop.fm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileCooking extends TileEntity implements IInventory {
	private ItemStack[] chestContents = new ItemStack[310];// %10=slot

	@Override
	public int getSizeInventory() {
		return 310;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.chestContents[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.chestContents[par1] != null) {
			ItemStack var3;

			if (this.chestContents[par1].stackSize <= par2) {
				var3 = this.chestContents[par1];
				this.chestContents[par1] = null;
				this.onInventoryChanged();
				return var3;
			} else {
				var3 = this.chestContents[par1].splitStack(par2);

				if (this.chestContents[par1].stackSize == 0) {
					this.chestContents[par1] = null;
				}

				this.onInventoryChanged();
				return var3;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.chestContents[par1] != null) {
			ItemStack var2 = this.chestContents[par1];
			this.chestContents[par1] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.chestContents[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "container.cooking";
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.chestContents = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < this.chestContents.length) {
				this.chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.chestContents.length; ++var3) {
			if (this.chestContents[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.chestContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(
				(double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
				(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

    @Override
    public boolean func_94042_c() {
        return false;
    }

    @Override
    public boolean func_94041_b(int i, ItemStack itemstack) {
        return true;
    }

}
