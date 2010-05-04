/* 
 * SHEventTest.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.junit.common;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;

import lamao.soh.core.SHGamePack;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventCounter;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * Test case for counting arrived events
 * @author lamao
 *
 */
public class SHEventTestCase
{
	public SHEventDispatcher dispatcher = null;
	public SHEventCounter counter = new SHEventCounter();
	
	public SHEventTestCase()
	{
		SHGamePack.dispatcher = new SHEventDispatcher();
		dispatcher = SHGamePack.dispatcher;
	}
	
	@Before
	public void setUp()
	{
		dispatcher.reset();
		counter.reset();
		dispatcher.addHandler("all", counter);
	}
	
	private Map<String, Object> createParams(Object... params)
	{
		Map<String, Object> parameters = null;
		if (params != null && params.length % 2 == 0)
		{
			parameters = new HashMap<String, Object>();
			for (int i = 0; i < params.length; i += 2)
			{
				parameters.put((String)params[i], params[i + 1]);
			}
		}
		return parameters;
	}
	
	public SHEvent createEvent(String type, Object sender, Object... params)
	{
		return new SHEvent(type, sender, createParams(params));
	}

}
