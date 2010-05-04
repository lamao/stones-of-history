/* 
 * SHLevelState.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import lamao.soh.console.SHConsoleState;
import lamao.soh.console.SHWireFrameCommand;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHScene;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import com.acarter.scenemonitor.SceneMonitor;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.KeyInputAction;
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
import com.jmex.game.state.GameStateManager;

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
//	private SHLevel _level = null;
	private SHScene _scene = null;
	
	/** Node for text objects */
	private Node _statNode = new Node("stat node");
	
	/** Displays FPS */
	private Text _fps = null;
	
	private Text _info = null;
	
	private Text _events = null;
	
	private DisplaySystem _display = DisplaySystem.getDisplaySystem();
	
	private InputHandler _input = new InputHandler();
	
	/** Indicates where draw bounding volumes or not */
	private boolean drawBounds = false;
	
	/** Indicates whether draw normals for scene */
	private boolean drawNormals = false;
	
	private boolean _pause = false;
	
	public SHLevelState()
	{
		super(NAME);
		
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
		bindKeys();
		
		rootNode.updateRenderState();
		_statNode.updateRenderState();
		
		 SceneMonitor.getMonitor().registerNode(rootNode, "Root Node");
         SceneMonitor.getMonitor().showViewer(true); 
	}
	
	public void initTextLabels()
	{
		_fps = Text.createDefaultTextLabel("fps", "FPS");
		_statNode.attachChild(_fps);
		
		_info = Text.createDefaultTextLabel("into", "info");
		_info.setLocalTranslation(_display.getWidth() / 2 - _info.getWidth() / 2, 
				0, 0);
		_statNode.attachChild(_info);
		
		_events = Text.createDefaultTextLabel("events", "events");
		_events.setLocalTranslation(_display.getWidth() * 3 / 4, 0, 0);
		_statNode.attachChild(_events);
	}
	
	public void initConsole()
	{
		SHConsoleState console = (SHConsoleState)GameStateManager.getInstance()
				.getChild(SHConsoleState.STATE_NAME);
		console.add("wired", new SHWireFrameCommand(rootNode));
		
		console.add("bounds", new ISHEventHandler() 
		{
			@Override
			public void processEvent(SHEvent event)
			{
				String[] args = (String[])event.params.get("args");
				drawBounds = Boolean.parseBoolean(args[1]);
			}
		});
		
		console.add("normals", new ISHEventHandler() 
		{
			@Override
			public void processEvent(SHEvent event)
			{
				String args[] = (String[])event.params.get("args");
				if (args.length > 2)
				{
					drawNormals = Boolean.parseBoolean(args[1]);
				}
			}
		});
		
	}
	
	public void bindKeys()
	{
		_input.addAction(new KeyInputAction(){
			public void performAction(InputActionEvent evt)
			{
				SHConsoleState console = (SHConsoleState)GameStateManager
						.getInstance().getChild(SHConsoleState.STATE_NAME);
				console.setActive(true);
			}
		}, "show console", KeyInput.KEY_GRAVE, false);
		
		_input.addAction(new KeyInputAction()
		{
			public void performAction(InputActionEvent evt)
			{
				_pause = !_pause;
			}
		}, "pause", KeyInput.KEY_PAUSE, false);
	}

	public SHScene getScene()
	{
//		return _level;
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

	/* (non-Javadoc)
	 * @see com.jmex.game.state.BasicGameState#update(float)
	 */
	@Override
	public void update(float tpf)
	{
		_input.update(tpf);
		if (!_pause)
		{
			super.update(tpf);
			SHGamePack.input.update(tpf);
			_scene.update(tpf);
		}
		_fps.print("FPS: " + Math.round(Timer.getTimer().getFrameRate()));
		
		 SceneMonitor.getMonitor().updateViewer(tpf);
	}
	
	/* (non-Javadoc)
	 * @see com.jmex.game.state.BasicGameState#render(float)
	 */
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
		
		SceneMonitor.getMonitor().renderViewer(renderer);
	}
	
	/* (non-Javadoc)
	 * @see com.jmex.game.state.GameState#setActive(boolean)
	 */
	@Override
	public void setActive(boolean active)
	{
		if (active)
		{
			SHBreakoutGameContext context = (SHBreakoutGameContext)SHGamePack.context;
			_info.print(Integer.toString(context.getNumDeletableBricks()));
		}
		super.setActive(active);
	}
	
	private void setupHandlers()
	{
		SHEventDispatcher dispatcher = SHGamePack.dispatcher;
		dispatcher.addHandler("level-completed", new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				_events.print("Victory");
				Text win = Text.createDefaultTextLabel("win", "YOU ARE WINNER");
				win.setLocalTranslation(_display.getWidth() / 2 - win.getWidth() / 2,
						_display.getHeight() / 2 - win.getHeight(), 0);
				_statNode.attachChild(win);
				_statNode.updateRenderState();
			}
		});
		
		dispatcher.addHandler("level-failed", new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				_events.print("Defeat");
				Text win = Text.createDefaultTextLabel("fail", "YOU ARE LOOSER");
				win.setLocalTranslation(_display.getWidth() / 2 - win.getWidth() / 2,
						_display.getHeight() / 2 - win.getHeight(), 0);
				_statNode.attachChild(win);
				_statNode.updateRenderState();
			}
		});
		
		dispatcher.addHandler("level-brick-hit", new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				_events.print("Brick hit");
			}
		});
		
		dispatcher.addHandler("level-wall-hit", new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				_events.print("Wall hit: " + event.params.get("wall-type"));
			}
		});
		
		dispatcher.addHandler("level-brick-deleted", new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				_events.print("Brick deleted");
				
				SHBreakoutGameContext context = (SHBreakoutGameContext)
					SHGamePack.context;
				_info.print(Integer.toString(context.getNumDeletableBricks()));
			}
		});
		
		ISHEventHandler bonusHandler = new ISHEventHandler()
		{
			@Override
			public void processEvent(SHEvent event)
			{
				_events.print(event.type + event.params.get("bonus")
						.getClass());
			}
			
		};
		dispatcher.addHandler("level-bonus-activated", bonusHandler);
		dispatcher.addHandler("level-bonus-deactivated", bonusHandler);		
		dispatcher.addHandler("level-bonus-showed", bonusHandler);
	}
	
	/* (non-Javadoc)
	 * @see com.jmex.game.state.BasicGameState#cleanup()
	 */
	@Override
	public void cleanup()
	{
		super.cleanup();
		
		SceneMonitor.getMonitor().cleanup();
	}
	
	
}
