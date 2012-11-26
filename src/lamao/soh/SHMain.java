/** 
 * SHMain.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import lamao.soh.console.SHConsoleState;
import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;

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
		Logger.getLogger("").setLevel(SHOptions.LogLevel);
		
		GAME = new StandardGame("Stones of History");
		GAME.start();
		
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"data/spring/rootApplicationContext.xml");
		
		SHLevelState levelState = applicationContext.getBean(SHLevelState.class);
		SHConsoleState consoleState = applicationContext.getBean(SHConsoleState.class);
		SHNiftyState niftyState = applicationContext.getBean(SHNiftyState.class);
		
		GameStateManager gameStateManager = GameStateManager.getInstance();
		gameStateManager.attachChild(levelState);
		gameStateManager.attachChild(niftyState);
		gameStateManager.attachChild(consoleState);
		niftyState.setActive(true);
	}
	
	public static void exit() 
	{
		GAME.shutdown();
	}
	
}
