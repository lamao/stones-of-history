/* 
 * SHEventDispatcherTest.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHEventDispatcherTest
{
	private class SHDummyEventHandler implements ISHEventHandler
	{
		public int eventsArrived = 0;
		@Override
		public void processEvent(SHEvent event)
		{
			eventsArrived++;
		}
	}
	
	
	private SHEventDispatcher _dispatcher = SHEventDispatcher.getInstance();
	
	@Before
	public void setUp()
	{
		_dispatcher.reset();
	}
	
	@Test
	public void testConstructor()
	{
		assertNotNull(_dispatcher);
		assertNotNull(_dispatcher.getHandlers());
		assertEquals(0, _dispatcher.getHandlers().size());
	}
	
	@Test
	public void testAddRemoveHandler()
	{
		SHDummyEventHandler handler = new SHDummyEventHandler(); 
		_dispatcher.addHandler("1", handler);
		assertEquals(1, _dispatcher.getHandlers().size());
		assertTrue(_dispatcher.hasHandler("1"));
		assertEquals(1, _dispatcher.getHandlers("1").size());
		assertEquals(1, _dispatcher.getHandledEvents().size());
		
		_dispatcher.addHandler("1", handler);
		assertEquals(1, _dispatcher.getHandlers().size());
		assertEquals(2, _dispatcher.getHandlers("1").size());
		assertEquals(1, _dispatcher.getHandledEvents().size());
		
		_dispatcher.addHandler("2", handler);
		assertEquals(2, _dispatcher.getHandlers().size());
		assertTrue(_dispatcher.hasHandler("2"));
		assertEquals(1, _dispatcher.getHandlers("2").size());
		assertEquals(2, _dispatcher.getHandledEvents().size());
		
		_dispatcher.removeHandler("1", handler);
		assertEquals(2, _dispatcher.getHandlers().size());
		assertTrue(_dispatcher.hasHandler("1"));
		assertEquals(1, _dispatcher.getHandlers("1").size());
		assertEquals(2, _dispatcher.getHandledEvents().size());
		
		_dispatcher.removeHandler("2", handler);
		assertEquals(1, _dispatcher.getHandlers().size());
		assertFalse(_dispatcher.hasHandler("2"));
		assertNull(_dispatcher.getHandlers("2"));
		assertEquals(1, _dispatcher.getHandledEvents().size());
		
		assertEquals(1, _dispatcher.getHandlers().size());
		assertFalse(_dispatcher.hasHandler("2"));
		assertNull(_dispatcher.getHandlers("2"));
		assertEquals(1, _dispatcher.getHandledEvents().size());
		
	}
	
	@Test
	public void testAddEvent()
	{
		SHDummyEventHandler handler = new SHDummyEventHandler();
		_dispatcher.addHandler("1", handler);
		
		_dispatcher.addEvent(new SHEvent("2", null, null));
		assertEquals(0, handler.eventsArrived);
		
		_dispatcher.addEvent(new SHEvent("1", null, null));
		assertEquals(1, handler.eventsArrived);
		
	}
	
	@Test
	public void testAddEventEx()
	{
		SHEventCounter counter = new SHEventCounter();
		_dispatcher.addHandler("all", counter);
		
		_dispatcher.addEventEx("1", null, "1", 2, "3", 4);
		assertNotNull(counter.lastEvent.params);
		assertTrue(2 == counter.lastEvent.params.size());
		assertEquals(2, counter.lastEvent.params.get("1"));
		assertEquals(4, counter.lastEvent.params.get("3"));
		
		_dispatcher.addEventEx("1", null, (Object[])null);
		assertNull(counter.lastEvent.params);
		
		_dispatcher.addEventEx("1", null, (Object)null);
		assertNull(counter.lastEvent.params);
		
		_dispatcher.addEventEx("1", null, "124");
		assertNull(counter.lastEvent.params);
	}
	
	@Test 
	public void testSupervisor()
	{
		SHDummyEventHandler handler = new SHDummyEventHandler();
		_dispatcher.addHandler("all", handler);
		
		_dispatcher.addEvent(new SHEvent("1", null, null));
		_dispatcher.addEvent(new SHEvent("2", null, null));
		assertEquals(2, handler.eventsArrived);
	}
	
}
