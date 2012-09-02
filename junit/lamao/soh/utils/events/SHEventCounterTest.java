/* 
 * SHEventCounterTest.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import java.util.HashMap;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
/**
 * @author lamao
 *
 */
public class SHEventCounterTest
{
	@Test
	public void testEventCounter()
	{
		SHEventCounter counter = new SHEventCounter();
		
		counter.processEvent(new SHEvent("1", null, null));
		counter.processEvent(new SHEvent("1", null, null));
		counter.processEvent(new SHEvent("2", null, null));
		counter.processEvent(new SHEvent("3", null, null));
		assertNull(counter.lastEvent.params);
		counter.processEvent(new SHEvent("1", null, new HashMap<String, Object>()));
		assertEquals(0, counter.lastEvent.params.size());
		
		assertTrue(3 == counter.numEvents.get("1"));
		assertEquals(3, counter.getNumEvents("1"));
		assertTrue(1 == counter.numEvents.get("2"));
		assertEquals(1, counter.getNumEvents("2"));
		assertTrue(1 == counter.numEvents.get("3"));
		assertEquals(1, counter.getNumEvents("3"));
		assertNull(counter.numEvents.get("4"));
		assertEquals(0, counter.getNumEvents("4"));
		
		counter.reset();
		assertNull(counter.numEvents.get("1"));
		assertNull(counter.numEvents.get("2"));
		assertNull(counter.numEvents.get("3"));
		assertNull(counter.numEvents.get("4"));
		
		
	}
}
