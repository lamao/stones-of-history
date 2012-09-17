/* 
 * SHBulletBrickCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBall;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBulletBrickCollisionHandler extends SHBallBrickCollisionHandler
{
	/**
	 * @param dispatcher
	 * @param scene
	 */
	public SHBulletBrickCollisionHandler(SHEventDispatcher dispatcher,
			SHScene scene)
	{
		super(dispatcher, scene);
	}

	@Override
	public void processEvent(SHEvent event)
	{
		super.processEvent(event);
		SHBall bullet = event.getParameter("src", SHBall.class);
		scene.remove(bullet);
	}

}
