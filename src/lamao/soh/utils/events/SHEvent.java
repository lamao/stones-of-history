/* 
 * SHEvent.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import java.util.Map;

/**
 * @author lamao
 *
 */
public class SHEvent
{
	/** Type of event */
	private String type;
	
	/** Sender of event */
	private Object sender;
	
	/** Additional parameters of event (event specific) */
	public Map<String, Object> parameters;
	
	/** Time to fire this event. time < 0 if event has to be fired immediately */
	private float time;

	/**
	 * Constructs time event 
	 */
	public SHEvent(String type, Object sender, Map<String, Object> params, float time)
	{
		this(type, sender, params);
		this.time = time;
	}
	
	/**
	 * Constructs immediately event
	 */
	public SHEvent(String type, Object sender, Map<String, Object> params)
	{
		this();
		this.type = type;
		this.sender = sender;
		this.parameters = params;
	}
	
	public SHEvent()
	{
		time = -1;
	}
	
	@Override
	public String toString()
	{
		return type;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Object getSender()
	{
		return sender;
	}

	public void setSender(Object sender)
	{
		this.sender = sender;
	}

	public Map<String, Object> getParameters()
	{
		return parameters;
	}

	public void setParameters(Map<String, Object> params)
	{
		this.parameters = params;
	}
	
	public Object getParameter(String name) 
	{
		return parameters.get(name); 
	}
	
	@SuppressWarnings("unchecked")
	public<T> T getParameter(String name, Class<T> clazz) 
	{
		return (T) getParameter(name);
	}
	
	public void setParameter(String name, Object value) 
	{
		parameters.put(name, value);
	}

	public float getTime()
	{
		return time;
	}

	public void setTime(float time)
	{
		this.time = time;
	}
	
	
}
