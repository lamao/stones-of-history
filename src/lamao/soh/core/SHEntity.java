/* 
 * SHEntity.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;

/**
 * Game entity, which has model.
 * @author lamao
 *
 */
public class SHEntity
{

	/** Model for ball */
	private Spatial _model;
	
	public SHEntity(Spatial model)
	{
		_model = model;
	}
	
	public SHEntity()
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
	
	/** 
	 * Changes model local translation. <br>
	 * <b>NOTE: </b>Model must be not null
	 */
	public void setLocation(Vector3f location)
	{
		_model.setLocalTranslation(location);
		_model.updateModelBound();
	}
	
	/** 
	 * @see #setLocation(Vector3f)
	 */
	public void setLocation(float x, float y, float z)
	{
		_model.setLocalTranslation(x, y, z);
		_model.updateModelBound();
	}
	
	public Vector3f getLocation()
	{
		return _model.getLocalTranslation();
	}

}