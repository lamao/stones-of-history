/* 
 * SHBulletWallCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.entities.SHBall;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * @author lamao
 *
 */
public class SHBulletWallCollisionHandler implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		SHBall bullet = (SHBall)event.params.get("src");
		SHGamePack.scene.removeEntity(bullet);
	}
}
