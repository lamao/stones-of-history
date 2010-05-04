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
	
	public SHEvent(String type, Object sender, Map<String, Object> params)
	{
		this.type = type;
		this.sender = sender;
		this.params = params;
	}
	
	public SHEvent()
	{}
	
	@Override
	public String toString()
	{
		return type;
	}
}
