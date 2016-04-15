/* 
 * SHBallLauncherAction.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input.listeners;

import com.jme3.input.controls.ActionListener;
import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHScene;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;

import com.jme3.scene.Spatial;
import lamao.soh.states.SHLevelState;

/**
 * Launches ball by mouse click. It removes <code>SHPaddleSticker</code> 
 * controller from ball controllers and adds <code>SHDefaultMover</code> to
 * it.<br> 
 * <b>NOTE:</b> is does not removes itself from InputHandler
 * @author lamao
 *
 */
public class LaunchBallInputListener implements ActionListener
{
	private SHLevelState levelState;

	// TODO: Remove this variable
	/** Class for calculation ball velocity after launching */
	private SHDefaultPaddleHitHandler handler = new SHDefaultPaddleHitHandler();
	
	public LaunchBallInputListener(SHLevelState state)
	{
		this.levelState = state;
	}

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
		if (!isPressed)
		{
            SHScene scene = levelState.getScene();
			SHPaddle paddle = scene.getEntity("paddle", "paddle", SHPaddle.class);
			for (Spatial e : scene.get("ball"))
			{
				SHBall ball = (SHBall)e;
                ball.removeControl(SHPaddleSticker.class);
                ball.addControl(new SHDefaultBallMover());
                handler.execute(ball, paddle);
			}
			
		}
	}
}
