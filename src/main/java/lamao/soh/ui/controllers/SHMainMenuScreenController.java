/** 
 * SHMainMenuScreenController.java 15.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

import lamao.soh.SHMain;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHSessionInfo;
import lamao.soh.core.model.entity.SHUser;
import lamao.soh.core.service.SHSessionService;
import lamao.soh.core.service.SHUserService;

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
	
	private static final Logger LOGGER = Logger.getLogger(SHMainMenuScreenController.class.getCanonicalName());
	
	private SHBreakoutGameContext gameContext;
	
	private SHSessionService sessionService;
	
	private SHUserService userService;
	
	
	public SHMainMenuScreenController(SHBreakoutGameContext gameContext,
			SHSessionService sessionService,
			SHUserService userService)
	{
		this.gameContext = gameContext;
		this.sessionService = sessionService;
		this.userService = userService;
	}

	public void quit() 
	{
		SHMain.exit();
	}
	
	/**
	 * Get name of currently selected user
	 * @return name of current user of 'anonymous' if no use is selected
	 */
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
		if (gameContext.getPlayer() == null)
		{
			gameContext.setPlayer(getUserFromSession());
		}
		
		Label helloLabel = getScreen().findNiftyControl("helloLabel", Label.class);
		helloLabel.setText(String.format(HELLO_USER_PATTERN, getUserName()));
		
		Button startGameButton = getScreen().findNiftyControl("btnStart", Button.class);
		startGameButton.setEnabled(gameContext.getPlayer() != null);
	}
	
	/**
	 * Loads last selected profile from session info. 
	 * @return last selected profile or null if it was not found
	 */
	private SHUser getUserFromSession()
	{
		SHUser result = null;
		SHSessionInfo sessionInfo = sessionService.getSessionInfo();
		if (sessionInfo.getLastSelectedUsername() != null 
			&& !sessionInfo.getLastSelectedUsername().isEmpty())
		{
			try
			{
				result = userService.load(sessionInfo.getLastSelectedUsername());
			}
			catch (FileNotFoundException e)
			{
				LOGGER.warning("Can not load profile from session: <" 
						+ sessionInfo.getLastSelectedUsername() + ">");
			}
		}
		return result;
	}

}
