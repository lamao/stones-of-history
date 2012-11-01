/* 
 * SHBonusTimeOverEventHandler.java 15.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.eventhandlers;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * 
 * @author lamao
 *
 */
public class SHBonusTimeOverEventHandler extends SHAbstractEventHandler
{
	
	
	public SHBonusTimeOverEventHandler(SHEventDispatcher dispatcher)
	{
		super(dispatcher);
	}

	/** 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void processEvent(SHEvent event)
	{
		SHBonus bonus = event.getParameter("bonus", SHBonus.class);
		
		bonus.cleanup(SHGamePack.scene);
		
		dispatcher.removeHandler(event.getType(), this);
	}

}
