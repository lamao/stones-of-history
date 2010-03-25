/* 
 * SHLevel.java 24.03. 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.jme.scene.Node;
import com.jme.scene.Spatial;

/**
 * Game level. Contains bricks, balls, bonuses, paddle and perform all
 * interlevel logic.
 * @author lamao
 *
 */
public class SHLevel
{
	/** Wall types */
	public enum SHWallType 
	{
		LEFT(0), RIGHT(1), TOP(2), BOTTOM(3);
		
		private int _type;
		SHWallType(int type)
		{
			_type = type;
		}
		
		public int intValue()
		{
			return _type;
		}
	};
	
	
	/** All brick in current level */ 
	private List<SHBrick> _bricks = new LinkedList<SHBrick>();
	
	/** Bricks to delete from level */
	private List<SHBrick> _bricksToDelete = new LinkedList<SHBrick>();
	
	/** All balls in current level */
	private List<SHBall> _balls = new LinkedList<SHBall>();
	
	/** Paddle for current level */
	private SHPaddle _paddle = null;
	
	/** Root node for entire level */
	private Node _rootNode = new Node("level root");
	
	/** Root node for bricks. */
	private Node _bricksRoot = new Node("bricks root");
	
	/** Walls of level. @see {@link SHWalls} */ 
	private Spatial[] _walls = new Spatial[4];
	
	/** Indicates whether bottom wall is active (e.i. it now repulse all balls) 
	 * or not (default behavior)
	 */
	private boolean _bottomWallActive = false;
	
	public SHLevel()
	{
		_rootNode.attachChild(_bricksRoot);
	}
	
	
	public List<SHBrick> getBricks()
	{
		return _bricks;
	}

	public void setBricks(List<SHBrick> bricks)
	{
		_bricks = bricks;
	}
	
	public void addBricks(List<SHBrick> bricks)
	{
		_bricks.addAll(bricks);
		for (SHBrick brick : bricks)
		{
			_bricksRoot.attachChild(brick.getModel());
		}
		_bricksRoot.updateModelBound();
		_bricksRoot.updateRenderState();
	}
	
	public void addBrick(SHBrick brick)
	{
		_bricks.add(brick);
		_bricksRoot.attachChild(brick.getModel());
		_bricksRoot.updateModelBound();
		_bricksRoot.updateRenderState();
	}
	
	public void deleteBrick(SHBrick brick)
	{
		_bricks.remove(brick);
		_bricksRoot.detachChild(brick.getModel());
		_bricksRoot.updateModelBound();
		_bricksRoot.updateRenderState();
	}
	
	public void deleteBricks(List<SHBrick> bricks)
	{
		for (SHBrick brick: bricks)
		{
			_bricksRoot.detachChild(brick.getModel());
		}
		_bricksRoot.updateModelBound();
		_bricksRoot.updateRenderState();
		
		_bricks.removeAll(bricks);
	}

	public List<SHBall> getBalls()
	{
		return _balls;
	}

	public void setBalls(List<SHBall> balls)
	{
		_balls = balls;
	}
	
	public void addBall(SHBall ball)
	{
		_balls.add(ball);
		_rootNode.attachChild(ball.getModel());
		_rootNode.updateModelBound();
		_rootNode.updateRenderState();
	}
	
	public void addBalls(List<SHBall> balls)
	{
		_balls.addAll(balls);
		for (SHBall ball : balls)
		{
			_rootNode.attachChild(ball.getModel());
		}
		_rootNode.updateModelBound();
		_rootNode.updateRenderState();
	}
	
	public void deleteBall(SHBall ball)
	{
		_rootNode.detachChild(ball.getModel());
		_balls.remove(ball);
		_rootNode.updateModelBound();
		_rootNode.updateRenderState();
	}
	
	public void deleteBalls(List<SHBall> balls)
	{
		for (SHBall ball : balls)
		{
			_rootNode.detachChild(ball.getModel());
		}
		_rootNode.updateModelBound();
		_rootNode.updateRenderState();
		
		_balls.removeAll(balls);
	}

	public SHPaddle getPaddle()
	{
		return _paddle;
	}

	public void setPaddle(SHPaddle paddle)
	{
		if (_paddle != null)
		{
			_rootNode.detachChild(_paddle.getModel());
		}
		_paddle = paddle;
		_rootNode.attachChild(paddle.getModel());
			
	}
	
	public Node getRootNode()
	{
		return _rootNode;
	}
	
	public void setWall(Spatial model, SHWallType type)
	{
		if (_walls[type.intValue()] != null)
		{
			_rootNode.detachChild(_walls[type.intValue()]);
		}
		_walls[type.intValue()] = model;
		_rootNode.attachChild(model);
		_rootNode.updateModelBound();
		_rootNode.updateRenderState();
	}
	
	public Spatial getWall(SHWallType type)
	{
		return _walls[type.intValue()];
	}
	
	public void setBottomWallActive(boolean active)
	{
		_bottomWallActive = active;
	}
	
	public boolean isBottomWallActive()
	{
		return _bottomWallActive;
	}
	
	/** 
	 * Deletes all bricks and balls from level. Changes paddle state to default. 
	 * Does not effects on walls.
	 */
	public void clear()
	{
		deleteBricks(_bricks);
		deleteBalls(_balls);
		if (!(_paddle.getHitHandler() instanceof SHDefaultPaddleHitHandler))
		{
			_paddle.setHitHandler(new SHDefaultPaddleHitHandler());
		}
	}
	
	/** Update cycle for level. Checks collisions, removes bricks, activates
	 * bonuses etc.
	 * @param tpf - time since last update
	 */
	public void update(float tpf)
	{
		for (SHBall ball : _balls)
		{
			if (ball.getModel().hasCollision(_walls[SHWallType.LEFT.intValue()], 
				false) || 
				ball.getModel().hasCollision(_walls[SHWallType.RIGHT.intValue()], 
				false))
			{
				ball.getVelocity().x = -ball.getVelocity().x;
			}
			if (ball.getModel().hasCollision(_walls[SHWallType.TOP.intValue()], 
				false)) 
			{
				ball.getVelocity().y = -ball.getVelocity().y;
			}
			if (ball.getModel().hasCollision(_walls[SHWallType.BOTTOM.intValue()], 
					false))
			{
				if (isBottomWallActive())
				{
					ball.getVelocity().y = -ball.getVelocity().y;
				}
			}
			
			for (SHBrick brick : _bricks)
			{
				if (ball.getModel().hasCollision(brick.getModel(), false))
				{
					ball.onHit(brick);
					if (brick.getStrength() <= 0)
					{
						_bricksToDelete.add(brick);
					}
				}
			}
			if (ball.getModel().hasCollision(_paddle.getModel(), false))
			{
				_paddle.onHit(ball);
			}
		}
		
		processDeleteQueues();
	}
	
	/** Process all delete queues and removes entities in them */
	private void processDeleteQueues()
	{
		deleteBricks(_bricksToDelete);
	}
	
}
