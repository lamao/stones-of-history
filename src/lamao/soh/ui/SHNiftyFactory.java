/** 
 * SHNiftyFactory.java 19.12.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui;

import java.util.List;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.jme.input.JmeInputSystem;
import de.lessvoid.nifty.jme.render.JmeRenderDevice;
import de.lessvoid.nifty.jme.sound.JmeSoundDevice;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.TimeProvider;

/**
 * Factory class to create {@link Nifty} instanse
 * @author lamao
 *
 */
public class SHNiftyFactory
{
	/**
	 * Create {@link Nifty} instanse
	 * @param configurationFiles list of configuration files with UI
	 * @param startScreen name of start screen 
	 * @param controllers list of screen controllers for screens in UI
	 * @return instantiated {@link Nifty} object
	 */
	public static Nifty createNifty(List<String> configurationFiles,
			String startScreen,
			List<ScreenController> controllers)
	{
		Nifty nifty = new Nifty(new JmeRenderDevice(), new JmeSoundDevice(), 
				new JmeInputSystem(), new TimeProvider());
		if (configurationFiles.size() > 0) 
		{
			String firstFile = configurationFiles.get(0);
			ScreenController controllersArray[] = controllers.toArray(new ScreenController[0]);
			nifty.fromXml(firstFile, startScreen, controllersArray);
		}
		
		for (int i = 1; i < configurationFiles.size(); i++)
		{
			nifty.addXml(configurationFiles.get(i));
		}
		
		nifty.registerMouseCursor("default", "data/cursors/nifty-cursor.png", 0, 23);
		
		return nifty;
	}
}
