/* 
 * SHIncBallSpeedBonus.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import java.util.LinkedList;
import java.util.List;

import com.jme.scene.Spatial;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;

/**
 * Increases ball speed.
 * @author lamao
 *
 */
public class SHIncBallSpeedBonus extends SHBonus
{
	public final static float DURATION = 5;
	public final static float INC_PERCENT = 0.5f;
	
	private List<SHBall> _balls = new LinkedList<SHBall>();
	
	
	public SHIncBallSpeedBonus(Spatial model)
	{
		super(model);
		setDuration(DURATION);
	}
	
	public SHIncBallSpeedBonus()
	{
		this(null);
	}
	
	@Override
	public void apply(SHScene scene)
	{
		float speed;
		double angle;
		for (SHEntity entity : scene.getEntities("ball"))
		{
			SHBall ball = (SHBall)entity;
			speed = ball.getVelocity().length();			
			angle = SHUtils.angle(ball.getVelocity());
			
			ball.getVelocity().x = (float)Math.cos(angle) * speed * (1 + INC_PERCENT);
			ball.getVelocity().y = (float)Math.sin(angle) * speed * (1 + INC_PERCENT);
			
			_balls.add(ball);
		}
	}

	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#cleanup(lamao.soh.core.SHLevel)
	 */
	@Override
	public void cleanup(SHScene scene)
	{
		float speed;
		double angle;
		for (SHBall ball : _balls)
		{
			speed = ball.getVelocity().length();			
			angle = Math.acos(ball.getVelocity().x / Math.abs(speed));
			if (ball.getVelocity().y < 0)
			{
				angle = 2 * Math.PI - angle;
			}

			ball.getVelocity().x = (float)Math.cos(angle) * speed / (1 + INC_PERCENT);
			ball.getVelocity().y = (float)Math.sin(angle) * speed / (1 + INC_PERCENT);
		}
		_balls.clear();
	}

}
