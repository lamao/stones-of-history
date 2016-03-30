/** 
 * GameContextService.java 19.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import java.util.List;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBrick;

import com.jme3.scene.Spatial;

/**
 * @author lamao
 *
 */
public class SHGameContextService
{
	private SHScene scene;
	
	public SHGameContextService(SHScene scene)
	{
		super();
		this.scene = scene;
	}

	public void updateNumberOfDeletableBricks(SHBreakoutGameContext context)
	{
		int numberOfDeletableBricks = 0;
		List<Spatial> bricks = scene.get("brick");
		if (bricks != null)
		{
			SHBrick brick = null;
			for (Spatial e : bricks)
			{
				brick = (SHBrick)e;
				if (brick.getStrength() != Integer.MAX_VALUE)
				{
					numberOfDeletableBricks++;
				}
			}
		}
		
		context.setNumberOfDeletableBricks(numberOfDeletableBricks);
	}

}
