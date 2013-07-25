package org.yogpstop.fm;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.ItemStack;

public class Drop {
	private byte[] min;
	private byte[] max;
	private short itemID;

	public Drop(byte[] Amin, byte[] Amax, short iid) {
		this.min = Amin;
		this.max = Amax;
		this.itemID = iid;
	}

	public Drop(byte a, byte from, byte to, byte Amin, byte Amax, short iid) {
		this.min = new byte[a];
		this.max = new byte[a];
		for (int i = 0; i < a; i++) {
			if (from - 1 <= i && i < to) {
				this.min[i] = Amin;
				this.max[i] = Amax;
			} else {
				this.min[i] = 0;
				this.max[i] = 0;
			}
		}
		this.itemID = iid;
	}

	public Drop(byte a, ArrayList<Byte> from, ArrayList<Byte> to, ArrayList<Byte> Amin, ArrayList<Byte> Amax, short iid) throws Exception {
		if (from.size() == to.size() && Amin.size() == Amax.size() && to.size() == Amin.size()) {
			for (int i = 0; i < from.size(); i++) {
				for (int j = 0; j < a; j++) {
					if (from.get(i) - 1 <= j && j < to.get(i)) {
						this.min[j] = Amin.get(i);
						this.max[j] = Amax.get(i);
					} else {
						this.min[j] = 0;
						this.max[j] = 0;
					}
				}
			}
		} else {
			throw new Exception("Value is invalid.");
		}
		this.itemID = iid;
	}

	public ItemStack getDrop(byte a, Random r) {
		return new ItemStack(this.itemID, r.nextInt(this.max[a] - this.min[a]) + this.min[a], 0);
	}

	public short getItemID() {
		return this.itemID;
	}
}
