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
 * Launches ball by mouse click. It removes sticker ball controller and 
 * adds default mover. 
 * @author lamao
 *
 */
public class SHMouseBallLauncher extends MouseInputAction
{
	/** Ball to launch */
	private SHBall _ball;
	
	/** Input handler to which this action is attached */
	private InputHandler _input;
	
	public SHMouseBallLauncher(SHBall ball, InputHandler input)
	{
		_ball = ball;
		_input = input;
	}

	/* (non-Javadoc)
	 * @see com.jme.input.action.InputActionInterface#performAction(com.jme.input.action.InputActionEvent)
	 */
	@Override
	public void performAction(InputActionEvent evt)
	{
		if (MouseInput.get().isButtonDown(SHOptions.ReleaseBallButton))
		{
			for (Controller controller : _ball.getModel().getControllers())
			{
				if (controller instanceof SHPaddleSticker)
				{
					_ball.getModel().removeController(controller);
					break;
				}
			}
			
			_ball.getModel().addController(new SHDefaultBallMover(_ball));
			_input.removeAction(this);
		}
	}
	

}
