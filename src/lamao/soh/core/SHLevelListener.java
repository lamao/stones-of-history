/* 
 * SHLevelListener.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.core.SHLevel.SHWallType;

/**
 * Listener for level events.
 * @author lamao
 *
 */
public interface SHLevelListener
{
	/** Brick will be removed */
	public void brickDeleted(SHBrick brick);
	
	/** Wall is hit by ball*/
	public void wallHit(SHWallType wall);
	
	/** Brick is hit by ball*/
	public void brickHit(SHBrick brick);
	
	/** Level is cleared */
	public void completed();
}
