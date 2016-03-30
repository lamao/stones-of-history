/* 
 * SHPaddleSticker.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.controllers;

import lamao.soh.core.entities.SHBall;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void controlUpdate(float time)
	{
		getBall().setLocation(_target.getLocalTranslation().subtract(_distance));
	}
	

}
