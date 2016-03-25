/* 
 * SHBallPaddleCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBallPaddleCollisionHandler extends SHAbstractCollisiontHandler
{
	
	public SHBallPaddleCollisionHandler(SHEventDispatcher dispatcher,
			SHScene scene)
	{
		super(dispatcher, scene);
	}

	@Override
	public void processEvent(SHEvent event)
	{
		SHBall ball = event.getParameter("src", SHBall.class);
		SHPaddle paddle = event.getParameter("dst", SHPaddle.class);
		
		dispatcher.addEvent("level-paddle-hit", this, null);
		paddle.onHit(ball);
	}

}
