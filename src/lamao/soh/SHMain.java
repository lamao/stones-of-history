/* 
 * SHMain.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import lamao.soh.console.SHConsoleState;
import lamao.soh.states.SHLevelState;

import com.jme.app.AbstractGame.ConfigShowMode;
import com.jmex.game.StandardGame;
import com.jmex.game.state.GameStateManager;

/**
 * Enter point into the program.
 * @author lamao
 *
 */
public class SHMain
{
	private static StandardGame GAME = null;

	public static void main(String args[])
	{
		GAME = new StandardGame("Stones of History");
		GAME.setConfigShowMode(ConfigShowMode.AlwaysShow);
		GAME.start();
		
		SHConsoleState console = new SHConsoleState(SHConsoleState.STATE_NAME);
		GameStateManager.getInstance().attachChild(console);
		
		SHLevelState level = new SHLevelState();
		SHLevelGenerator.generate(level.getLevel());
		GameStateManager.getInstance().attachChild(level);
		level.setActive(true);
	}
}
