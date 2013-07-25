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
