/** 
 * SHMain.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.util.logging.Logger;

import lamao.soh.core.Application;

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
        GAME = new Application(SHConstants.PATHS_TO_ASSETS);
        GAME.start();
	}
	
	public static void exit() 
	{
		GAME.stop();
	}
	
}
