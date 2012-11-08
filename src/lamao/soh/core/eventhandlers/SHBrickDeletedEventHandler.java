/* 
 * SHBrickDeletedEventHandler.java 04.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.eventhandlers;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBrickDeletedEventHandler extends SHAbstractEventHandler
{
	
	private SHBreakoutGameContext context;
	
	public SHBrickDeletedEventHandler(SHEventDispatcher dispatcher,
			SHBreakoutGameContext context)
	{
		super(dispatcher);
		this.context = context;
	}

	@Override
	public void processEvent(SHEvent event)
	{
		context.updateNumDeletableBricks();
		if (context.getNumDeletableBricks() == 0)
		{
			dispatcher.addEvent("level-completed", this, null);
		}
		
	}

}
