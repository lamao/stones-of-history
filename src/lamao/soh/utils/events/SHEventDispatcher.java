/* 
 * SHEventDispatcher.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Dispatcher for all game events. Plays as a singleton.
 * @author lamao
 *
 */
public class SHEventDispatcher
{
	
	private static SHEventDispatcher _instance = null;
	
	private Map<String, List<ISHEventHandler>> _handlers = 
			new TreeMap<String, List<ISHEventHandler>>();
	
	public static SHEventDispatcher getInstance()
	{
		if (_instance == null)
		{
			_instance = new SHEventDispatcher();
		}
		return _instance;
	}
	
	protected SHEventDispatcher()
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
	
	
	/** Resets dispatcher by removing all events and handlers */
	public void reset()
	{
		_handlers.clear();
	}
	
	public void addEvent(SHEvent event)
	{
		notifySupervisors(event);
		List<ISHEventHandler> handlers = getHandlers(event.type);
		if (handlers != null)
		{
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
		}
	}

}
