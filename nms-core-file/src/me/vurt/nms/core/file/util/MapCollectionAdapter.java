package me.vurt.nms.core.file.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Map到Collection接口的适配器，暂时只支持{@link Collection#add(Object)}操作
 * @author yanyl
 *
 */
public class MapCollectionAdapter<K,V> extends AbstractCollection<Entry<K, V>>{
	private Map<K, V> map;
	
	public MapCollectionAdapter(Map<K, V> map){
		this.map=map;
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#add(java.lang.Object)
	 */
	@Override
	public boolean add(Entry<K, V> e) {
		map.put(e.getKey(), e.getValue());
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#iterator()
	 */
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return map.entrySet().iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		return map.size();
	}


}
