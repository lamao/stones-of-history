/** 
 * SHMainMenuScreenController.java 15.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import lamao.soh.SHMain;
import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;

import com.jmex.game.state.GameStateManager;

import de.lessvoid.nifty.screen.DefaultScreenController;

/**
 * @author lamao
 *
 */
public class SHMainMenuScreenController extends DefaultScreenController
{
	
	private SHNiftyState niftyState;
	
	
	public SHMainMenuScreenController(SHNiftyState niftyState)
	{
		this.niftyState = niftyState;
	}

	public void quit() 
	{
		SHMain.exit();
	}
	
	public void startGame() 
	{
		niftyState.setActive(false);
		GameStateManager.getInstance().activateChildNamed(SHLevelState.NAME);
	}

}
