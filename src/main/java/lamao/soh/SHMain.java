/** 
 * SHMain.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.util.logging.Logger;

import lamao.soh.console.SHConsoleState;
import lamao.soh.core.Application;
import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Enter point into the program.
 * @author lamao
 *
 */
public class SHMain
{
	private static Application GAME = null;

	public static void main(String args[])
	{
		Logger.getLogger("").setLevel(SHOptions.LogLevel);
		
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"data/spring/rootApplicationContext.xml");

		SHLevelState levelState = applicationContext.getBean(SHLevelState.class);
        SHConsoleState consoleState = applicationContext.getBean(SHConsoleState.class);
        SHNiftyState niftyState = applicationContext.getBean(SHNiftyState.class);

        GAME = applicationContext.getBean(Application.class);
        GAME.getStateManager().attachAll(levelState, consoleState, niftyState);
        GAME.start();
	}
	
	public static void exit() 
	{
		GAME.stop();
	}
	
}
