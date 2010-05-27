/* 
 * SHStickyPaddleBonus.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHStickyPaddleHitHandler;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;

import com.jme.scene.Controller;
import com.jme.scene.Spatial;

/**
 * Makes paddle sticky.
 * @author lamao
 *
 */
public class SHStickyPaddleBonus extends SHBonus
{
	public final static float DURATION = 5;
	
	public SHStickyPaddleBonus(Spatial model)
	{
		super(model);
		setDuration(DURATION);
		setAddictive(true);
	}
	
	public SHStickyPaddleBonus()
	{
		this(null);
	}
	
	@Override
	public void apply(SHScene scene)
	{
		SHPaddle paddle = (SHPaddle) scene.getEntity("paddle", "paddle");
		paddle.setHitHandler(new SHStickyPaddleHitHandler());
	}
	
	@Override
	public void cleanup(SHScene scene)
	{
		SHPaddle paddle = (SHPaddle) scene.getEntity("paddle", "paddle");
		paddle.setHitHandler(new SHDefaultPaddleHitHandler());
		
		for (SHEntity entity : scene.getEntities("ball"))
		{
			SHBall ball = (SHBall)entity;
			for (Controller controller : ball.getRoot().getControllers())
			{
				if (controller instanceof SHPaddleSticker)
				{
					ball.getRoot().removeController(controller);
					ball.getRoot().addController(new SHDefaultBallMover(ball));
					break;
				}
			}
		}
	}

}
