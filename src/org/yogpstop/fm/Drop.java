package org.yogpstop.fm;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.ItemStack;

public class Drop {
	private byte[] min;
	private byte[] max;
	private short itemID;

	public Drop(byte[] Amin, byte[] Amax, short iid) {
		min = Amin;
		max = Amax;
		itemID = iid;
	}

	public Drop(byte a, byte from, byte to, byte Amin, byte Amax, short iid) {
		min = new byte[a];
		max = new byte[a];
		for (int i = 0; i < a; i++) {
			if (from - 1 <= i && i < to) {
				min[i] = Amin;
				max[i] = Amax;
			} else {
				min[i] = 0;
				max[i] = 0;
			}
		}
		itemID = iid;
	}

	public Drop(byte a, ArrayList<Byte> from, ArrayList<Byte> to,
			ArrayList<Byte> Amin, ArrayList<Byte> Amax, short iid)
			throws Exception {
		if (from.size() == to.size() && Amin.size() == Amax.size()
				&& to.size() == Amin.size()) {
			for (int i = 0; i < from.size(); i++) {
				for (int j = 0; j < a; j++) {
					if (from.get(i) - 1 <= j && j < to.get(i)) {
						min[j] = Amin.get(i);
						max[j] = Amax.get(i);
					} else {
						min[j] = 0;
						max[j] = 0;
					}
				}
			}
		} else {
			throw new Exception("Value is invalid.");
		}
		itemID = iid;
	}

	public ItemStack getDrop(byte a, Random r) {
		return new ItemStack(itemID, r.nextInt(max[a] - min[a]) + min[a], 0);
	}

	public short getItemID() {
		return itemID;
	}
}
