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

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;

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
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Nifty nifty, Screen screen)
	{
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
