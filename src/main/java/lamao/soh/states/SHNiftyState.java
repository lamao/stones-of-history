/** 
 * SHNiftyState.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import com.jme3.app.state.AbstractAppState;
import com.jme3.renderer.RenderManager;
import lamao.soh.SHConstants;

import de.lessvoid.nifty.Nifty;

/**
 * @author lamao
 *
 */
public class SHNiftyState extends AbstractAppState {

	private Nifty nifty;
	
	private String startScreen;
	
	private SHConstants constants;
	
	public SHNiftyState(Nifty nifty,
			String startScreen,
			SHConstants constants)
	{
		super();
		this.nifty = nifty;
		this.startScreen = startScreen;
		this.constants = constants;
        setEnabled(false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(RenderManager renderManager)
	{
		super.render(renderManager);
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
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		if (!enabled)
		{
			nifty.getNiftyMouse().resetMouseCursor();
			nifty.exit();
		}
		else 
		{
			nifty.getNiftyMouse().enableMouseCursor(constants.CURSOR_DEFAULT);
			nifty.gotoScreen(startScreen);
		}
	}
	
	/**
	 * Show screen
	 * @param screenId ID of desired screen
	 */
	public void gotoScreen(String screenId)
	{
		nifty.gotoScreen(screenId);
	}
}
