/** 
 * SHInGameScreenController.java 19.12.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import com.jmex.game.state.GameState;
import com.jmex.game.state.GameStateManager;

import lamao.soh.SHConstants;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.window.WindowControl;

/**
 * Screen controller for in-game UI.
 * @author lamao
 *
 */
public class SHInGameScreenController extends SHBasicScreenController
{
	private SHBreakoutGameContext context;
	
	private SHConstants constants;
	
	private GameStateManager gameStateManager;
	
	public SHInGameScreenController(SHBreakoutGameContext context,
			SHConstants constants,
			GameStateManager gameStateManager)
	{
		this.context = context;
		this.constants = constants;
		this.gameStateManager = gameStateManager;
	}
	
	/**
	 * Setup number of deleteble bricks left on the field
	 * @param numberOfBricks
	 */
	public void setNumberOfBricks(int numberOfBricks) {
		Label label = getScreen().findNiftyControl("bricksLeftValue", Label.class);
		label.setText(String.valueOf(numberOfBricks));
	}
	
	public void setFps(int value)
	{
		if (getScreen() != null)
		{
			Label label = getScreen().findNiftyControl("fpsValue", Label.class);
			label.setText(String.valueOf(value));
		}
	}
	
	public void showCompletedMessage()
	{
		showInfoWindow("COMPLETED", "LEVEL COMPLETED", "onLevelFinished");
		getNifty().getNiftyMouse().enableMouseCursor(constants.CURSOR_DEFAULT);
	}
	
	public void showFailedMessage()
	{
		showInfoWindow("FAILED", "LEVEL FAILED", "onLevelFinished");
		getNifty().getNiftyMouse().enableMouseCursor(constants.CURSOR_DEFAULT);
	}
	
	public void onLevelFinished(String windowId)
	{
		WindowControl infoWindow = getScreen().findNiftyControl(windowId, 
				WindowControl.class);
		infoWindow.closeWindow();
		getNifty().getNiftyMouse().resetMouseCursor();
		
		SHLevelState levelState = (SHLevelState)gameStateManager.getChild(SHLevelState.NAME);
		levelState.setActive(false);
		
		gameStateManager.activateChildNamed(SHNiftyState.NAME);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStartScreen()
	{
		Label label = getScreen().findNiftyControl(
				"bricksLeftValue", Label.class);
		label.setText(String.valueOf(context.getNumDeletableBricks()));
		super.onStartScreen();
	}
	
	

}
