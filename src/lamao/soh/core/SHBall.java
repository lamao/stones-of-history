/* 
 * SHBall.java 23.10.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;

/**
 * Ball element
 * @author lamao
 *
 */
public class SHBall
{
	/** Model for ball */
	private Spatial _model;
	
	/** Move direction */
	private Vector3f _direction;
	
	/** Move speed */
	private float _speed;

	public SHBall(Spatial model, Vector3f direction, float speed)
	{
		super();
		_model = model;
		_direction = direction;
		_speed = speed;
	}
	
	/** Create ball with move direction = (0, 1, 0) and speed = 1 */
	public SHBall(Spatial model)
	{
		this(model, Vector3f.UNIT_Y, 1);
	}
	
	/** Ball without model and same parameters as in {@link #SHBall(Spatial)} */
	public SHBall()
	{
		this(null);
	}

	public Spatial getModel()
	{
		return _model;
	}

	public void setModel(Spatial model)
	{
		_model = model;
	}

	public Vector3f getDirection()
	{
		return _direction;
	}

	public void setDirection(Vector3f direction)
	{
		_direction = direction;
	}

	public float getSpeed()
	{
		return _speed;
	}

	public void setSpeed(float speed)
	{
		_speed = speed;
	}
	
	/** 
	 * Changes model local translation. <br>
	 * <b>NOTE: </b>Model must be not null
	 */
	public void setLocation(Vector3f location)
	{
		_model.setLocalTranslation(location);
		_model.updateModelBound();
	}
	
	public Vector3f getLocation()
	{
		return _model.getLocalTranslation();
	}
	
	/** Reacts on collision with brick */
	public void onHit(SHBrick brick)
	{
		_direction.multLocal(-1);
	}
	
	
}
