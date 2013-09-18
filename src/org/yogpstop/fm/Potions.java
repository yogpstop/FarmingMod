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

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Potions {
	private byte effect;
	private byte level;
	private int TickTime;
	private float prob;

	public Potions(byte a, byte b, int c, float d) {
		this.effect = a;
		this.level = b;
		this.TickTime = c;
		this.prob = d;
	}

	public void usePotion(EntityPlayer e, World world) {
		if (world.rand.nextFloat() < this.prob) e.addPotionEffect(new PotionEffect(this.effect, this.TickTime, this.level));
	}

	public static ArrayList<Potions> setPotion(String potion) {
		ArrayList<Potions> effects = new ArrayList<Potions>();
		String[] potions = potion.split(",");
		for (String effect : potions) {
			String[] parse = effect.split("-");
			String name = parse[0];
			byte level = Byte.parseByte(parse[1]);
			short time = Short.parseShort(parse[2]);
			float probably = Float.parseFloat(parse[3]) / 100;
			byte type = 0;
			if (name.equals("movespeed")) {
				type = 1;
			} else if (name.equals("moveslowdown")) {
				type = 2;
			} else if (name.equals("digspeed")) {
				type = 3;
			} else if (name.equals("digslowdown")) {
				type = 4;
			} else if (name.equals("damageboost")) {
				type = 5;
			} else if (name.equals("heal")) {
				type = 6;
			} else if (name.equals("harm")) {
				type = 7;
			} else if (name.equals("jump")) {
				type = 8;
			} else if (name.equals("confusion")) {
				type = 9;
			} else if (name.equals("regeneration")) {
				type = 10;
			} else if (name.equals("resistance")) {
				type = 11;
			} else if (name.equals("fireresistance")) {
				type = 12;
			} else if (name.equals("waterbreathing")) {
				type = 13;
			} else if (name.equals("invisibility")) {
				type = 14;
			} else if (name.equals("blindness")) {
				type = 15;
			} else if (name.equals("nightvision")) {
				type = 16;
			} else if (name.equals("hunger")) {
				type = 17;
			} else if (name.equals("weakness")) {
				type = 18;
			} else if (name.equals("poison")) {
				type = 19;
			} else if (name.equals("wither")) {
				type = 20;

			}
			if (type > 0 && time > 0 && level > 0) effects.add(new Potions(type, (byte) (level - 1), time * 20, probably));
		}
		return effects;
	}
}
