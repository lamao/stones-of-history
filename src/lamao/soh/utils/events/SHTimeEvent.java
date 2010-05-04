/* 
 * SHTimeEvent.java 01.05.2010
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
public class SHTimeEvent extends SHEvent
{
	/** Time left to fire event */
	public long time = 0;
	
	public SHTimeEvent()
	{
	}
	
	public SHTimeEvent(String type, Object sender, Map<String, Object> params, 
			long time)
	{
		super(type, sender, params);
		this.time = time;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "(" + time + ")";
	}

}
