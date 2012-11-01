/* 
 * SHBonusBottomWallCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScene;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBonusBottomWallCollisionHandler extends SHAbstractCollisiontHandler
{
	
	public SHBonusBottomWallCollisionHandler(SHEventDispatcher dispatcher,
			SHScene scene)
	{
		super(dispatcher, scene);
	}

	@Override
	public void processEvent(SHEvent event)
	{
		SHBonus bonus = event.getParameter("src", SHBonus.class);
		SHGamePack.scene.remove(bonus);
		dispatcher.addEventEx("level-bonus-destroyed", this, 
				"bonus", bonus); 
	}

}
