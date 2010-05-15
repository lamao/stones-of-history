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
import static lamao.soh.core.SHGamePack.*;

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
		
		scene.removeEntity(bonus);
		boolean needAdd = true;
		if (bonus.isAddictive())
		{
			String eventName = "bonus-over-" + bonus.getType();
			if (dispatcher.hasTimeEvent(eventName))
			{
				needAdd = false;
				dispatcher.prolongTimeEvent(eventName, 
						(int)bonus.getDuration());
				dispatcher.addEventEx("level-bonus-prolongated", this, 
						"bonus", bonus);
			}
		}
		if (needAdd)
		{
			String eventType = "bonus-over-" + bonus;
			dispatcher.addTimeEvent(eventType, this, (int)bonus.getDuration(), 
					"bonus", bonus);
			dispatcher.addHandler(eventType, new SHBonusTimeOverEventHandler());
			bonus.apply(SHGamePack.scene);
			dispatcher.addEventEx("level-bonus-activated", this, 
					"bonus", bonus);
		}
	}

}
