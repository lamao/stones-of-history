/* 
 * SHLoadLevelCommand.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import java.io.File;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScripts;
import lamao.soh.utils.deled.SHSceneLoader;

/**
 * Loads level from specified file
 * @author lamao
 *
 */
public class SHLoadLevelCommand extends SHBasicCommand
{
	
	public SHLoadLevelCommand()
	{
		super(1, 1);
	}
	
	@Override
	public void processCommand(String[] args)
	{
		File file = new File(args[1]);
		if (!file.exists())
		{
			warning("File <" + args[1] + "> does not exists");
		}
		else
		{
			SHGamePack.scene.resetAll();
			SHScripts.loadLevelScript(file.toString());
		}
	}
	
	@Override
	protected String getHelpMessage()
	{
		return "Arguments: <path/to/file>";
	}

}
