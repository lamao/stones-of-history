/** 
 * SHMainMenuScreenController.java 15.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import lamao.soh.SHMain;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;

import com.jmex.game.state.GameState;
import com.jmex.game.state.GameStateManager;

import de.lessvoid.nifty.controls.Label;

/**
 * @author lamao
 *
 */
public class SHMainMenuScreenController extends SHBasicScreenController
{
	private static final String ANONYMOUS_USER_NAME = "unknown user";
	
	private static final String HELLO_USER_PATTERN = "Hello, %s";
	
	private GameStateManager manager;
	
	private SHBreakoutGameContext gameContext;
	
	
	public SHMainMenuScreenController(GameStateManager manager,
			SHBreakoutGameContext gameContext)
	{
		this.manager = manager;
		this.gameContext = gameContext;
	}

	public void quit() 
	{
		SHMain.exit();
	}
	
	public void startGame() 
	{
		GameState niftyState = manager.getChild(SHNiftyState.NAME); 
		GameState levelState = manager.getChild(SHLevelState.NAME);
		
		niftyState.setActive(false);
		levelState.setActive(true);
	}
	
	public String getUserName()
	{
		return gameContext.getPlayer() == null 
			? ANONYMOUS_USER_NAME
			: gameContext.getPlayer().getName();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStartScreen()
	{
		Label helloLabel = getScreen().findNiftyControl("helloLabel", Label.class);
		helloLabel.setText(String.format(HELLO_USER_PATTERN, getUserName()));
	}

}
