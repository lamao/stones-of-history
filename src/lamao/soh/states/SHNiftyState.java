/** 
 * SHNiftyState.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import java.util.List;

import com.jmex.game.state.BasicGameState;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.jme.input.JmeInputSystem;
import de.lessvoid.nifty.jme.render.JmeRenderDevice;
import de.lessvoid.nifty.jme.sound.JmeSoundDevice;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.TimeProvider;

/**
 * @author lamao
 *
 */
public class SHNiftyState extends BasicGameState {

	public static final String NAME = "menu";
	
	private static final String CURSOR_DEFAULT = "default";
	
	private Nifty nifty;
	
	private String startScreen;
	
	/**
	 * @param name
	 */
	public SHNiftyState(String uiConfigurationFile, String startScreen,
			List<ScreenController> controllers)
	{
		super(NAME);
		this.startScreen = startScreen;
		
		nifty = new Nifty(new JmeRenderDevice(), new JmeSoundDevice(), 
						new JmeInputSystem(), new TimeProvider());
		
		ScreenController controllersArray[] = controllers.toArray(new ScreenController[0]);
		nifty.fromXml(uiConfigurationFile, startScreen, controllersArray);
		nifty.registerMouseCursor("default", "data/cursors/nifty-cursor.png", 0, 23);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(float tpf)
	{
		super.render(tpf);
		nifty.render(false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float tpf)
	{
		super.update(tpf);
		nifty.update();
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void setActive(boolean active)
	{
		super.setActive(active);
		if (!active) 
		{
			nifty.getNiftyMouse().resetMouseCursor();
			nifty.exit();
		}
		else 
		{
			nifty.getNiftyMouse().enableMouseCursor(CURSOR_DEFAULT);
			nifty.gotoScreen(startScreen);
		}
	}
}
