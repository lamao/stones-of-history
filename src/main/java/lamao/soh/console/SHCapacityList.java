/* 
 * SHCapacityList.java Jun 4, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Capacity restricted list. If new item can't be added, last element is removed.
 * @author lamao
 *
 */
public class SHCapacityList<E> extends LinkedList<E>
{
	private static final long serialVersionUID = 1L;
	
	private int _capacity = 10;
	
	public SHCapacityList(int capacity)
	{
		super();
		_capacity = capacity;
	}
	
	public int getCapacity()
	{
		return _capacity;
	}

	public void setCapacity(int capacity)
	{
		_capacity = capacity;
		while (size() > _capacity)
		{
			removeLast();
		}
	}



	/** Checks for ability to add new item. If list is already full, last item
	 * is removed.
	 */
	private void checkForSize()
	{
		if (size() >= _capacity)
		{
			remove(size() - 1);
		}
	}
	
	public boolean add(E e) 
	{
		checkForSize();
		return super.add(e);		
	};
	
	public void add(int index, E element) 
	{
		checkForSize();
		super.add(index, element);
	};
	
	public void addFirst(E e)
	{
		checkForSize();
		super.addFirst(e);
	}
	
	public void addLast(E e) 
	{
		checkForSize();
		super.addLast(e);
	};
	
	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		return false;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		return false;
	}

}
