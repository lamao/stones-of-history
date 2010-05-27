/* 
 * SHBottomWallBonus.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import com.jme.scene.Spatial;

import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBottomWall;

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
	public void apply(SHScene scene)
	{
		SHBottomWall wall = (SHBottomWall) scene.getEntity("bottom-wall", "bottom-wall");
		wall.setActive(true);
	}

	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#cleanup(lamao.soh.core.SHLevel)
	 */
	@Override
	public void cleanup(SHScene scene)
	{
		SHBottomWall wall = (SHBottomWall) scene.getEntity("bottom-wall", "bottom-wall");
		wall.setActive(false);
	}
	

}
