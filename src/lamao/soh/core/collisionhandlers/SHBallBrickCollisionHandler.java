/* 
 * SHBallBrickCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHBrick;
import lamao.soh.core.SHScene;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.bonuses.SHDefaultMover;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import static lamao.soh.core.SHGamePack.*;

/**
 * @author lamao
 *
 */
public class SHBallBrickCollisionHandler implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		SHBrick brick = (SHBrick)event.params.get("dst");
		SHBall ball = (SHBall)event.params.get("src");
		
		dispatcher.addEventEx("level-brick-hit", this, "brick", brick);
		ball.onHit(brick);
		if (brick.getStrength() <= 0)
		{
			SHScene scene = (SHScene)event.sender;
			scene.removeEntity(brick);
			dispatcher.addEventEx("level-brick-deleted", this, "brick", brick);
			
			SHBonus bonus = brick.getBonus();
			if (bonus != null)
			{
				bonus.getRoot().addController(new SHDefaultMover(bonus.getRoot()));
				bonus.setLocation(brick.getLocation());
				bonus.getRoot().updateGeometricState(0, true);
				scene.addEntity(bonus);
				dispatcher.addEventEx("level-bonus-extracted", this, 
						"bonus", bonus);
			}
		}
	}

}