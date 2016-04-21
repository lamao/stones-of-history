/* 
 * SHBallLauncherAction.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input.listeners;

import com.jme3.input.controls.ActionListener;
import lamao.soh.core.SHScene;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;

import com.jme3.scene.Spatial;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEventDispatcher;

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
	private LevelState levelState;

    private SHEventDispatcher dispatcher;

	public LaunchBallInputListener(LevelState state, SHEventDispatcher dispatcher)
	{
		this.levelState = state;
        this.dispatcher = dispatcher;
	}

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
		if (!isPressed)
		{
            SHScene scene = levelState.getScene();
			Spatial paddle = scene.getEntity("paddle", "paddle");
			for (Spatial ball : scene.get("ball"))
			{
                ball.removeControl(SHPaddleSticker.class);
                ball.addControl(new SHDefaultBallMover());

                dispatcher.addEventEx("scene-collision-ball-paddle", this, "src", ball, "dst", paddle);
			}
			
		}
	}
}
