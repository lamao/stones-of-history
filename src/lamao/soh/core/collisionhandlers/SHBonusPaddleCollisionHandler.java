/* 
 * SHBonusPaddleCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHPaddle;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;
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
		SHPaddle paddle = (SHPaddle)event.params.get("dst");
		
		dispatcher.addEventEx("level-bonus-activated", this, "bonus", bonus);
		
//		_bonusNode.detachChild(bonus.getModel());
//		_showedBonuses.remove(bonus);
//		i--;
//		activateBonus(bonus);
		
	}

}
