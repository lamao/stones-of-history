/* 
 * SHBrickDeletedEventHandler.java 04.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.eventhandlers;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHGamePack;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * @author lamao
 *
 */
public class SHBrickDeletedEventHandler implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		SHBreakoutGameContext context = (SHBreakoutGameContext)SHGamePack.context;
		context.updateNumDeletableBricks();
		if (context.getNumDeletableBricks() == 0)
		{
			SHGamePack.dispatcher.addEvent("level-completed", this, null);
		}
		
	}

}
