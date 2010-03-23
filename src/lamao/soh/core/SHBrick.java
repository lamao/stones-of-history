/* 
 * SHBrick.java 23.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.scene.Spatial;

/**
 * Brick game element.<br>
 * <b>NOTE:</b> Model bound for brick must be only <code>BoundingBox</code> 
 * 
 * @author lamao
 *
 */
public class SHBrick
{
	/** Model for brick */
	private Spatial _model;
	
	/** Strength (or lives) of the brick */
	private int _strength;
	
	/** true if ball can move thought the brick */
	private boolean _glass;

	
	public SHBrick(Spatial model, int strength, boolean glass)
	{
		super();
		_model = model;
		_strength = strength;
		_glass = glass;
	}
	
	/** Creates default brick with strength 1 */
	public SHBrick(Spatial model)
	{
		this(model, 1, false);
	}
	
	/** Creates default brick with strength 1 and without model */
	public SHBrick()
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

	public int getStrength()
	{
		return _strength;
	}

	public void setStrength(int strength)
	{
		_strength = strength;
	}

	public boolean isGlass()
	{
		return _glass;
	}

	public void setGlass(boolean glass)
	{
		_glass = glass;
	}
}
