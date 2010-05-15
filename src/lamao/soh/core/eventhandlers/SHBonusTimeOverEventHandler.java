/* 
 * SHBonusTimeOverEventHandler.java 15.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.eventhandlers;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * 
 * @author lamao
 *
 */
public class SHBonusTimeOverEventHandler implements ISHEventHandler
{
	/* (non-Javadoc)
	 * @see lamao.soh.utils.events.ISHEventHandler#processEvent(lamao.soh.utils.events.SHEvent)
	 */
	@Override
	public void processEvent(SHEvent event)
	{
		SHBonus bonus = (SHBonus)event.params.get("bonus");
		
		bonus.cleanup(SHGamePack.scene);
		
		SHGamePack.dispatcher.removeHandler(event.type, this);
	}

}
