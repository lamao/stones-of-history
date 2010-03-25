/* 
 * SHLevelState.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.states;

import lamao.soh.core.SHLevel;
import lamao.soh.core.SHPaddleInputHandler;

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
	
	private DisplaySystem _display = DisplaySystem.getDisplaySystem();
	
	private SHPaddleInputHandler _input = null;
	
	public SHLevelState()
	{
		super(NAME);
		_level = new SHLevel();
		rootNode.attachChild(_level.getRootNode());
		
		PointLight light = new  PointLight();
		light.setEnabled(true);
		light.setLocation(new Vector3f(0, 0, 3));
		light.setAmbient(ColorRGBA.white.clone());
		light.setDiffuse(ColorRGBA.white.clone());
		LightState ls = _display.getRenderer().createLightState();
		ls.attach(light);
		rootNode.setRenderState(ls);
		
		_fps = Text.createDefaultTextLabel("fps", "FPS");
		_statNode.attachChild(_fps);
		
		rootNode.updateRenderState();
		_statNode.updateRenderState();
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
		_input.update(tpf);
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
			_input = new SHPaddleInputHandler(_level.getPaddle().getModel());
			_input.setConstraints(-8, 8);
		}
		super.setActive(active);
	}
	
	
}
