/* 
 * SHDrawBoundsCommand.java 08.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import lamao.soh.states.LevelState;

/**
 * @author lamao
 *
 */
public class SHDrawBoundsCommand extends SHBasicCommand
{
	private LevelState levelState;
	
	public SHDrawBoundsCommand(LevelState levelState)
	{
		this.levelState = levelState;
	}
	
	@Override
	public void processCommand(String[] args)
	{
        warning("Disabled command");
//		levelState.setDrawBounds(Boolean.parseBoolean(args[1]));
	}

}
