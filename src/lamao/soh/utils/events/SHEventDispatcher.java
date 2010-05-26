/* 
 * SHEventDispatcher.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import lamao.soh.core.SHUtils;

import com.jme.util.GameTaskQueueManager;

/**
 * Dispatcher for all game events. Plays as a singleton, but clients can create
 * have their own local dispatchers.
 * @author lamao
 *
 */
public class SHEventDispatcher
{
	private Map<String, List<ISHEventHandler>> _handlers = 
			new TreeMap<String, List<ISHEventHandler>>();
	
	private List<SHTimeEvent> _timedEvents = new LinkedList<SHTimeEvent>();
	
	public SHEventDispatcher()
	{		
	}
	
	Map<String, List<ISHEventHandler>> getHandlers()
	{
		return _handlers;
	}
	
	List<ISHEventHandler> getHandlers(String type)
	{
		return _handlers.get(type);
	}
	
	public Set<String> getHandledEvents()
	{
		return _handlers.keySet();
	}
	
	/** Resets dispatcher by removing all events and handlers */
	public void reset()
	{
		_handlers.clear();
		_timedEvents.clear();
	}
	
	public void addEvent(SHEvent event)
	{
		notifySupervisors(event);
		List<ISHEventHandler> handlers = getHandlers(event.type);
		if (handlers != null)
		{
			handlers = new LinkedList<ISHEventHandler>(handlers);
			for (ISHEventHandler handler : handlers)
			{
				handler.processEvent(event);
			}
		}
	}
	
	private void notifySupervisors(SHEvent event)
	{
		List<ISHEventHandler> handlers = getHandlers("all");
		if (handlers != null)
		{
			for (ISHEventHandler handler : handlers)
			{
				handler.processEvent(event);
			}
		}
	}
	
	public void addEvent(String type, Object sender, Map<String, Object> params)
	{
		addEvent(new SHEvent(type, sender, params));
	}
	
	/**
	 * Add event. <code>params</code> must have length = n pow 2 and must
	 * consist of pairs <code>[String key, Object value, ..., 
	 * String key, Object value]</code>
	 * @param type
	 * @param sender
	 * @param params
	 */
	public void addEventEx(String type, Object sender, Object... params)
	{
		
		addEvent(type, sender, SHUtils.buildEventMap(params));
	}
	
	/**
	 * Adding handler for 'all' event means adding supervisor, that will accept
	 * all events. Supervisors accepts all events before they are dispatching to
	 * other receivers.
	 */ 
	public void addHandler(String type, ISHEventHandler handler)
	{
		List<ISHEventHandler> handlers = getHandlers(type);
		if (handlers == null)
		{
			handlers = new LinkedList<ISHEventHandler>();
			_handlers.put(type, handlers);
		}
		handlers.add(handler);
	}
	
	public void removeHandler(String type, ISHEventHandler handler)
	{
		List<ISHEventHandler> handlers = getHandlers(type);
		if (handlers != null)
		{
			handlers.remove(handler);
			if (handlers.size() == 0)
			{
				_handlers.remove(type);
			}
		}
	}
	
	public boolean hasHandler(String type)
	{
		List<ISHEventHandler> handlers = getHandlers(type);
		return handlers != null && handlers.size() > 0;
	}
	
	
	/**
	 * Add time event. All events are sorted in descending order of their
	 * <code>time</code> values.
	 * @param event
	 */
	public void addTimeEvent(SHTimeEvent event)
	{
		Iterator<SHTimeEvent> it = _timedEvents.iterator();
		SHTimeEvent e = null;
		while (it.hasNext())
		{
			e = it.next();
			if (e.time >= event.time)
			{
				_timedEvents.add(_timedEvents.indexOf(e), event);
				return;
			}
		}

		_timedEvents.add(event);
	}
	
	public void addTimeEvent(String type, Object sender, 
			Map<String, Object> params, int time)
	{
		addTimeEvent(new SHTimeEvent(type, sender, params, time));
	}
	
	public void addTimeEvent(String type, Object sender, float time, Object... params)
	{
		addTimeEvent(new SHTimeEvent(type, sender, 
				SHUtils.buildEventMap(params), time));
	}
	
	int getNumberOfTimeEvents()
	{
		return _timedEvents.size();
	}
	
	/**
	 * Adds <code>time</code> to the time of the first event of type 
	 * <code>type</code>. 
	 * @param type
	 * @param time
	 */
	public void prolongTimeEvent(String type, float time)
	{
		Iterator<SHTimeEvent> it = _timedEvents.iterator();
		// find event of such type
		SHTimeEvent event = null;
		while (it.hasNext())
		{
			event = it.next();
			if (event.type.equals(type))
			{
				event.time += time;
				break;
			}
		}

		// move event according its new time
		if (it.hasNext())
		{
			SHTimeEvent laterEvent = null;
			while (it.hasNext())
			{
				laterEvent = it.next();
				if (laterEvent.time >= event.time)
				{
					_timedEvents.remove(event);
					_timedEvents.add(_timedEvents.indexOf(laterEvent), event);
					return;
				}
			}
			_timedEvents.remove(event);
			_timedEvents.add(event);
		}
	}
	
	public boolean hasTimeEvent(String type)
	{
		for (SHTimeEvent event : _timedEvents)
		{
			if (event.type.equals(type))
			{
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Updates all time events.
	 * @param tpf - time since last frame
	 */
	public void update(float tpf)
	{
		updateEventTime(tpf);
		checkEvents();
	}
	
	/** Decrease given time for all time events.		  
	 * @param time
	 */
	private void updateEventTime(float time)
	{
		for (SHTimeEvent event : _timedEvents)
		{
			event.time -= time;
		}
	}
	
	/**
	 * Send expired events.
	 */
	private void checkEvents()
	{
		List<SHEvent> eventsToSent = new LinkedList<SHEvent>();
		SHTimeEvent event = null;
		do
		{
			if (!_timedEvents.isEmpty())
			{
				event = _timedEvents.get(0);
				if (event.time <= 0)
				{
					eventsToSent.add(event);
//					addEvent(event);
					_timedEvents.remove(event);
				}
			}
		}
		while (!_timedEvents.isEmpty() && event.time <= 0);
		
		if (!eventsToSent.isEmpty())
		{
			for (SHEvent e : eventsToSent)
			{
				addEvent(e);
			}
		}
	}
	
}
