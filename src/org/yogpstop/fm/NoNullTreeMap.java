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

import java.util.TreeMap;

public class NoNullTreeMap<K, V> extends TreeMap<K, V> {
	private static final long serialVersionUID = 1L;
	private final V dfl;

	public NoNullTreeMap(V def) {
		super();
		if (def == null) throw new NullPointerException("NoNullTreeMap cannot have null default value.");
		this.dfl = def;
	}

	@Override
	public V get(Object key) {
		V s = super.get(key);
		if (s == null) return this.dfl;
		return s;

	}
}
