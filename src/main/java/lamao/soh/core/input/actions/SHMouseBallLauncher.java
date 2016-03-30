/* 
 * SHBallLauncherAction.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input.actions;

import com.jme3.input.controls.ActionListener;
import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHScene;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;

import com.jme3.scene.Spatial;

/**
 * Launches ball by mouse click. It removes <code>SHPaddleSticker</code> 
 * controller from ball controllers and adds <code>SHDefaultMover</code> to
 * it.<br> 
 * <b>NOTE:</b> is does not removes itself from InputHandler
 * @author lamao
 *
 */
public class SHMouseBallLauncher implements ActionListener
{
	/** Level where find balls to launch */
	private SHScene scene = null;
	
	// TODO: Remove this variable
	/** Class for calculation ball velocity after launching */
	private SHDefaultPaddleHitHandler handler = new SHDefaultPaddleHitHandler();
	
	public SHMouseBallLauncher(SHScene scene)
	{
		this.scene = scene;
	}

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
		if (!isPressed)
		{
			SHPaddle paddle = scene.getEntity("paddle", "paddle", SHPaddle.class);
			for (Spatial e : scene.get("ball"))
			{
				SHBall ball = (SHBall)e;
                ball.removeControl(SHPaddleSticker.class);
                ball.addControl(new SHDefaultBallMover(ball));
                handler.execute(ball, paddle);
			}
			
		}
	}
}
