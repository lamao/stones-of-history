/* 
 * SHBulletBrickCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import static lamao.soh.core.SHGamePack.*;

/**
 * @author lamao
 *
 */
public class SHBulletBrickCollisionHandler implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		SHBall bullet = (SHBall)event.params.get("src");
		SHBrick brick = (SHBrick)event.params.get("dst");
		
		dispatcher.addEventEx("level-brick-hit", this, "brick", brick);
		bullet.onHit(brick);
		if (brick.getStrength() <= 0)
		{
			scene.removeEntity(brick);
			dispatcher.addEventEx("level-brick-deleted", this, "brick", brick);
			// TODO: Implement
			/*
			 * copy from ball-brick bonus show section
			 * 		SHEntity bonus = SHBreakoutEntityFactory.createEntity(
			 * 			SHUtils.buildMap(brick.getBonus());
			 * 		if (bonus != null)
			 * 		{
			 * 			bonus.getRootNode().addController(new mover);
			 * 			scene.addEntity(bonus);
			 * 			dispatcher.addEventEx("level-bonus-extracted", this, "bonus", bonus);
			 *		}
			 * } 
			 */
//			showBonusIfPresent(brick);
		}
		scene.removeEntity(bullet);
	}

}
