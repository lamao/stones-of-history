/* 
 * SHPaddleSticker.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.controllers;

import lamao.soh.core.entities.SHBall;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;

/**
 * Follows the given target (assumed it is paddle model).
 * @author lamao
 *
 */
@SuppressWarnings("serial")
public class SHPaddleSticker extends SHBallMover
{
	private Spatial _target = null;
	
	/** Distance to target */
	private Vector3f _distance = null;
	
	public SHPaddleSticker(SHBall ball, Spatial target)
	{
		super(ball);
		_target = target;
		_distance = target.getLocalTranslation().subtract(ball.getLocation());
	}
	
	public Spatial getTarget()
	{
		return _target;
	}

	/* (non-Javadoc)
	 * @see com.jme.scene.Controller#update(float)
	 */
	@Override
	public void update(float time)
	{
		getBall().setLocation(_target.getLocalTranslation().subtract(_distance));
	}
	

}
