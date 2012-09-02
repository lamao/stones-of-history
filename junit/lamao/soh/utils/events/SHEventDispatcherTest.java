/* 
 * SHEventDispatcherTest.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;


import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

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
	
	
	private SHEventDispatcher _dispatcher = null;
	
	@BeforeMethod
	public void setUp()
	{
		_dispatcher = new SHEventDispatcher();
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
	
	@Test
	public void testTimeEvents() throws InterruptedException
	{
		SHEventCounter counter = new SHEventCounter();
		_dispatcher.addHandler("all", counter);
		
		_dispatcher.addEvent(new SHEvent("type", "sender", null, .4f));
		_dispatcher.addEvent(new SHEvent("type1", "sender", null, .2f));
		_dispatcher.addEvent(new SHEvent("type2", null, null, .3f));
		_dispatcher.update(0.1f);
		assertEquals(0, counter.getNumEvents("type"));
		assertEquals(0, counter.getNumEvents("type1"));
		assertEquals(0, counter.getNumEvents("type2"));
		_dispatcher.update(0.15f);
		GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).execute();
		assertEquals(0, counter.getNumEvents("type"));
		assertEquals(1, counter.getNumEvents("type1"));
		assertEquals(0, counter.getNumEvents("type2"));
		_dispatcher.update(0.1f);
		GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).execute();
		assertEquals(0, counter.getNumEvents("type"));
		assertEquals(1, counter.getNumEvents("type1"));
		assertEquals(1, counter.getNumEvents("type2"));
		_dispatcher.update(0.1f);
		GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).execute();
		assertEquals(1, counter.getNumEvents("type"));
		assertEquals(1, counter.getNumEvents("type1"));
		assertEquals(1, counter.getNumEvents("type2"));
	}
	
	@Test
	public void testReset() throws InterruptedException
	{
		SHDummyEventHandler handler = new SHDummyEventHandler();
		_dispatcher.addHandler("all", handler);
		
		_dispatcher.addEvent("type", "name", null);
		_dispatcher.addEventEx("type1", "name1", null, 100);
		_dispatcher.reset();
		
		assertEquals(0, _dispatcher.getHandlers().size());
		assertEquals(0, _dispatcher.getNumberOfTimeEvents());
		
		handler.eventsArrived = 0;
		_dispatcher.addHandler("all", handler);
		_dispatcher.update(0.15f);		
		assertEquals(0, handler.eventsArrived);
	}
	
	@Test
	public void testDeleteEvents()
	{
		SHDummyEventHandler handler = new SHDummyEventHandler();
		_dispatcher.addHandler("all", handler);
		
		_dispatcher.addEvent("type", "name", null);
		_dispatcher.addEventEx("type1", "name1", null, 100);
		_dispatcher.deleteEvents();		
		assertEquals(0, _dispatcher.getNumberOfTimeEvents());
		
		handler.eventsArrived = 0;
		_dispatcher.update(0.15f);
		assertEquals(0, handler.eventsArrived);
	}
	
	@Test
	public void testProlongEvent() throws InterruptedException
	{
		SHEventCounter counter = new SHEventCounter();
		_dispatcher.addHandler("all", counter);
		
		_dispatcher.addEvent("type", "sender", 0.3f, null);
		_dispatcher.addEvent("type1", "sender", 0.3f, null);
		_dispatcher.update(0.1f);
		
		_dispatcher.prolongTimeEvent("type1", .2f);
		_dispatcher.update(0.25f);
		
		assertEquals(1, counter.getNumEvents("type"));
		assertEquals(0, counter.getNumEvents("type1"));
		
		_dispatcher.update(0.2f);		
		assertEquals(1, counter.getNumEvents("type"));
		assertEquals(1, counter.getNumEvents("type1"));
	}
	
	@Test
	public void testGetTimeEvent()
	{
		_dispatcher.addEvent("type1", "sender", 0.2f, null);
		assertTrue(_dispatcher.hasTimeEvent("type1"));
		assertFalse(_dispatcher.hasTimeEvent("type2"));
	}
	
}
