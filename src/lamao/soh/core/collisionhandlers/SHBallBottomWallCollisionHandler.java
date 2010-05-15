/* 
 * SHBallBottomWallCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHBottomWall;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import static lamao.soh.core.SHGamePack.*;

/**
 * Handler for ball collision with bottom wall
 * @author lamao
 *
 */
public class SHBallBottomWallCollisionHandler implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		SHBall ball = (SHBall)event.params.get("src");
		SHBottomWall wall = (SHBottomWall)event.params.get("dst");
		
		if (wall.isActive())
		{
			ball.getVelocity().y = -ball.getVelocity().y;
			dispatcher.addEvent("level-wall-hit", this, null);
		}
		else
		{
			scene.removeEntity(ball);
			if (scene.getEntities(ball.getType()) == null)
			{
				dispatcher.addEvent("level-failed", this, null);
			}
		}
	}

}