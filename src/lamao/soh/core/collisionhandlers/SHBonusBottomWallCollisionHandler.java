/* 
 * SHBonusBottomWallCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * @author lamao
 *
 */
public class SHBonusBottomWallCollisionHandler implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		SHBonus bonus = (SHBonus)event.params.get("src");
		SHGamePack.scene.removeEntity(bonus);
		SHGamePack.dispatcher.addEventEx("level-bonus-destroyed", this, 
				"bonus", bonus); 
	}

}
