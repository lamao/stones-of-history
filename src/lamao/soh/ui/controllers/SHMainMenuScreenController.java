/** 
 * SHMainMenuScreenController.java 15.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import lamao.soh.SHMain;
import lamao.soh.core.SHBreakoutGameContext;

import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.Label;

/**
 * @author lamao
 *
 */
public class SHMainMenuScreenController extends SHBasicScreenController
{
	private static final String ANONYMOUS_USER_NAME = "unknown user";
	
	private static final String HELLO_USER_PATTERN = "Hello, %s";
	
	private SHBreakoutGameContext gameContext;
	
	
	public SHMainMenuScreenController(SHBreakoutGameContext gameContext)
	{
		this.gameContext = gameContext;
	}

	public void quit() 
	{
		SHMain.exit();
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
		
		Button startGameButton = getScreen().findNiftyControl("btnStart", Button.class);
		startGameButton.setEnabled(gameContext.getPlayer() != null);
	}

}
