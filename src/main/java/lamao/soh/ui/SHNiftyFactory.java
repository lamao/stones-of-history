/** 
 * SHNiftyFactory.java 19.12.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui;

import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.InputSystemJme;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.niftygui.RenderDeviceJme;
import com.jme3.niftygui.SoundDeviceJme;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.spi.time.impl.FastTimeProvider;

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
	public static Nifty createNifty(
            SimpleApplication simpleApplication,
            List<String> configurationFiles,
			String startScreen,
			List<ScreenController> controllers)
	{
        NiftyJmeDisplay niftyJmeDisplay = new NiftyJmeDisplay(
                simpleApplication.getAssetManager(),
                simpleApplication.getInputManager(),
                simpleApplication.getAudioRenderer(),
                simpleApplication.getGuiViewPort());
        Nifty nifty = niftyJmeDisplay.getNifty();
		if (configurationFiles.size() > 0)
		{
			String firstFile = configurationFiles.get(0);
			nifty.fromXml(firstFile, startScreen, controllers.toArray(new ScreenController[0]));
		}
		
		for (int i = 1; i < configurationFiles.size(); i++)
		{
			nifty.addXml(configurationFiles.get(i));
		}

        simpleApplication.getGuiViewPort().addProcessor(niftyJmeDisplay);
		nifty.registerMouseCursor("default", "data/cursors/nifty-cursor.png", 0, 23);
		
		return nifty;
	}
}
