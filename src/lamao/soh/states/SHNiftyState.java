/** 
 * SHNiftyState.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import com.jmex.game.state.BasicGameState;

import de.lessvoid.nifty.Nifty;

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
	public SHNiftyState(Nifty nifty, String startScreen)
	{
		super(NAME);
		this.nifty = nifty;
		this.startScreen = startScreen;
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
