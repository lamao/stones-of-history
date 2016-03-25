/* 
 * SHLoadLevelCommand.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.core.service.SHLevelService;

/**
 * Loads level from specified file
 * @author lamao
 *
 */
public class SHLoadLevelCommand extends SHBasicCommand
{
	private SHLevelService scripts;
	
	public SHLoadLevelCommand(SHLevelService scripts)
	{
		super(2, 2);
		this.scripts = scripts;
	}
	
	@Override
	public void processCommand(String[] args)
	{
		scripts.loadLevelScene(new SHEpoch(args[1]), new SHLevel(args[2]));
	}
	
	@Override
	protected String getHelpMessage()
	{
		return "Arguments: <epochId> <scenefilename.dps>";
	}

}
