/* 
 * SHBreakoutGameContext.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.core.model.entity.SHUser;

/**
 * Game context for Stones of History
 * @author lamao
 *
 */
public class SHBreakoutGameContext
{
	/** Number of bricks can be deleted ( = number of bricks to delete)*/
	private int numberOfDeletableBricks = 0;
	
	/**  Current player profile */
	private SHUser _player = null;
	
	
	public int getNumDeletableBricks()
	{
		return numberOfDeletableBricks;
	}
	
	public SHUser getPlayer()
	{
		return _player;
	}

	public void setPlayer(SHUser player)
	{
		_player = player;
	}

	/**
	 * @param numberOfDeletableBricks
	 */
	public void setNumberOfDeletableBricks(int numberOfDeletableBricks)
	{
		this.numberOfDeletableBricks = numberOfDeletableBricks;
	}
	
	
}
