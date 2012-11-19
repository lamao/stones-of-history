/* 
 * SHMain.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import lamao.soh.core.SHBreakoutEntityFactory;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHScripts;
import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;
import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.deled.SHSceneLoader;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jmex.game.StandardGame;
import com.jmex.game.state.GameStateManager;

/**
 * Enter point into the program.
 * @author lamao
 *
 */
public class SHMain
{
	private static StandardGame GAME = null;

	static SHEventDispatcher dispatcher = new SHEventDispatcher();
	
	static SHScene scene = new SHScene(null, null);
	
	static SHBreakoutGameContext context = new SHBreakoutGameContext();
	
	static SHResourceManager resourceManager = new SHResourceManager();
	
	static SHSceneLoader sceneLoader = new SHSceneLoader(scene, new SHBreakoutEntityFactory(resourceManager));
	
	static SHScripts scripts = new SHScripts(dispatcher , scene, context, resourceManager, sceneLoader);
	
	public static void main(String args[])
	{
		GAME = new StandardGame("Stones of History");
		GAME.start();
		
		scripts.gameStartupScript();
		
		scripts.loadEpochScript("data/epochs/test_epoch/appearence.txt");
		scripts.loadLevelScript("data/test/test-level.dps");
		
		SHLevelState levelState = scripts.initializeLevelState(dispatcher, scene);
		scripts.initializeConsole(levelState);
		
		GameStateManager.getInstance().attachChild(levelState);
//		levelState.setActive(true);
		
		SHNiftyState niftyState = new SHNiftyState( 
				SHConstants.UI_FILE, SHConstants.UI_SCREEN_START);		
		GameStateManager.getInstance().attachChild(niftyState);
		niftyState.setActive(true);
	}
	
	public static void exit() 
	{
		GAME.shutdown();
	}
	
}
