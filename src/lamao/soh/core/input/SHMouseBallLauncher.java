/* 
 * SHBallLauncherAction.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input;

import lamao.soh.SHOptions;
import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHScene;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.scene.Controller;
import com.jme.scene.Spatial;

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
			for (Spatial e : _scene.get("ball"))
			{
				SHBall ball = (SHBall)e;
				for (Controller controller : ball.getControllers())
				{
					if (controller instanceof SHPaddleSticker)
					{
						ball.removeController(controller);
						ball.addController(new SHDefaultBallMover(ball));
						_handler.execute(ball, paddle);
						break;
					}
				}
			}
			
		}
	}
	

}
