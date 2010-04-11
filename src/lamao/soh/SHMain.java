/* 
 * SHMain.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.io.File;

import lamao.soh.console.SHConsoleState;
import lamao.soh.core.SHBall;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHPaddle;
import lamao.soh.states.SHLevelState;
import lamao.soh.utils.SHLevelLoader;
import lamao.soh.utils.SHModelLoader;
import lamao.soh.utils.SHResourceManager;

import com.jme.app.AbstractGame.ConfigShowMode;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
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
		GAME.setConfigShowMode(ConfigShowMode.AlwaysShow);
		GAME.getSettings().setFramerate(2000);
		GAME.start();
		
		//SHResourceManager.getInstance().loadAll(new File("data/model_test.txt"));
		SHResourceManager.getInstance().loadAll(new File(
				"data/epochs/test_epoch/appearence.txt"));
		Node models = (Node)SHModelLoader.load(new File(
				"data/epochs/test_epoch/level1_mw3d.jme"));
		
		SHLevel level = new SHLevelLoader().load(
				SHResourceManager.getInstance(), models, 
				new File("data/epochs/test_epoch/metadata1.xml"));
		level.updateDeletebleBricks();
		
		SHConsoleState console = new SHConsoleState(SHConsoleState.STATE_NAME);
		GameStateManager.getInstance().attachChild(console);
		
		SHLevelState levelState = new SHLevelState();
		levelState.setLevel(level);
		
//		SHLevelGenerator.generate(level.getLevel());
		GameStateManager.getInstance().attachChild(levelState);
		levelState.setActive(true);
	}
}
