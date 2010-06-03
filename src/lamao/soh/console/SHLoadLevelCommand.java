/* 
 * SHLoadLevelCommand.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import java.io.File;

import lamao.soh.SHMain;
import lamao.soh.core.SHGamePack;
import lamao.soh.utils.deled.SHSceneLoader;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * Loads level from specified file
 * @author lamao
 *
 */
public class SHLoadLevelCommand implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		String[] args = (String[])event.params.get("args");
		File file = new File(args[1]);
		if (file.exists())
		{
			SHGamePack.scene.resetAll();
			SHSceneLoader loader = new SHSceneLoader(SHGamePack.scene);
			loader.load(file);
			SHMain.levelStartupScript();
		}
	}

}
