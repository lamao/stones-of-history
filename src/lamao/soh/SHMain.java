/* 
 * SHMain.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.io.File;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScripts;
import lamao.soh.states.SHLevelState;
import lamao.soh.utils.deled.SHSceneLoader;

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

	public static void main(String args[])
	{
		GAME = new StandardGame("Stones of History");
		GAME.start();
		
		SHScripts.gameStartupScript();
		
		SHGamePack.manager.loadAll(new File(
				"data/epochs/test_epoch/appearence.txt"));
		SHSceneLoader loader = new SHSceneLoader(SHGamePack.scene);
		loader.load(new File("data/test/test-level.dps"));
		SHScripts.levelStartupScript();
		
		SHLevelState levelState = new SHLevelState();
		levelState.setScene(SHGamePack.scene);
		
		GameStateManager.getInstance().attachChild(levelState);
		levelState.setActive(true);
	}
	
}
