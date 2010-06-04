/* 
 * SHCapacityListTest.java Jun 4, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

/**
 * @author lamao
 *
 */
public class SHCapacityListTest
{

	@Test
	public void testRestrictions()
	{
		SHCapacityList<String> list = new SHCapacityList<String>(2);
		
		list.add("1");
		list.add("2");
		assertEquals(2, list.size());
		
		list.add("3");
		assertEquals(2, list.size());
		assertEquals("3", list.getLast());
		
		list.add(0, "4");
		assertEquals(2, list.size());
		assertEquals("4", list.get(0));
		
		list.addFirst("5");
		assertEquals(2, list.size());
		assertEquals("5", list.getFirst());
		
		list.addLast("6");
		assertEquals(2, list.size());
		assertEquals("6", list.getLast());
		
		assertFalse(list.addAll(new LinkedList<String>()));
		assertFalse(list.addAll(0, new LinkedList<String>()));
	}
	
	@Test
	public void testChangeCapacity()
	{
		SHCapacityList<String> list = new SHCapacityList<String>(2);
		
		list.add("1");
		list.add("2");
		list.add("3");
		list.setCapacity(4);
		list.add("4");
		list.add("5");
		list.add("6");		
		assertEquals(4, list.size());
		
		list.setCapacity(3);
		assertEquals(3, list.size());
		
	}
	
}
