/* 
 * SHLevelState.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import lamao.soh.core.SHBrick;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHLevelListener;
import lamao.soh.core.SHLevel.SHWallType;

import com.jme.light.PointLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Text;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
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
	private SHLevel _level = null;
	
	/** Node for text objects */
	private Node _statNode = new Node("stat node");
	
	/** Displays FPS */
	private Text _fps = null;
	
	private Text _info = null;
	
	private Text _events = null;
	
	private DisplaySystem _display = DisplaySystem.getDisplaySystem();
	
	public SHLevelState()
	{
		super(NAME);
		_level = new SHLevel();
		_level.addListener(new SHDefaultLevelListener());
		rootNode.attachChild(_level.getRootNode());
		
		PointLight light = new  PointLight();
		light.setEnabled(true);
		light.setLocation(new Vector3f(0, 0, 3));
		light.setAmbient(ColorRGBA.white.clone());
		light.setDiffuse(ColorRGBA.white.clone());
		LightState ls = _display.getRenderer().createLightState();
		ls.attach(light);
		rootNode.setRenderState(ls);
		
		initTextLabels();
		
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

	public SHLevel getLevel()
	{
		return _level;
	}

	/* (non-Javadoc)
	 * @see com.jmex.game.state.BasicGameState#update(float)
	 */
	@Override
	public void update(float tpf)
	{
		super.update(tpf);
		_level.update(tpf);
		_fps.print(Float.toString(Timer.getTimer().getFrameRate()));
	}
	
	/* (non-Javadoc)
	 * @see com.jmex.game.state.BasicGameState#render(float)
	 */
	@Override
	public void render(float tpf)
	{
		super.render(tpf);
		DisplaySystem.getDisplaySystem().getRenderer().draw(_statNode);
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
	
	private class SHDefaultLevelListener implements SHLevelListener
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
	}
	
	
}
