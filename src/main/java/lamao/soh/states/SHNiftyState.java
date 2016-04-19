/** 
 * SHNiftyState.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import lamao.soh.SHConstants;

import de.lessvoid.nifty.Nifty;
import lamao.soh.core.service.StateService;

/**
 * @author lamao
 *
 */
public class SHNiftyState extends BasicAppState {

    private Nifty nifty;
	
	private String startScreen;
	
	private SHConstants constants;
	
	public SHNiftyState(Nifty nifty,
			String startScreen,
			SHConstants constants,
            StateService stateService)
	{
		super(stateService);
		this.nifty = nifty;
		this.startScreen = startScreen;
		this.constants = constants;
	}
	
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        nifty.getNiftyMouse().enableMouseCursor(constants.CURSOR_DEFAULT);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        nifty.getNiftyMouse().resetMouseCursor();
        nifty.exit();
	}
	
	/**
	 * Show screen
	 * @param screenId ID of desired screen
	 */
	public void gotoScreen(String screenId)
	{
		nifty.gotoScreen(screenId);
	}

    public void gotoStartScreen() {
        nifty.gotoScreen(startScreen);
    }
}
