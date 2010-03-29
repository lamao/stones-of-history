/* 
 * SHLevelAdapter.java 29.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.core.SHLevel.SHWallType;
import lamao.soh.core.bonuses.SHBonus;

/**
 * @author lamao
 *
 */
public class SHLevelAdapter implements ISHLevelListener
{

	@Override
	public void bonusActivated(SHBonus bonus)
	{
	}

	@Override
	public void bonusDeactivated(SHBonus bonus)
	{
	}

	@Override
	public void bonusShowed(SHBonus bonus)
	{
	}

	@Override
	public void brickDeleted(SHBrick brick)
	{
	}

	@Override
	public void brickHit(SHBrick brick)
	{
	}

	@Override
	public void completed()
	{
	}

	@Override
	public void wallHit(SHWallType wall)
	{
	}

}
