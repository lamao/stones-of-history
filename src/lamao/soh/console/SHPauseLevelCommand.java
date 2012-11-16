/* 
 * SHPauseLevelCommand.java 08.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import lamao.soh.states.SHLevelState;

/**
 * Console command to pause level
 * @author lamao
 *
 */
public class SHPauseLevelCommand extends SHBasicCommand
{
	private SHLevelState levelState;
	
	public SHPauseLevelCommand(SHLevelState levelState)
	{
		this.levelState = levelState;
	}
		
	@Override
	public void processCommand(String[] args)
	{
		levelState.setPause(Boolean.parseBoolean(args[1]));
	}
}
