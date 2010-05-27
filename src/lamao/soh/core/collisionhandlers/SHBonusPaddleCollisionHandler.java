/* 
 * SHBonusPaddleCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.eventhandlers.SHBonusTimeOverEventHandler;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * @author lamao
 *
 */
public class SHBonusPaddleCollisionHandler implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		SHBonus bonus = (SHBonus)event.params.get("src");
		
		SHGamePack.scene.removeEntity(bonus);
		boolean needAdd = true;
		if (bonus.isAddictive())
		{
			String eventName = "bonus-over-" + bonus.getType();
			if (SHGamePack.dispatcher.hasTimeEvent(eventName))
			{
				needAdd = false;
				SHGamePack.dispatcher.prolongTimeEvent(eventName, bonus.getDuration());
				SHGamePack.dispatcher.addEventEx("level-bonus-prolongated", this, 
						"bonus", bonus);
			}
		}
		if (needAdd)
		{
			String eventType = "bonus-over-" + bonus;
			SHGamePack.dispatcher.addEventEx(eventType, this, bonus.getDuration(), 
					"bonus", bonus);
			SHGamePack.dispatcher.addHandler(eventType, new SHBonusTimeOverEventHandler());
			bonus.apply(SHGamePack.scene);
			SHGamePack.dispatcher.addEventEx("level-bonus-activated", this, 
					"bonus", bonus);
		}
	}

}
