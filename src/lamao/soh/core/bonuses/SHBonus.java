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
import lamao.soh.core.SHScene;

/**
 * Bonus game entity.
 * @author lamao
 *
 */
public abstract class SHBonus extends SHEntity
{
	/** Duration of bonus. NaN for persistent bonus (e.g. additional life) */
	float _duration = 0;
	
	/** Defines whether bonus duration is added or new bonus should be 
	 * created.
	 */
	boolean _addictive = false;
	
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
	
	public void increaseDuration(float time)
	{
		_duration += time;
	}
	
	public boolean isAddictive()
	{
		return _addictive;
	}

	public void setAddictive(boolean addictive)
	{
		_addictive = addictive;
	}

	/** Apply (activate) this bonus */
	public abstract void apply(SHScene scene);
	
	/** Cleanup (deactivate) this bonus */
	public abstract void cleanup(SHScene scene);
}
