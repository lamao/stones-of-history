/* 
 * SHDefaultPaddleHitHandler.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;

/**
 * Default paddle hit handler. When ball hits paddle its new velocity 
 * is calculated by the formula <code>angle = arccos((x - 0.5 * l) / (0.5 * l))
 * </code> where x - contact point on the paddle, l - total paddle length
 * along X-axis. 
 * @author lamao
 *
 */
public class SHDefaultPaddleHitHandler implements ISHPaddleHitHandler
{

	/* (non-Javadoc)
	 * @see lamao.soh.core.ISHPaddleHitHandler#execute(lamao.soh.core.SHBall, lamao.soh.core.SHPaddle)
	 */
	@Override
	public void execute(SHBall ball, SHPaddle paddle)
	{
		if (ball.getLocation().y > paddle.getLocation().y)
		{
			BoundingBox paddleBound = (BoundingBox)paddle.getModel().getWorldBound();
			float ballPos = ball.getLocation().x - paddle.getLocation().x 
					+ paddleBound.xExtent;
			float paddleWidth = paddleBound.xExtent * 2;
			
			double newAngle = Math.acos((ballPos - 0.5f * paddleWidth) 
										/ (0.5f * paddleWidth));
			float speed = ball.getVelocity().length();
			Vector3f newVelocity = new Vector3f(
					(float)(speed * Math.cos(newAngle)),
					(float)(speed * Math.sin(newAngle)),
					0);
			ball.setVelocity(newVelocity);
		}
	}

}
