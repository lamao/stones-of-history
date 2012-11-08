/* 
 * SHLoadLevelCommand.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import java.io.File;

import lamao.soh.core.SHScene;
import lamao.soh.core.SHScripts;

/**
 * Loads level from specified file
 * @author lamao
 *
 */
public class SHLoadLevelCommand extends SHBasicCommand
{
	private SHScene scene;
	
	private SHScripts scripts;
	
	public SHLoadLevelCommand(SHScene scene, SHScripts scripts)
	{
		super(1, 1);
		this.scene = scene;
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
			scene.resetAll();
			scripts.loadLevelScript(file.toString());
		}
	}
	
	@Override
	protected String getHelpMessage()
	{
		return "Arguments: <path/to/file>";
	}

}
