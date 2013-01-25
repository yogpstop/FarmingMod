package org.yogpstop.fm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;

public class ContainerCooking extends Container {
	private World world;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private IInventory playerInventory;
	private EntityPlayer player;

	public ContainerCooking(EntityPlayer player, World world, int x, int y,
			int z) {
		this.world = world;
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.player = player;
		this.playerInventory = player.inventory;
		for (int slotIndex = 0; slotIndex < 5; ++slotIndex) {
			addSlotToContainer(new SlotCooking(
					(IInventory) this.world.getBlockTileEntity(x, y, z),
					slotIndex, 8, 27 + slotIndex * 20));
		}
		for (int slotIndex = 0; slotIndex < 5; ++slotIndex) {
			addSlotToContainer(new SlotCooking(
					(IInventory) this.world.getBlockTileEntity(x, y, z),
					slotIndex + 5, 152, 27 + slotIndex * 20));
		}
		for (int rows = 0; rows < 3; ++rows) {
			for (int slotIndex = 0; slotIndex < 9; ++slotIndex) {
				addSlotToContainer(new Slot(playerInventory, slotIndex + rows
						* 9 + 9, 8 + slotIndex * 18, 140 + rows * 18));
			}
		}
		for (int slotIndex = 0; slotIndex < 9; ++slotIndex) {
			addSlotToContainer(new Slot(playerInventory, slotIndex,
					8 + slotIndex * 18, 198));
		}
	}

	public void onButtonPushed(int i) {
		if (((IInventory) this.world.getBlockTileEntity(xCoord, yCoord, zCoord))
				.getStackInSlot(i) == null)
			return;
		player.openGui(FarmingMod.instance, i, world, xCoord, yCoord, zCoord);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return this.world.getBlockId(this.xCoord, this.yCoord, this.zCoord) != FarmingMod.cookingTable.blockID ? false
				: entityPlayer.getDistanceSq((double) this.xCoord + 0.5D,
						(double) this.yCoord + 0.5D,
						(double) this.zCoord + 0.5D) <= 64.0D;
	}
}
