/* 
 * SHBallLauncherAction.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.SHOptions;

import com.jme.input.InputHandler;
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
	private SHLevel _level = null;
	
	// TODO: Remove this variable
	/** Class for calculation ball velocity after launching */
	private SHDefaultPaddleHitHandler _handler = new SHDefaultPaddleHitHandler();
	
	public SHMouseBallLauncher(SHLevel level)
	{
		_level = level;
	}
	
	/* (non-Javadoc)
	 * @see com.jme.input.action.InputActionInterface#performAction(com.jme.input.action.InputActionEvent)
	 */
	@Override
	public void performAction(InputActionEvent evt)
	{
		if (MouseInput.get().isButtonDown(SHOptions.ReleaseBallButton))
		{
			for (SHBall ball : _level.getBalls())
			{
				for (Controller controller : ball.getModel().getControllers())
				{
					if (controller instanceof SHPaddleSticker)
					{
						ball.getModel().removeController(controller);
						ball.getModel().addController(new SHDefaultBallMover(ball));
						_handler.execute(ball, _level.getPaddle());
						break;
					}
				}
			}
			
		}
	}
	

}
