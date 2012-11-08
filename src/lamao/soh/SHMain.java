/* 
 * SHMain.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.io.File;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHScripts;
import lamao.soh.states.SHLevelState;
import lamao.soh.utils.deled.SHSceneLoader;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jmex.game.StandardGame;
import com.jmex.game.state.BasicGameState;
import com.jmex.game.state.GameStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.jme.input.JmeInputSystem;
import de.lessvoid.nifty.jme.render.JmeRenderDevice;
import de.lessvoid.nifty.jme.sound.JmeSoundDevice;
import de.lessvoid.nifty.tools.TimeProvider;

/**
 * Enter point into the program.
 * @author lamao
 *
 */
public class SHMain
{
	private static StandardGame GAME = null;

	static SHEventDispatcher dispatcher = new SHEventDispatcher();
	
	static SHScene scene = new SHScene();
	
	static SHScripts scripts = new SHScripts(dispatcher , scene);
	
	public static void main(String args[])
	{
		GAME = new StandardGame("Stones of History");
		GAME.start();
		
		scripts.gameStartupScript();
		
		SHGamePack.manager.loadAll(new File(
				"data/epochs/test_epoch/appearence.txt"));
		SHSceneLoader loader = new SHSceneLoader(scene);
		loader.load(new File("data/test/test-level.dps"));
		scripts.levelStartupScript();
		
		SHLevelState levelState = new SHLevelState(dispatcher);
		levelState.setScene(scene);		
		scripts.initializeConsole(levelState);
		
		GameStateManager.getInstance().attachChild(levelState);
		levelState.setActive(true);
		
		NiftyState niftyState = new NiftyState("menu");
		GameStateManager.getInstance().attachChild(niftyState);
//		niftyState.setActive(true);
	}
	
	static class NiftyState extends BasicGameState {

		Nifty nifty;
		/**
		 * @param name
		 */
		public NiftyState(String name)
		{
			super(name);
			nifty = new Nifty(new JmeRenderDevice(), new JmeSoundDevice(), 
							new JmeInputSystem(), new TimeProvider());
			nifty.fromXml("data/nifty/main.xml", "start");
		}
		
		/* (non-Javadoc)
		 * @see com.jmex.game.state.BasicGameState#render(float)
		 */
		@Override
		public void render(float tpf)
		{
			super.render(tpf);
			nifty.render(false);
		}
		
	}
	
}
