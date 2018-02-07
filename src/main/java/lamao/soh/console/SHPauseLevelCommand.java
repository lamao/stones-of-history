/* 
 * SHPauseLevelCommand.java 08.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import lamao.soh.states.LevelState;

/**
 * Console command to pause level
 * @author lamao
 *
 */
public class SHPauseLevelCommand extends SHBasicCommand
{
	private LevelState levelState;
	
	public SHPauseLevelCommand(LevelState levelState)
	{
		this.levelState = levelState;
	}
		
	@Override
	public void processCommand(String[] args)
	{
		levelState.setEnabled(Boolean.parseBoolean(args[1]));
	}
}
