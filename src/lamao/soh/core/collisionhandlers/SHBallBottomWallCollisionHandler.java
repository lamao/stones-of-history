/* 
 * SHBallBottomWallCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBottomWall;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;
import lamao.soh.core.SHScene;

/**
 * Handler for ball collision with bottom wall
 * @author lamao
 *
 */
public class SHBallBottomWallCollisionHandler implements ISHEventHandler
{
	private SHEventDispatcher dispatcher;
	
	private SHScene scene;
	
	public SHBallBottomWallCollisionHandler()
	{
	}

	public SHBallBottomWallCollisionHandler(
			SHEventDispatcher dispatcher,
			SHScene scene
			)
	{
		this.dispatcher = dispatcher;
		this.scene = scene;
	}
	
	public SHEventDispatcher getDispatcher()
	{
		return dispatcher;
	}

	public void setDispatcher(SHEventDispatcher dispatcher)
	{
		this.dispatcher = dispatcher;
	}
	
	public SHScene getScene()
	{
		return scene;
	}

	public void setScene(SHScene scene)
	{
		this.scene = scene;
	}

	@Override
	public void processEvent(SHEvent event)
	{
		SHBall ball = event.getParameter("src", SHBall.class);
		SHBottomWall wall = event.getParameter("dst", SHBottomWall.class);
		
		if (wall.isActive())
		{
			ball.getVelocity().y = -ball.getVelocity().y;
			dispatcher.addEvent("level-wall-hit", this);
		}
		else
		{
			scene.removeEntity(ball);
			if (scene.getEntities(ball.getType()) == null)
			{
				dispatcher.addEvent("level-failed", this);
			}
		}
	}

}
