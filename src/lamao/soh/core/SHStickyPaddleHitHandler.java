/* 
 * SHStickyPaddleHitHandler.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.scene.Controller;

/**
 * Changes ball mover to {@link SHPaddleSticker} and adds 
 * {@link SHMouseBallLauncher} action to input handler.<br>
 * <b>NOTE:</b> if does this only if <code>ball.getVelocity().y < 0</code>
 * @author lamao
 *
 */
public class SHStickyPaddleHitHandler implements ISHPaddleHitHandler
{

	/* (non-Javadoc)
	 * @see lamao.soh.core.ISHPaddleHitHandler#execute(lamao.soh.core.SHBall, lamao.soh.core.SHPaddle)
	 */
	@Override
	public void execute(SHBall ball, SHPaddle paddle)
	{
		if (ball.getVelocity().y < 0)
		{
			Controller controller = null;
			int n = ball.getModel().getControllers().size();
			for (int i = 0; i < n; i++)
			{
				controller = ball.getModel().getController(i);
				if (controller instanceof SHDefaultBallMover)
				{
					ball.getModel().removeController(i);
				}
				if (controller instanceof SHPaddleSticker)
				{
					return;
				}
			}
			ball.getModel().addController(new SHPaddleSticker(ball, paddle.getModel()));
		}
	}
	
}
