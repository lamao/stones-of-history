/* 
 * SHBottomWall.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.scene.Spatial;

/**
 * Entity for bottom wall
 * @author lamao
 *
 */
public class SHBottomWall extends SHEntity
{

	/** Is bottom wall active or not. If it is active ball is not destroyed
	 * after collision
	 */
	private boolean _active = false;
	
	public SHBottomWall()
	{
		super();
	}
	
	public SHBottomWall(String type, String name, Spatial model)
	{
		super(type, name, model);
	}

	public boolean isActive()
	{
		return _active;
	}

	public void setActive(boolean active)
	{
		_active = active;
	}
	
	
	
}
