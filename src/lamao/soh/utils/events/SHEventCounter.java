/* 
 * SHEventCounter.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import java.util.HashMap;
import java.util.Map;

import lamao.soh.core.eventhandlers.SHAbstractEventHandler;

/**
 * Simple event handler that counts all arrived events and store last event.
 * @author lamao
 *
 */
public class SHEventCounter extends SHAbstractEventHandler
{
	public Map<String, Integer> numEvents = new HashMap<String, Integer>();
	public SHEvent lastEvent = null;

	public SHEventCounter(SHEventDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void processEvent(SHEvent event)
	{
		Integer count = numEvents.get(event.getType());
		if (count == null)
		{
			count = 0;
		}
		numEvents.put(event.getType(), count + 1);
		lastEvent = event;
	}
	
	public int getNumEvents(String type)
	{
		Integer num = numEvents.get(type);
		return num == null ? 0 : num;
	}
	
	public void reset()
	{
		numEvents.clear();
		lastEvent = null;
	}

}
