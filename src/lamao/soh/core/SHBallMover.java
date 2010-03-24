/* 
 * SHBallMover.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.scene.Controller;

/**
 * Controller for ball moving 
 * @author lamao
 *
 */
@SuppressWarnings("serial")
public abstract class SHBallMover extends Controller
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

}
