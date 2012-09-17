/* 
 * SHEventLogger.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import java.util.logging.Logger;

import lamao.soh.core.eventhandlers.SHAbstractEventHandler;

/**
 * Prints all events to log.
 * @author lamao
 *
 */
public class SHEventLogger extends SHAbstractEventHandler
{
	public SHEventLogger(SHEventDispatcher dispatcher)
	{
		super(dispatcher);
	}

	private Logger _logger = Logger.getLogger(SHEventLogger.class.getName());
	
	@Override
	public void processEvent(SHEvent event)
	{
		_logger.info("EVENT: <" + event.getType() + ", " + event.getSender() + ", " + 
				event.getParameters() + ">");
	}

}
