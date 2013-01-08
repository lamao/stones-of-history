/** 
 * SHInGameScreenController.java 19.12.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import com.jmex.game.state.GameStateManager;

import lamao.soh.SHConstants;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.model.SHEpochLevelItem;
import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.window.WindowControl;
import de.lessvoid.nifty.elements.Element;

/**
 * Screen controller for in-game UI.
 * @author lamao
 *
 */
public class SHInGameScreenController extends SHBasicScreenController
{
	private static final String EPOCHS_SCREEN = "epochs";

	private static final String LEVEL_INFO_PATTERN = "%s (%s)";
	
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
		gotoEpochsScreen();
	}
	
	private void gotoEpochsScreen()
	{
		gameStateManager.deactivateChildNamed(SHLevelState.NAME);
		gameStateManager.activateChildNamed(SHNiftyState.NAME);
		getNifty().gotoScreen(EPOCHS_SCREEN);
	}
	
	public void setLevelInfo(SHEpochLevelItem levelItem)
	{
		Label epochLabel = getScreen().findNiftyControl("epochName", Label.class);
		Label levelLabel = getScreen().findNiftyControl("levelName", Label.class);
		
		epochLabel.setText(String.format(LEVEL_INFO_PATTERN, 
				levelItem.getEpoch().getName(), levelItem.getEpoch().getId()));
		levelLabel.setText(String.format(LEVEL_INFO_PATTERN, 
				levelItem.getLevel().getName(), levelItem.getLevel().getId()));
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
	
	/**
	 * Show in-game menu 
	 */
	public void showInGameMenu() 
	{
		Element menuPanel = getScreen().findElementByName("ingamemenu");
		menuPanel.show();
		getNifty().getNiftyMouse().enableMouseCursor(constants.CURSOR_DEFAULT);
	}
	
	/**
	 * Hide in-game menu
	 */
	private void hideInGameMenu()
	{
		Element menuPanel = getScreen().findElementByName("ingamemenu");
		menuPanel.hide();
	}
	
	/**
	 * Go to epochs menu
	 */
	public void exitGame() 
	{
		hideInGameMenu();
		gotoEpochsScreen();
	}
	
	/**
	 * Close in-game menu and resume game
	 */
	public void resumeGame() 
	{
		hideInGameMenu();
		getNifty().getNiftyMouse().resetMouseCursor();
		SHLevelState levelState = (SHLevelState) gameStateManager.getChild(SHLevelState.NAME);
		levelState.setPause(false);
	}
	

}
