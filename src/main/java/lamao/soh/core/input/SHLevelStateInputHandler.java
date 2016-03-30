/** 
 * SHLevelStateInputHandler.java 20.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input;

import com.jme3.input.InputManager;
import com.jme3.input.controls.KeyTrigger;
import lamao.soh.core.input.actions.SHPauseKeyAction;
import lamao.soh.core.input.actions.SHToMenuKeyAction;
import lamao.soh.states.SHLevelState;
import lamao.soh.ui.controllers.SHInGameScreenController;

import com.jme3.input.KeyInput;

/**
 * Input handler for level state that initialize it with default commands.
 * Created for simple injection into levelState via Spring IoC.
 * @author lamao
 *
 */
public class SHLevelStateInputHandler extends InputManager
{

    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_MENU = "menu";

    public SHLevelStateInputHandler(
			SHLevelState levelState,
			SHInGameScreenController inGameScreenController)
	{
        addMapping(ACTION_PAUSE, new KeyTrigger(KeyInput.KEY_PAUSE));
		addMapping(ACTION_MENU, new KeyTrigger(KeyInput.KEY_ESCAPE));

        addListener(new SHPauseKeyAction(levelState), ACTION_PAUSE);
        addListener(new SHToMenuKeyAction(levelState, inGameScreenController), ACTION_MENU);
	}

}
