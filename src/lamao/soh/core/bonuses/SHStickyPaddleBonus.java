/* 
 * SHStickyPaddleBonus.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHDefaultBallMover;
import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHPaddle;
import lamao.soh.core.SHPaddleSticker;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHStickyPaddleHitHandler;

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
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#apply(lamao.soh.core.SHLevel)
	 */
	@Override
	public void apply(SHScene scene)
	{
		SHPaddle paddle = (SHPaddle) scene.getEntity("paddle", "paddle");
		paddle.setHitHandler(new SHStickyPaddleHitHandler());
	}
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#cleanup(lamao.soh.core.SHLevel)
	 */
	@Override
	public void cleanup(SHScene scene)
	{
		SHPaddle paddle = (SHPaddle) scene.getEntity("paddle", "paddle");
		paddle.setHitHandler(new SHDefaultPaddleHitHandler());
		
		for (SHEntity entity : scene.getEntities("ball"))
		{
			SHBall ball = (SHBall)entity;
			for (Controller controller : ball.getModel().getControllers())
			{
				if (controller instanceof SHPaddleSticker)
				{
					ball.getModel().removeController(controller);
					ball.getModel().addController(new SHDefaultBallMover(ball));
					break;
				}
			}
		}
	}

}
