/* 
 * SHBonus.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import com.jme.scene.Spatial;

import lamao.soh.core.SHEntity;
import lamao.soh.core.SHLevel;

/**
 * Bonus game entity.
 * @author lamao
 *
 */
public abstract class SHBonus extends SHEntity
{
	/** Duration of bonus */
	float _duration = 0;
	
	public SHBonus(Spatial model)
	{
		super(model);
	}
	
	public SHBonus()
	{
		this(null);
	}

	public float getDuration()
	{
		return _duration;
	}

	public void setDuration(float duration)
	{
		_duration = duration;
	}
	
	public void decreaseDuration(float time)
	{
		_duration -= time;
	}
	
	/** Apply (activate) this bonus */
	public abstract void apply(SHLevel level);
	
	/** Cleanup (deactivate) this bonus */
	public abstract void cleanup(SHLevel level);
}
