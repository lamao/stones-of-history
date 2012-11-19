/* 
 * SHCollisionTaskTest.java 30.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author lamao
 *
 */
public class SHCollisionTaskTest
{
	@Test
	public void testEquals()
	{
		SHCollisionTask task1 = new SHCollisionTask("1", "2", true);
		SHCollisionTask task2 = new SHCollisionTask("1", "2", true);
		
		assertEquals(task1, task1);
		assertFalse(task1.equals(new String("sah")));
		assertEquals(task1, task2);
		assertEquals(task2, task1);
		
		task2.setCheckTris(false);
		assertEquals(task1, task2);
		assertEquals(task2, task1);
		
		task2.setSourceType("3");
		assertFalse(task1.equals(task2));
		assertFalse(task2.equals(task1));
		
		task2.setDestType("3");
		assertFalse(task1.equals(task2));
		assertFalse(task2.equals(task1));
		
		task2.setSourceType("2");
		assertFalse(task1.equals(task2));
		
		task2.setSourceType(null);
		assertFalse(task1.equals(task2));
		assertFalse(task2.equals(task1));
		
		task2.setDestType(null);
		assertFalse(task1.equals(task2));
		assertFalse(task2.equals(task1));
		
		task1.setSourceType(null);
		assertFalse(task1.equals(task2));
		assertFalse(task2.equals(task1));
		
		task1.setDestType(null);
		assertFalse(task1.equals(task2));
		assertFalse(task2.equals(task1));
		
	}

}
