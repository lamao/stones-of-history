/* 
 * SHBallBrickCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.bonuses.SHDefaultMover;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBallBrickCollisionHandler extends SHAbstractCollisiontHandler
{
	public SHBallBrickCollisionHandler(SHEventDispatcher dispatcher,
			SHScene scene)
	{
		super(dispatcher, scene);
	}

	@Override
	public void processEvent(SHEvent event)
	{
		SHBrick brick = event.getParameter("dst", SHBrick.class);
		SHBall ball = event.getParameter("src", SHBall.class);
		
		dispatcher.addEventEx("level-brick-hit", this, "brick", brick);
		ball.onHit(brick);
		if (brick.getStrength() <= 0)
		{
			scene.removeEntity(brick);
			dispatcher.addEventEx("level-brick-deleted", this, "brick", brick);
			
			SHBonus bonus = brick.getBonus();
			if (bonus != null)
			{
				bonus.addController(new SHDefaultMover(bonus));
				bonus.setLocation(brick.getLocation());
				bonus.updateGeometricState(0, true);
				scene.addEntity(bonus);
				bonus.updateGeometricState(0, true);
				dispatcher.addEventEx("level-bonus-extracted", this, 
						"bonus", bonus);
			}
		}
	}

}
