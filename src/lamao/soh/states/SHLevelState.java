/* 
 * SHLevelState.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import lamao.soh.SHConstants;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScene;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import com.acarter.scenemonitor.SceneMonitor;
import com.jme.input.InputHandler;
import com.jme.light.PointLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Text;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
import com.jme.util.geom.Debugger;
import com.jmex.game.state.BasicGameState;

/**
 * Game state for actual game for one level (from starting to winning or
 * loosing).
 * 
 * @author lamao
 *
 */
public class SHLevelState extends BasicGameState
{
	public final static String NAME = "Level state";

	/** Level for playing */
	private SHScene _scene = null;
	
	/** Node for text objects */
	private Node _statNode = new Node("stat node");
	
	/** Displays FPS */
	private Text _fps = null;
	
	private Text _info = null;
	
	private Text _buildNumber = null;
	
	private DisplaySystem _display = DisplaySystem.getDisplaySystem();
	
	private InputHandler inputHandler;
	
	/** Indicates where draw bounding volumes or not */
	private boolean drawBounds = false;
	
	/** Indicates whether draw normals for scene */
	private boolean drawNormals = false;
	
	private boolean _pause = false;
	
	/** Dispatcher used to fire events */
	private SHEventDispatcher dispatcher;
	
	
	public SHLevelState(SHEventDispatcher dispatcher)
	{
		super(NAME);
		
		this.dispatcher = dispatcher;
		
		PointLight light = new  PointLight();
		light.setEnabled(true);
		light.setLocation(new Vector3f(0, 0, 3));
		light.setAmbient(ColorRGBA.white.clone());
		light.setDiffuse(ColorRGBA.white.clone());
		LightState ls = _display.getRenderer().createLightState();
		ls.attach(light);
		rootNode.setRenderState(ls);
		
		initTextLabels();
		initConsole();
		setupHandlers();
		
		rootNode.updateRenderState();
		_statNode.updateRenderState();
		
		 //SceneMonitor.getMonitor().registerNode(rootNode, "Root Node");
         //SceneMonitor.getMonitor().showViewer(true); 
	}
	
	public void initTextLabels()
	{
		_fps = Text.createDefaultTextLabel("fps", "FPS");
		_statNode.attachChild(_fps);
		
		_info = Text.createDefaultTextLabel("into", "info");
		_info.setLocalTranslation(_display.getWidth() / 2 - _info.getWidth() / 2, 
				0, 0);
		_statNode.attachChild(_info);
		
		_buildNumber = Text.createDefaultTextLabel("build-number", "BUILD #" + 
				SHConstants.BUILD_NUMBER);
		_buildNumber.setLocalTranslation(
				_display.getWidth() - _buildNumber.getWidth() - 20, 
				_display.getHeight() - _buildNumber.getHeight(), 0);
		_statNode.attachChild(_buildNumber);
	}
	
	public void initConsole()
	{
		
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

	@Override
	public void update(float tpf)
	{
		inputHandler.update(tpf);
		if (!_pause)
		{
			super.update(tpf);
			SHGamePack.input.update(tpf);
			_scene.update(tpf);
			dispatcher.update(tpf);
			SHBreakoutGameContext context = SHGamePack.context;
			_info.print(Integer.toString(context.getNumDeletableBricks()));
		}
		_fps.print("FPS: " + Math.round(Timer.getTimer().getFrameRate()));
		
		 //SceneMonitor.getMonitor().updateViewer(tpf);
	}
	
	@Override
	public void render(float tpf)
	{
		super.render(tpf);
		
		Renderer renderer = DisplaySystem.getDisplaySystem().getRenderer();
		renderer.draw(_statNode);
		
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
		super.setActive(active);
	}
	
	private void setupHandlers()
	{
		dispatcher.addHandler("level-completed", new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				Text win = Text.createDefaultTextLabel("win", "YOU ARE WINNER");
				win.setLocalTranslation(_display.getWidth() / 2 - win.getWidth() / 2,
						_display.getHeight() / 2 - win.getHeight(), 0);
				_statNode.attachChild(win);
				_statNode.updateRenderState();
				_pause = true;
			}
		});
		
		dispatcher.addHandler("level-failed", new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				Text win = Text.createDefaultTextLabel("fail", "YOU ARE LOOSER");
				win.setLocalTranslation(_display.getWidth() / 2 - win.getWidth() / 2,
						_display.getHeight() / 2 - win.getHeight(), 0);
				_statNode.attachChild(win);
				_statNode.updateRenderState();
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
