/* 
 * SHPaddle.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.entities;

import lamao.soh.core.ISHPaddleHitHandler;
import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHEntity;

import com.jme3.scene.Spatial;

/**
 * Paddle game entity.<br>
 * <b>Note:</b> Its model bound must be only <code>BoundingBox</code>
 * @author lamao
 *
 */
@SuppressWarnings("serial")
public class SHPaddle extends SHEntity
{
	private ISHPaddleHitHandler _hitHandler = null;
	
	public ISHPaddleHitHandler getHitHandler()
	{
		return _hitHandler;
	}

	public SHPaddle(Spatial model, ISHPaddleHitHandler hitHandler)
	{
		super(model);
		_hitHandler = hitHandler;
	}
	
	/** 
	 * Creates paddle with given model and default hit handler 
	 * @param model - model
	 */
	public SHPaddle(Spatial model)
	{
		this(model, null);
		_hitHandler = new SHDefaultPaddleHitHandler();
	}
	
	public SHPaddle()
	{
		this(null);
	}
	
	public void setHitHandler(ISHPaddleHitHandler hitHandler)
	{
		_hitHandler = hitHandler;
	}
	
	/** 
	 * Handles intersection with ball. 
	 * @param ball - ball
	 */
	public void onHit(SHBall ball)
	{
		if (_hitHandler != null)
		{
			_hitHandler.execute(ball, this);
		}
	}
}
