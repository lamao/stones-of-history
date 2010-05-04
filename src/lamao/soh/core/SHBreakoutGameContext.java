/* 
 * SHBreakoutGameContext.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.List;

/**
 * Game context for breakout
 * @author lamao
 *
 */
public class SHBreakoutGameContext implements ISHGameContext
{
	private int _numDeletableBricks = 0;
	
	public int getNumDeletableBricks()
	{
		return _numDeletableBricks;
	}
	
	public void updateNumDeletableBricks()
	{
		_numDeletableBricks = 0;
		List<SHEntity> bricks = SHGamePack.scene.getEntities("brick");
		if (bricks != null)
		{
			SHBrick brick = null;
			for (SHEntity e : bricks)
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
