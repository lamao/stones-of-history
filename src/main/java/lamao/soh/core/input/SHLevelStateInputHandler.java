/** 
 * SHLevelStateInputHandler.java 20.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input;

import lamao.soh.core.input.keyactions.SHPauseKeyAction;
import lamao.soh.core.input.keyactions.SHToMenuKeyAction;
import lamao.soh.states.SHLevelState;
import lamao.soh.ui.controllers.SHInGameScreenController;

import com.jme3.input.InputHandler;
import com.jme3.input.KeyInput;

/**
 * Input handler for level state that initialize it with default commands.
 * Created for simple injection into levelState via Spring IoC.
 * @author lamao
 *
 */
public class SHLevelStateInputHandler extends InputHandler
{
	public SHLevelStateInputHandler(
			SHLevelState levelState,
			SHInGameScreenController inGameScreenController)
	{
		addAction(new SHPauseKeyAction(levelState), "pause",
				KeyInput.KEY_PAUSE, false);
		addAction(new SHToMenuKeyAction(levelState, inGameScreenController), "menu",
				KeyInput.KEY_ESCAPE, false);
	}

}
