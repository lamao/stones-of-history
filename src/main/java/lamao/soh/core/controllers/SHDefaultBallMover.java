/* 
 * SHDefaultBallMover.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.controllers;

import lamao.soh.core.entities.SHBall;

/**
 * Moves ball taking into account its velocity
 * @author lamao
 *
 */
@SuppressWarnings("serial")
public class SHDefaultBallMover extends SHBallMover
{

	public SHDefaultBallMover(SHBall ball)
	{
		super(ball);
	}
	
	public SHDefaultBallMover()
	{
		super();
	}

	/**
     * {@inheritDoc}
	 */
	@Override
	public void controlUpdate(float time)
	{
		if (getBall() != null)
		{
			getBall().getLocalTranslation().addLocal(
					getBall().getVelocity().mult(time));
			getBall().updateWorldBound();
		}
	}

}
