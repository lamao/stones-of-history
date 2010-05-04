/* 
 * SHBallLauncherAction.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.SHOptions;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.scene.Controller;

/**
 * Launches ball by mouse click. It removes <code>SHPaddleSticker</code> 
 * controller from ball controllers and adds <code>SHDefaultMover</code> to
 * it.<br> 
 * <b>NOTE:</b> is does not removes itself from InputHandler
 * @author lamao
 *
 */
public class SHMouseBallLauncher extends MouseInputAction
{
	/** Level where find balls to launch */
	private SHScene _scene = null;
	
	// TODO: Remove this variable
	/** Class for calculation ball velocity after launching */
	private SHDefaultPaddleHitHandler _handler = new SHDefaultPaddleHitHandler();
	
	public SHMouseBallLauncher(SHScene scene)
	{
		_scene = scene;
	}
	
	/* (non-Javadoc)
	 * @see com.jme.input.action.InputActionInterface#performAction(com.jme.input.action.InputActionEvent)
	 */
	@Override
	public void performAction(InputActionEvent evt)
	{
		if (MouseInput.get().isButtonDown(SHOptions.ReleaseBallButton))
		{
			SHPaddle paddle = (SHPaddle)_scene.getEntity("paddle", "paddle");
			for (SHEntity e : _scene.getEntities("ball"))
			{
				SHBall ball = (SHBall)e;
				for (Controller controller : ball.getRoot().getControllers())
				{
					if (controller instanceof SHPaddleSticker)
					{
						ball.getRoot().removeController(controller);
						ball.getRoot().addController(new SHDefaultBallMover(ball));
						_handler.execute(ball, paddle);
						break;
					}
				}
			}
			
		}
	}
	

}
