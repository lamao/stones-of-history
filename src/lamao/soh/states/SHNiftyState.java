/** 
 * SHNiftyState.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import lamao.soh.SHConstants;
import lamao.soh.ui.controllers.SHMainMenuScreenController;

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
	
	private Nifty nifty;
	/**
	 * @param name
	 */
	public SHNiftyState(String uiConfigurationFile, String startScreen)
	{
		super(NAME);
		
		nifty = new Nifty(new JmeRenderDevice(), new JmeSoundDevice(), 
						new JmeInputSystem(), new TimeProvider());
		
		ScreenController controllers[] = {
				new SHMainMenuScreenController(this)};
		nifty.fromXml(uiConfigurationFile, startScreen, controllers);
		nifty.registerMouseCursor("default", "data/cursors/cursor-default.png", 15, 15);
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
			nifty.getNiftyMouse().enableMouseCursor(SHConstants.UI_CURSOR_DEFAULT);
			nifty.gotoScreen(SHConstants.UI_SCREEN_START);
		}
	}
}
