/* 
 * SHDrawNormalsCommand.java 08.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import lamao.soh.states.SHLevelState;

/**
 * @author lamao
 *
 */
public class SHDrawNormalsCommand extends SHBasicCommand
{
	private SHLevelState levelState;
	
	public SHDrawNormalsCommand(SHLevelState levelState)
	{
		this.levelState = levelState;
	}
	
	@Override
	public void processCommand(String[] args)
	{
		levelState.setDrawNormals(Boolean.parseBoolean(args[1]));
	}
}
