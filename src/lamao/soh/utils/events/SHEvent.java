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
	public String type;
	
	/** Sender of event */
	public Object sender;
	
	/** Additional parameters of event (event specific) */
	public Map<String, Object> params;
	
	/** Time to fire this event. time < 0 if event has to be fired immediately */
	public float time;

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
		this.params = params;
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
}
