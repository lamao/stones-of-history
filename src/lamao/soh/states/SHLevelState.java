/* 
 * SHLevelState.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import lamao.soh.SHLevelGenerator;
import lamao.soh.console.ISHCommandHandler;
import lamao.soh.console.SHConsoleState;
import lamao.soh.console.SHWireFrameCommand;
import lamao.soh.core.SHBrick;
import lamao.soh.core.SHLevel;
import lamao.soh.core.ISHLevelListener;
import lamao.soh.core.SHLevel.SHWallType;
import lamao.soh.core.bonuses.SHBonus;

import com.jme.input.FirstPersonHandler;
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
	private SHLevel _level = null;
	
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
		bindKeys();
		
		rootNode.updateRenderState();
		_statNode.updateRenderState();
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
		console.add("generate", new ISHCommandHandler() 
		{
			@Override
			public String execute(String[] args)
			{
				_level.clear();
				SHLevelGenerator.generate(_level);
				_statNode.detachChildNamed("win");
				rootNode.updateRenderState();
				return null;
			}
		});
		
		console.add("bounds", new ISHCommandHandler() 
		{
			@Override
			public String execute(String[] args)
			{
				drawBounds = Boolean.parseBoolean(args[1]);
				return null;
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
	}

	public SHLevel getLevel()
	{
		return _level;
	}
	
	public void setLevel(SHLevel level)
	{
		if (_level != null && _level != level)
		{
			_level.getListeners().clear();
			rootNode.detachChild(_level.getRootNode());
		}
		_level = level;
		_level.addListener(new SHDefaultLevelListener());
		rootNode.attachChild(_level.getRootNode());
		rootNode.updateRenderState();
	}

	/* (non-Javadoc)
	 * @see com.jmex.game.state.BasicGameState#update(float)
	 */
	@Override
	public void update(float tpf)
	{
		_input.update(tpf);
		super.update(tpf);
		_level.update(tpf);
		_fps.print("FPS: " + Math.round(Timer.getTimer().getFrameRate()));
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
	}
	
	/* (non-Javadoc)
	 * @see com.jmex.game.state.GameState#setActive(boolean)
	 */
	@Override
	public void setActive(boolean active)
	{
		if (active)
		{
			_info.print(Integer.toString(_level.getNumDeletebleBricks()));
		}
		super.setActive(active);
	}
	
	private class SHDefaultLevelListener implements ISHLevelListener
	{
		@Override
		public void completed()
		{
			_events.print("Victory");
			Text win = Text.createDefaultTextLabel("win", "YOU ARE WINNER");
			win.setLocalTranslation(_display.getWidth() / 2 - win.getWidth() / 2,
					_display.getHeight() / 2 - win.getHeight(), 0);
			_statNode.attachChild(win);
			_statNode.updateRenderState();
		}
		
		@Override
		public void brickHit(SHBrick brick)
		{
			_events.print("Brick hit");
		}
		
		@Override
		public void wallHit(SHWallType wall)
		{
			_events.print("Wall hit: " + wall.toString());
		}
		
		@Override
		public void brickDeleted(SHBrick brick)
		{
			_events.print("Brick deleted");
			_info.print(Integer.toString(_level.getNumDeletebleBricks()));
		}
		
		@Override
		public void bonusShowed(SHBonus bonus)
		{
			_events.print("Bonus " + bonus.getClass());
		}
		
		@Override
		public void bonusActivated(SHBonus bonus)
		{
			_events.print("Bonus activated" + bonus.getClass());
		}
		
		@Override
		public void bonusDeactivated(SHBonus bonus)
		{
			_events.print("Bonus deactivated" + bonus.getClass());
			
		}
	}
	
	
}
