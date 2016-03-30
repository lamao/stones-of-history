/* 
 * SHBallMover.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.controllers;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import lamao.soh.core.entities.SHBall;

/**
 * Controller for ball moving 
 * @author lamao
 *
 */
@SuppressWarnings("serial")
public abstract class SHBallMover extends AbstractControl
{
	private SHBall _ball;

	public SHBallMover(SHBall ball)
	{
		super();
		_ball = ball;
	}
	public SHBallMover()
	{
		this(null);
	}
	
	public SHBall getBall()
	{
		return _ball;
	}
	public void setBall(SHBall ball)
	{
		_ball = ball;
	}

  @Override
  protected void controlRender(RenderManager rm, ViewPort vp) {

  }
}
