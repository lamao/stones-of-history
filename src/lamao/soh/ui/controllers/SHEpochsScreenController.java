/** 
 * SHEpochsScreenController.java 22.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;

import com.jmex.game.state.GameState;
import com.jmex.game.state.GameStateManager;

/**
 * Controller for screen with epochs/levels.
 * @author lamao
 *
 */
public class SHEpochsScreenController extends SHBasicScreenController
{
	private GameStateManager manager;
	
	private SHBreakoutGameContext gameContext;
	
	/**
	 * 
	 */
	public SHEpochsScreenController(GameStateManager manager,
			SHBreakoutGameContext gameContext)
	{
		this.manager = manager;
		this.gameContext = gameContext;
	}
	
	public void startGame() 
	{
		GameState niftyState = manager.getChild(SHNiftyState.NAME); 
		GameState levelState = manager.getChild(SHLevelState.NAME);
		
		niftyState.setActive(false);
		levelState.setActive(true);
	}

}
