/** 
 * SHToMenuKeyAction.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input.keyactions;

import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;

import com.jme.input.action.InputActionEvent;
import com.jme.input.action.KeyInputAction;
import com.jmex.game.state.GameStateManager;

/**
 * Show main menu
 * @author lamao
 *
 */
public class SHToMenuKeyAction extends KeyInputAction
{
	private SHLevelState levelState;
	
	private GameStateManager gameStateManager;
	
	public SHToMenuKeyAction(SHLevelState levelState,
			GameStateManager gameStateManager)
	{
		this.levelState = levelState;
		this.gameStateManager = gameStateManager;
	}

	@Override
	public void performAction(InputActionEvent evt)
	{
		levelState.setActive(false);
		gameStateManager.activateChildNamed(SHNiftyState.NAME);
	}

}
