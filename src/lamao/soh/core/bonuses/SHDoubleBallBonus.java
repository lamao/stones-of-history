/* 
 * SHDoubleBallBonus.java 29.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import java.util.List;

import com.jme.scene.Spatial;

import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.entities.SHBall;

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

	@Override
	public void apply(SHScene scene)
	{
		List<SHEntity> balls = scene.getEntities("ball");
		int n = balls.size();
		SHBall ball = null;
		SHBall newBall = null;
		float angle;
		for (int i = 0; i < n; i++)
		{
			ball = (SHBall) balls.get(i);
			newBall = ball.clone();
			newBall.getRoot().addController(new SHDefaultBallMover(newBall));
			
			angle = SHUtils.angle(ball.getVelocity());
			setVelocityAngle(ball, angle + (float)Math.PI / 8);
			setVelocityAngle(newBall, angle - (float)Math.PI / 8);
			scene.addEntity(newBall);
			newBall.getRoot().updateGeometricState(0, true);
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

	@Override
	public void cleanup(SHScene scene)
	{
		List<SHEntity> balls = scene.getEntities("ball");
		int n = balls.size() / 2;
		for (int i = 0; i < n; i++)
		{
			scene.removeEntity(balls.get(n - 1 - i));
		}
		scene.getRootNode().updateRenderState();
	}
	
}
