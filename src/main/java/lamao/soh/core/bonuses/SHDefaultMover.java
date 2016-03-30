/* 
 * SHDefaultMover.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import com.jme3.math.Vector3f;
import com.jme3.scene.Controller;
import com.jme3.scene.Spatial;

/**
 * Moves node according given velocity
 * @author lamao
 *
 */
@SuppressWarnings("serial")
public class SHDefaultMover extends Controller
{
	private Vector3f _velocity;
	
	private Spatial _model;
	
	public SHDefaultMover(Spatial model)
	{
		_model = model;
		_velocity = new Vector3f(0, 0, 1);
	}
	
	public SHDefaultMover()
	{
		this(null);
	}

	public Vector3f getVelocity()
	{
		return _velocity;
	}

	public void setVelocity(Vector3f velocity)
	{
		_velocity = velocity;
	}

	public Spatial getModel()
	{
		return _model;
	}

	public void setModel(Spatial model)
	{
		_model = model;
	}

	@Override
	public void update(float time)
	{
		_model.getLocalTranslation().addLocal(_velocity.mult(time));
		_model.updateModelBound();
	}
	
}
