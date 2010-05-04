/* 
 * SHBallWallCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHEntity;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import static lamao.soh.core.SHGamePack.*;

/**
 * @author lamao
 *
 */
public class SHBallWallCollisionHandler implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		SHBall ball = (SHBall)event.params.get("src");
		SHEntity wall = (SHEntity)event.params.get("dst");
		
		if (wall.getName().equals("left-wall") || 
			wall.getName().equals("right-wall"))
		{
			ball.getVelocity().x = -ball.getVelocity().x;
		} 
		else if (wall.getName().equals("top-wall"))
		{
			ball.getVelocity().y = -ball.getVelocity().y;
		}
		
		dispatcher.addEventEx("level-wall-hit", this, "wall-type", wall.getName());
	}

}
