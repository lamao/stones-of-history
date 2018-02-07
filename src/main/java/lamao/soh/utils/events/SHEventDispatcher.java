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

import lamao.soh.core.SHUtils;


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
	
	private List<SHEvent> _timeEvents = new LinkedList<SHEvent>();
	
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
		deleteEvents();
	}
	
	/** Deletes all events but don't touch handlers */
	public void deleteEvents()
	{
		_timeEvents.clear();
	}
	
	/**
	 * Adds event. If event.time >= 0 than this is simple event and it will be sent
	 * immediately. If event.tim < 0 this is time event and it will be added to
	 * event queue.
	 * @param event
	 */
	public void addEvent(SHEvent event)
	{
		if (event.getTime() < 0)
		{
			addSimpleEvent(event);
		}
		else 
		{
			addTimeEvent(event);
		}
	}
	
	private void addSimpleEvent(SHEvent event)
	{
		notifySupervisors(event);
		List<ISHEventHandler> handlers = getHandlers(event.getType());
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
	
	public void addEvent(String type, Object sender) 
	{
		addEvent(type, sender, null);
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
	
	public void addEvent(String type, Object sender, float time, Map<String, Object> params)
	{
		addEvent(new SHEvent(type, sender, params, time));
	}
	
	public void addEventExWithTime(String type, Object sender, float time, Object... params)
	{
		addEvent(type, sender, time, SHUtils.buildEventMap(params));
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
	
	/**
	 * Sets handlers map for this event dispatcher. Replaces current handlers.
	 * @param handlers - handlers to set
	 */
	public void setHandlers(Map<String, List<ISHEventHandler>> handlers)
	{
		this._handlers = handlers;
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
	private void addTimeEvent(SHEvent event)
	{
		Iterator<SHEvent> it = _timeEvents.iterator();
		SHEvent e = null;
		while (it.hasNext())
		{
			e = it.next();
			if (e.getTime() >= event.getTime())
			{
				_timeEvents.add(_timeEvents.indexOf(e), event);
				return;
			}
		}

		_timeEvents.add(event);
	}
	
//	private void addTimeEvent(String type, Object sender, 
//			Map<String, Object> params, int time)
//	{
//		addTimeEvent(new SHEvent(type, sender, params, time));
//	}
//	
//	private void addTimeEvent(String type, Object sender, float time, Object... params)
//	{
//		addTimeEvent(new SHEvent(type, sender, 
//				SHUtils.buildEventMap(params), time));
//	}
	
	int getNumberOfTimeEvents()
	{
		return _timeEvents.size();
	}
	
	/**
	 * Adds <code>time</code> to the time of the first event of type 
	 * <code>type</code>. 
	 * @param type
	 * @param time
	 */
	public void prolongTimeEvent(String type, float time)
	{
		Iterator<SHEvent> it = _timeEvents.iterator();
		// find event of such type
		SHEvent event = null;
		while (it.hasNext())
		{
			event = it.next();
			if (event.getType().equals(type))
			{
				event.setTime(event.getTime() + time);
				break;
			}
		}

		// move event according its new time
		if (it.hasNext())
		{
			SHEvent laterEvent = null;
			while (it.hasNext())
			{
				laterEvent = it.next();
				if (laterEvent.getTime() >= event.getTime())
				{
					_timeEvents.remove(event);
					_timeEvents.add(_timeEvents.indexOf(laterEvent), event);
					return;
				}
			}
			_timeEvents.remove(event);
			_timeEvents.add(event);
		}
	}
	
	public boolean hasTimeEvent(String type)
	{
		for (SHEvent event : _timeEvents)
		{
			if (event.getType().equals(type))
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
		for (SHEvent event : _timeEvents)
		{
			event.setTime(event.getTime() - time);
		}
	}
	
	/**
	 * Send expired events.
	 */
	private void checkEvents()
	{
		List<SHEvent> eventsToSent = new LinkedList<SHEvent>();
		SHEvent event = null;
		do
		{
			if (!_timeEvents.isEmpty())
			{
				event = _timeEvents.get(0);
				if (event.getTime() <= 0)
				{
					eventsToSent.add(event);
					_timeEvents.remove(event);
				}
			}
		}
		while (!_timeEvents.isEmpty() && event.getTime() <= 0);
		
		if (!eventsToSent.isEmpty())
		{
			for (SHEvent e : eventsToSent)
			{
				addSimpleEvent(e);
			}
		}
	}
	
}
