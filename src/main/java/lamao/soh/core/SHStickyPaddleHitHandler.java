/* 
 * SHStickyPaddleHitHandler.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;
import lamao.soh.core.input.actions.SHMouseBallLauncher;

import com.jme3.scene.Controller;

/**
 * Changes ball mover to {@link SHPaddleSticker} and adds 
 * {@link SHMouseBallLauncher} action to input handler.<br>
 * <b>NOTE:</b> if does this only if <code>ball.getVelocity().y < 0</code>
 * @author lamao
 *
 */
public class SHStickyPaddleHitHandler implements ISHPaddleHitHandler
{
	@Override
	public void execute(SHBall ball, SHPaddle paddle)
	{
		if (ball.getVelocity().z > 0)
		{
			Controller controller = null;
			int n = ball.getControllers().size();
			for (int i = 0; i < n; i++)
			{
				controller = ball.getController(i);
				if (controller instanceof SHDefaultBallMover)
				{
					ball.removeController(i);
				}
				if (controller instanceof SHPaddleSticker)
				{
					return;
				}
			}
			ball.addController(new SHPaddleSticker(ball, paddle));
		}
	}
	
}
