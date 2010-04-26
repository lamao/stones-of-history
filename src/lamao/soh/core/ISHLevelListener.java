/* 
 * SHLevelListener.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.core.SHLevel.SHWallType;
import lamao.soh.core.bonuses.SHBonus;

/**
 * Listener for level events.
 * @author lamao
 *
 */
public interface ISHLevelListener
{
	/** Bonus is activated (e.i. when it is captured by paddle)*/
	public void bonusActivated(SHBonus bonus);
	
	/** Bonus is deactivated (e.i. its duration is expired) */
	public void bonusDeactivated(SHBonus bonus);
	
	/** Bonus is extracted from brick */
	public void bonusShowed(SHBonus bonus);
	
	/** Brick will be removed */
	public void brickDeleted(SHBrick brick);
	
	/** Wall is hit by ball*/
	public void wallHit(SHWallType wall);
	
	/** Brick is hit by ball*/
	public void brickHit(SHBrick brick);
	
	/** Level is cleared */
	public void completed();
	
	/** Level is failed (e.i. all balls have been lost */
	public void failed();
}
