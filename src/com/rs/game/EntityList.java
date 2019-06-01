package com.rs.game;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EntityList<T extends Entity> extends AbstractCollection<T> {
	public Object[] entities;
	public Set<Integer> indicies = new HashSet<Integer>();
	public int capacity;
	private final Object lock = new Object();

	public EntityList(int capacity) {
		entities = new Object[capacity];
		this.capacity = capacity;
	}

	@Override
	public boolean add(T entity) {
		synchronized (lock) {
			final int slot = getEmptySlot();
			if (slot == -1) {
				return false;
			}
			add(entity, slot);
			return true;
		}
	}

	public void add(T entity, int index) {
		if (entities[index] != null) {
			return;
		}
		entities[index] = entity;
		entity.setIndex(index);
		indicies.add(index);
	}

	public boolean contains(T entity) {
		return indexOf(entity) > -1;
	}

	@SuppressWarnings("unchecked")
	public T get(int index) {
		synchronized (lock) {
			if (index >= entities.length)
				return null;
			return (T) entities[index];
		}
	}

	public int getEmptySlot() {
		for (int i = 1; i < entities.length; i++) {
			if (entities[i] == null) {
				return i;
			}
		}
		return -1;
	}

	public int indexOf(T entity) {
		for (final int index : indicies) {
			if (entities[index].equals(entity)) {
				return index;
			}
		}
		return -1;
	}

	@Override
	public Iterator<T> iterator() {
		synchronized (lock) {
			return new EntityListIterator<T>(entities, indicies, this);
		}
	}

	@SuppressWarnings("unchecked")
	public T remove(int index) {
		synchronized (lock) {
			final Object temp = entities[index];
			entities[index] = null;
			indicies.remove(index);
			return (T) temp;
		}
	}

	public void remove(T entity) {
		synchronized (lock) {
			entities[entity.getIndex()] = null;
			indicies.remove(entity.getIndex());
		}
	}

	@Override
	public int size() {
		
		return indicies.size();
	}

}