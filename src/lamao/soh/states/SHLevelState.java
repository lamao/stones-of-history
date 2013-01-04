/* 
 * SHLevelState.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHScene;
import lamao.soh.ui.controllers.SHInGameScreenController;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import com.acarter.scenemonitor.SceneMonitor;
import com.jme.input.InputHandler;
import com.jme.light.PointLight;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Text;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
import com.jme.util.geom.Debugger;
import com.jmex.game.state.BasicGameState;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;

/**
 * Game state for actual game for one level (from starting to winning or
 * loosing).
 * 
 * @author lamao
 *
 */
public class SHLevelState extends BasicGameState
{
	private static final String FINAL_MESSAGE = "final-message";
	public static final String NAME = "Level state";

	/** Level for playing */
	private SHScene _scene = null;
	
	private DisplaySystem displaySystem;
	
	private InputHandler inputHandler;
	
	/** Indicates where draw bounding volumes or not */
	private boolean drawBounds = false;
	
	/** Indicates whether draw normals for scene */
	private boolean drawNormals = false;
	
	private boolean _pause = false;
	
	/** Dispatcher used to fire events */
	private SHEventDispatcher dispatcher;
	
	private SHBreakoutGameContext context;
	
	/** Container for nifty UI elements */
	private Nifty nifty;
	
	private String startNiftyScreen;
	
	private SHInGameScreenController inGameScreenController;
	
	// TODO: Move to constructor scene and inputhandler
	public SHLevelState(SHEventDispatcher dispatcher, 
			SHBreakoutGameContext context,
			DisplaySystem displaySystem,
			Nifty nifty,
			String startNiftyScreen)
	{
		super(NAME);
		
		this.dispatcher = dispatcher;
		this.context = context;
		this.displaySystem = displaySystem;
		this.nifty = nifty;
		this.startNiftyScreen = startNiftyScreen;
		
		PointLight light = new  PointLight();
		light.setEnabled(true);
		light.setLocation(new Vector3f(0, 3, 3));
		light.setAmbient(ColorRGBA.white.clone());
		light.setDiffuse(ColorRGBA.white.clone());
		LightState ls = displaySystem.getRenderer().createLightState();
		ls.attach(light);
		rootNode.setRenderState(ls);
		
		Camera camera = displaySystem.getRenderer().getCamera();
		camera.setLocation(new Vector3f(0, 13, 18));
		camera.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);
		
		setupHandlers();
		
		rootNode.updateRenderState();
		
		 //SceneMonitor.getMonitor().registerNode(rootNode, "Root Node");
         //SceneMonitor.getMonitor().showViewer(true); 
	}
	
	public InputHandler getInputHandler()
	{
		return inputHandler;
	}

	public void setInputHandler(InputHandler inputHandler)
	{
		this.inputHandler = inputHandler;
	}

	public SHScene getScene()
	{
		return _scene;
	}
	
	public void setScene(SHScene scene)
	{
		if (_scene != null && _scene != scene)
		{
			rootNode.detachChild(_scene.getRootNode());
		}
		_scene = scene;
		rootNode.attachChild(_scene.getRootNode());
		rootNode.updateRenderState();
	}
	
	private void setFps(int value)
	{
		Label label = nifty.getCurrentScreen().findNiftyControl(
				"fpsValue", Label.class);
		label.setText(String.valueOf(value));
	}
	
	@Override
	public void update(float tpf)
	{
		nifty.update();
		inputHandler.update(tpf);
		if (!_pause)
		{
			super.update(tpf);
			_scene.update(tpf);
			dispatcher.update(tpf);
		}
		setFps(Math.round(Timer.getTimer().getFrameRate()));
		 //SceneMonitor.getMonitor().updateViewer(tpf);
	}
	
	@Override
	public void render(float tpf)
	{
		super.render(tpf);
		nifty.render(false);
		
		Renderer renderer = DisplaySystem.getDisplaySystem().getRenderer();
		
		if (drawBounds)
		{
			Debugger.drawBounds(rootNode, renderer);
		}
		
		if (drawNormals)
		{
			Debugger.drawNormals(rootNode, renderer);
		}
		
		//SceneMonitor.getMonitor().renderViewer(renderer);
	}
	
	@Override
	public void setActive(boolean active)
	{
		if (!isActive() && active) 
		{
			setPause(false);
			nifty.gotoScreen(startNiftyScreen);
		}
		else if (isActive() && !active)
		{
			nifty.exit();
		}
		super.setActive(active);
	}
	
	private void setupHandlers()
	{
		dispatcher.addHandler("level-completed", new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				// TODO: display 'level completed message'
				_pause = true;
			}
		});
		
		dispatcher.addHandler("level-failed", new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				//TODO: display 'level failed' message
				_pause = true;
			}
		});
		
	}
	
	@Override
	public void cleanup()
	{
		super.cleanup();
		
		//SceneMonitor.getMonitor().cleanup();
	}
	
	public void setPause(boolean pause)
	{
		_pause = pause;		 
	}
	
	public boolean isPause() 
	{
		return _pause;
	}

	public boolean isDrawBounds()
	{
		return drawBounds;
	}

	public void setDrawBounds(boolean drawBounds)
	{
		this.drawBounds = drawBounds;
	}

	public boolean isDrawNormals()
	{
		return drawNormals;
	}

	public void setDrawNormals(boolean drawNormals)
	{
		this.drawNormals = drawNormals;
	}
	
}
