/* 
 * SHBrick.java 23.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.entities;

import lamao.soh.core.SHEntity;
import lamao.soh.core.bonuses.SHBonus;

import com.jme.scene.Spatial;

/**
 * Brick game entity.<br>
 * <b>NOTE:</b> Model bound for brick must be only <code>BoundingBox</code> 
 * 
 * @author lamao
 *
 */
public class SHBrick extends SHEntity
{
	/** Strength (or lives) of the brick */
	private int _strength;
	
	/** true if ball can move thought the brick */
	private boolean _glass;
	
	/** name of bonus. Null if brick doesn't have associated bonus */
	private SHBonus _bonus = null;

	
	public SHBrick(Spatial model, int strength, boolean glass)
	{
		super(model);
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

	public SHBonus getBonus()
	{
		return _bonus;
	}

	public void setBonus(SHBonus bonus)
	{
		_bonus = bonus;
	}

	/** 
	 * Invoked when brick is hit. Decreases its strength by one if it is not
	 * Integer.MAX_VALUE (super brick).
	 */
	public void hit()
	{
		if (_strength != Integer.MAX_VALUE)
		{
			_strength--;
		}
	}
}
