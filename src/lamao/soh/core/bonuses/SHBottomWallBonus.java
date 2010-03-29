/* 
 * SHBottomWallBonus.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import com.jme.scene.Spatial;

import lamao.soh.core.SHLevel;

/**
 * Activates bottom wall (e.i. player is not failed when ball contact with 
 * bottom wall
 * @author lamao
 *
 */
public class SHBottomWallBonus extends SHBonus
{
	public final static float DURATION = 5; 

	public SHBottomWallBonus(Spatial model)
	{
		super(model);
		setDuration(DURATION);
		setAddictive(true);
	}
	
	public SHBottomWallBonus()
	{
		this(null);
	}
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#apply(lamao.soh.core.SHLevel)
	 */
	@Override
	public void apply(SHLevel level)
	{
		level.setBottomWallActive(true);
	}

	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#cleanup(lamao.soh.core.SHLevel)
	 */
	@Override
	public void cleanup(SHLevel level)
	{
		level.setBottomWallActive(false);
	}
	

}
