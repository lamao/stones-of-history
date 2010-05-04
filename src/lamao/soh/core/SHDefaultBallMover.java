/* 
 * SHDefaultBallMover.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

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

	/* (non-Javadoc)
	 * @see com.jme.scene.Controller#update(float)
	 */
	@Override
	public void update(float time)
	{
		if (getBall() != null)
		{
			getBall().getRoot().getLocalTranslation().addLocal(
					getBall().getVelocity().mult(time));
			getBall().getRoot().updateWorldBound();
		}
	}

}
