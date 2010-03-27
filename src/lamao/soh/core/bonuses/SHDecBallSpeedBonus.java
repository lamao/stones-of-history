/* 
 * SHDecBallSpeedBonus.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import java.util.LinkedList;
import java.util.List;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHLevel;

import com.jme.scene.Spatial;

/**
 * Decreases ball speed
 * @author lamao
 *
 */
public class SHDecBallSpeedBonus extends SHBonus
{
	public final static float DURATION = 5;
	public final static float DEC_PERCENT = 0.5f;
	
	private List<SHBall> _balls = new LinkedList<SHBall>();
	
	
	public SHDecBallSpeedBonus(Spatial model)
	{
		super(model);
		setDuration(DURATION);
	}
	
	public SHDecBallSpeedBonus()
	{
		this(null);
	}
	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#apply(lamao.soh.core.SHLevel)
	 */
	@Override
	public void apply(SHLevel level)
	{
		float speed;
		double angle;
		for (SHBall ball : level.getBalls())
		{
			speed = ball.getVelocity().length();			
			angle = Math.acos(ball.getVelocity().x / Math.abs(speed));
			if (ball.getVelocity().y < 0)
			{
				angle = 2 * Math.PI - angle;
			}
			
			ball.getVelocity().x = (float)Math.cos(angle) * speed * (1 - DEC_PERCENT);
			ball.getVelocity().y = (float)Math.sin(angle) * speed * (1 - DEC_PERCENT);
			
			_balls.add(ball);
		}
	}

	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#cleanup(lamao.soh.core.SHLevel)
	 */
	@Override
	public void cleanup(SHLevel level)
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

			ball.getVelocity().x = (float)Math.cos(angle) * speed / (1 - DEC_PERCENT);
			ball.getVelocity().y = (float)Math.sin(angle) * speed / (1 - DEC_PERCENT);
		}
		_balls.clear();
	}
}
