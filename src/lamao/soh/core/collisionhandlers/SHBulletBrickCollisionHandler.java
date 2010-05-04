/* 
 * SHBulletBrickCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHBrick;
import lamao.soh.core.SHScene;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;
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
//			showBonusIfPresent(brick);
		}
		scene.removeEntity(bullet);
	}

}
