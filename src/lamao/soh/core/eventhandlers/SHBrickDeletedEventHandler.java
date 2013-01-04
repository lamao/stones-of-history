/* 
 * SHBrickDeletedEventHandler.java 04.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.eventhandlers;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.service.SHGameContextService;
import lamao.soh.ui.controllers.SHInGameScreenController;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBrickDeletedEventHandler extends SHAbstractEventHandler
{
	
	private SHBreakoutGameContext context;
	
	private SHGameContextService contextService;
	
	private SHInGameScreenController inGameScreenController;
	
	public SHBrickDeletedEventHandler(
			SHEventDispatcher dispatcher,
			SHBreakoutGameContext context,
			SHGameContextService contextService,
			SHInGameScreenController inGameScreenController)
	{
		super(dispatcher);
		this.context = context;
		this.contextService = contextService;
		this.inGameScreenController = inGameScreenController;
	}

	@Override
	public void processEvent(SHEvent event)
	{
		contextService.updateNumberOfDeletableBricks(context);
		inGameScreenController.setNumberOfBricks(context.getNumDeletableBricks());
		if (context.getNumDeletableBricks() == 0)
		{
			dispatcher.addEvent("level-completed", this, null);
		} 
	}

}
