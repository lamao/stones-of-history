/* 
 * SimpleTest.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.util.LinkedList;
import java.util.List;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHBrick;
import lamao.soh.core.SHDefaultBallMover;
import lamao.soh.core.SHPaddle;

import com.jme.app.SimpleGame;
import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.input.KeyInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.KeyInputAction;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.Text;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;

/**
 * Simple integration test for basic game entities (ball, bricks and paddle)
 * @author lamao
 *
 */
public class SHGameIntegrationTest extends SimpleGame
{

	private List<SHBrick> _bricks = new LinkedList<SHBrick>();
	private Node _brickNode = new Node("bricks");
	private SHBall _ball;
	private SHPaddle _paddle;
	
	private Spatial _leftWall;
	private Spatial _rightWall;
	private Spatial _topWall;
	private Spatial _bottomWall;
	
	private Text _fps;
	
	public static void main(String args[])
	{
		SHGameIntegrationTest test = new SHGameIntegrationTest();
		test.setConfigShowMode(ConfigShowMode.AlwaysShow);
		test.start();
	}
	
	/* (non-Javadoc)
	 * @see com.jme.app.BaseSimpleGame#simpleInitGame()
	 */
	@Override
	protected void simpleInitGame()
	{
		buildLevel();		
		createEntities();
		bindKeys();
		rootNode.updateRenderState();
		
		_fps = Text.createDefaultTextLabel("fps", "FPS");
		statNode.attachChild(_fps);
	}
	
	private void buildLevel()
	{
		_leftWall = new Box("left wall", new Vector3f(-10, 0, 0), 1, 10, 1);
		_leftWall.setModelBound(new BoundingBox());
		_leftWall.updateModelBound();
		rootNode.attachChild(_leftWall);
		
		_rightWall = new Box("right wall", new Vector3f(10, 0, 0), 1, 10, 1);
		_rightWall.setModelBound(new BoundingBox());
		_rightWall.updateModelBound();
		rootNode.attachChild(_rightWall);
		
		_topWall = new Box("top wall", new Vector3f(0, 10, 0), 10, 1, 1);
		_topWall.setModelBound(new BoundingBox());
		_topWall.updateModelBound();
		rootNode.attachChild(_topWall);
		
		_bottomWall = new Box("bottom wall", new Vector3f(0, -10, 0), 10, 1, 1);
		_bottomWall.setModelBound(new BoundingBox());
		_bottomWall.updateModelBound();
		rootNode.attachChild(_bottomWall);
	}
	
	private void createEntities()
	{
		Box box = new Box("brick1", new Vector3f(0, 2, 0), 1, 0.5f, 0.5f);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		SHBrick brick = new SHBrick(box);
		brick.setStrength(Integer.MAX_VALUE);
		_brickNode.attachChild(brick.getModel());
		_bricks.add(brick);
		
		box = new Box("brick2", new Vector3f(2, 3, 0), 1, 0.5f, 0.5f);
		box.setDefaultColor(ColorRGBA.blue);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		brick = new SHBrick(box);
		brick.setStrength(Integer.MAX_VALUE);
		brick.setGlass(true);
		_brickNode.attachChild(brick.getModel());
		_bricks.add(brick);
		
		rootNode.attachChild(_brickNode);
		
		box = new Box("paddle", new Vector3f(0, 0, 0), 1, 0.5f, 0.5f);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		_paddle = new SHPaddle(box);
		_paddle.setLocation(0, -7, 0);
		rootNode.attachChild(_paddle.getModel());
		
		_ball = new SHBall(new Sphere("ball", 15, 15, 0.25f));
		_ball.getModel().setModelBound(new BoundingSphere());
		_ball.getModel().updateModelBound();
		_ball.setLocation(-2, 0, 0);
		_ball.setVelocity(-3 ,3 ,0);
		_ball.getModel().addController(new SHDefaultBallMover(_ball));
		rootNode.attachChild(_ball.getModel());
		
	}
	
	public void bindKeys()
	{
		input.addAction(new KeyInputAction(){
			public void performAction(InputActionEvent evt)
			{
				_paddle.getModel().getLocalTranslation().x -= 0.01f;
			}
		}, "move left", KeyInput.KEY_NUMPAD4, true);
		
		input.addAction(new KeyInputAction(){
			public void performAction(InputActionEvent evt)
			{
				_paddle.getModel().getLocalTranslation().x += 0.01f;
			}
		}, "move right", KeyInput.KEY_NUMPAD6, true);
	}
	
	/* (non-Javadoc)
	 * @see com.jme.app.BaseSimpleGame#simpleUpdate()
	 */
	@Override
	protected void simpleUpdate()
	{
		super.simpleUpdate();
		
		if (_ball.getModel().hasCollision(_leftWall, false) || 
			_ball.getModel().hasCollision(_rightWall, false))
		{
			_ball.getVelocity().x = -_ball.getVelocity().x;
		}
		if (_ball.getModel().hasCollision(_topWall,false) ||
			_ball.getModel().hasCollision(_bottomWall, false))
		{
			_ball.getVelocity().y = -_ball.getVelocity().y;
		}
		if (_ball.getModel().hasCollision(_paddle.getModel(), false))
		{
			_paddle.onHit(_ball);
		}
		
		for (SHBrick brick : _bricks) 
		{
			if (_ball.getModel().hasCollision(brick.getModel(), false))
			{
				_ball.onHit(brick);
			}
		}
		
		_fps.print(Float.toString(timer.getFrameRate()));
		
	}

}
