/** 
 * SHPauseKeyAction.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input.keyactions;

import lamao.soh.states.SHLevelState;

import com.jme.input.action.InputActionEvent;
import com.jme.input.action.KeyInputAction;

/**
 * Pauses game
 * @author lamao
 *
 */
public class SHPauseKeyAction extends KeyInputAction
{
	private SHLevelState levelState;
	
	/**
	 * 
	 */
	public SHPauseKeyAction(SHLevelState levelState)
	{
		this.levelState = levelState;
	}
	
	public void performAction(InputActionEvent evt)
	{
		levelState.setPause(!levelState.isPause());
	}
}
