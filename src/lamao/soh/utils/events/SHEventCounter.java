/* 
 * SHEventCounter.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple event handler that counts all arrived events and store last event.
 * @author lamao
 *
 */
public class SHEventCounter implements ISHEventHandler
{
	public Map<String, Integer> numEvents = new HashMap<String, Integer>();
	public SHEvent lastEvent = null;
	
	@Override
	public void processEvent(SHEvent event)
	{
		Integer count = numEvents.get(event.type);
		if (count == null)
		{
			count = 0;
		}
		numEvents.put(event.type, count + 1);
		lastEvent = event;
	}
	
	public void reset()
	{
		numEvents.clear();
		lastEvent = null;
	}

}
