/* 
 * SHBreakoutGameContext.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.List;

import com.jme.scene.Spatial;

import lamao.soh.core.entities.SHBrick;

/**
 * Game context for Stones of History
 * @author lamao
 *
 */
public class SHBreakoutGameContext implements ISHGameContext
{
	/** Number of bricks can be deleted ( = number of bricks to delete)*/
	private int _numDeletableBricks = 0;
	
	/**  Current player profile */
	private SHPlayerInfo _player = null;
	
	public int getNumDeletableBricks()
	{
		return _numDeletableBricks;
	}
	
	public SHPlayerInfo getPlayer()
	{
		return _player;
	}

	public void setPlayer(SHPlayerInfo player)
	{
		_player = player;
	}
	
	public void updateNumDeletableBricks()
	{
		_numDeletableBricks = 0;
		List<Spatial> bricks = SHGamePack.scene.get("brick");
		if (bricks != null)
		{
			SHBrick brick = null;
			for (Spatial e : bricks)
			{
				brick = (SHBrick)e;
				if (brick.getStrength() != Integer.MAX_VALUE)
				{
					_numDeletableBricks++;
				}
			}
		}
	}
}
