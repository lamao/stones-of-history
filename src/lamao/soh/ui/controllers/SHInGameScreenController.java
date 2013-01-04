/** 
 * SHInGameScreenController.java 19.12.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import lamao.soh.core.SHBreakoutGameContext;
import de.lessvoid.nifty.controls.Label;

/**
 * Screen controller for in-game UI.
 * @author lamao
 *
 */
public class SHInGameScreenController extends SHBasicScreenController
{
	private SHBreakoutGameContext context;
	
	public SHInGameScreenController(SHBreakoutGameContext context)
	{
		this.context = context;
	}
	
	/**
	 * Setup number of deleteble bricks left on the field
	 * @param numberOfBricks
	 */
	public void setNumberOfBricks(int numberOfBricks) {
		Label label = getScreen().findNiftyControl("bricksLeftValue", Label.class);
		label.setText(String.valueOf(numberOfBricks));
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
