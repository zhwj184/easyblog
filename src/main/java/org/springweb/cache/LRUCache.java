package org.springweb.cache;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LRUCache<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = -3923317621052085848L;
	private int maxCapacity;
	private Lock lock = new ReentrantLock();
	
	public LRUCache(int maxCapacity) { 
		super(maxCapacity + 1, 1f, true);// make it do not rehash ever,
		this.maxCapacity  = maxCapacity ;
	}
	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() > this.maxCapacity;
	}
	
	@Override
	public V put(K key, V value) {
		try {
			lock.lock();
			return super.put(key, value);
		} finally {  
            lock.unlock();  
        }  
	}
	
	@Override
	public V get(Object key) {
		try {
			lock.lock();
			return super.get(key);
		} finally {  
            lock.unlock();  
        }  
	}
	
	
}
