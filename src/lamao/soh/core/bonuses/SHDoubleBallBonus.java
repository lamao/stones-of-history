/* 
 * SHDoubleBallBonus.java 29.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import com.jme.scene.Spatial;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHDefaultBallMover;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHUtils;

/**
 * Doubles number of balls
 * @author lamao
 *
 */
public class SHDoubleBallBonus extends SHBonus
{
	public final static float DURATION = 10;
	
	public SHDoubleBallBonus(Spatial model)
	{
		super(model);
		setDuration(DURATION);
	}
	
	public SHDoubleBallBonus()
	{
		this(null);
	}

	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#apply(lamao.soh.core.SHLevel)
	 */
	@Override
	public void apply(SHLevel level)
	{
		int n = level.getBalls().size();
		SHBall ball = null;
		SHBall newBall = null;
		float angle;
		for (int i = 0; i < n; i++)
		{
			ball = level.getBalls().get(i);
			newBall = ball.clone();
			newBall.getModel().addController(new SHDefaultBallMover(newBall));
			
			angle = SHUtils.angle(ball.getVelocity());
			setVelocityAngle(ball, angle + (float)Math.PI / 8);
			setVelocityAngle(newBall, angle - (float)Math.PI / 8);
			level.addBall(newBall);
		}
	}
	
	/**
	 * Changes velocity of ball to specified angle. Speed is saved.
	 * @param ball - ball
	 * @param angle - new velocity
	 */
	private void setVelocityAngle(SHBall ball, float angle)
	{
		float speed = ball.getVelocity().length();
		ball.setVelocity((float)Math.cos(angle) * speed,
				(float)Math.sin(angle) * speed, 0);
	}

	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#cleanup(lamao.soh.core.SHLevel)
	 */
	@Override
	public void cleanup(SHLevel level)
	{
		int n = level.getBalls().size() / 2;
		for (int i = 0; i < n; i++)
		{
			level.deleteBall(level.getBalls().get(n - 1 - i));
		}
	}
	
}
