/** 
 * SHToMenuKeyAction.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input.keyactions;

import lamao.soh.states.SHLevelState;
import lamao.soh.ui.controllers.SHInGameScreenController;

import com.jme3.input.action.InputActionEvent;
import com.jme3.input.action.KeyInputAction;

/**
 * Show main menu
 * @author lamao
 *
 */
public class SHToMenuKeyAction extends KeyInputAction
{
	private SHLevelState levelState;
	
	private SHInGameScreenController inGameScreenController;
	
	public SHToMenuKeyAction(SHLevelState levelState,
			SHInGameScreenController inGameScreenController)
	{
		this.levelState = levelState;
		this.inGameScreenController = inGameScreenController;
	}

	@Override
	public void performAction(InputActionEvent evt)
	{
		levelState.setPause(true);
		inGameScreenController.showInGameMenu();
	}

}
