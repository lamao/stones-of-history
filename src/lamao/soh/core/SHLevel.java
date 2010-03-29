/* 
 * SHLevel.java 24.03. 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.bonuses.SHDefaultMover;

import com.jme.input.InputHandler;
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
	private List<SHBrick> _bricks = new ArrayList<SHBrick>();
	
	/** Number of bricks that can be destroyed (e.i. they are not superbricks */
	private int _numDeletebleBricks = 0;
	
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
	
	/** Input handler for level */
	private InputHandler _input = null;
	
	private List<ISHLevelListener> _listeners = new LinkedList<ISHLevelListener>();
	
	/** Bonuses */
	private Map<SHBrick, SHBonus> _bonuses = new HashMap<SHBrick, SHBonus>();
	
	/** Node that contains bonuses */
	private Node _bonusNode = new Node("bonuses");
	
	/** Showed bonuses */
	private List<SHBonus> _showedBonuses = new ArrayList<SHBonus>();
	
	/** Active bonuses */
	private List<SHBonus> _activeBonuses = new ArrayList<SHBonus>();
	
	public SHLevel()
	{
		_rootNode.attachChild(_bricksRoot);
		_rootNode.attachChild(_bonusNode);
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
	
	public int getNumDeletebleBricks()
	{
		return _numDeletebleBricks;
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
	
	public void setInputHandler(InputHandler input)
	{
		_input = input;
	}
	
	public InputHandler getInputHandler()
	{
		return _input;
	}
	
	public List<ISHLevelListener> getListeners()
	{
		return _listeners;
	}
	
	public void addListener(ISHLevelListener listener)
	{
		_listeners.add(listener);
	}
	
	public void removeListener(ISHLevelListener listener)
	{
		_listeners.remove(listener);
	}

	
	public Map<SHBrick, SHBonus> getBonuses()
	{
		return _bonuses;
	}


	public void setBonuses(Map<SHBrick, SHBonus> bonuses)
	{
		_bonuses = bonuses;
	}
	
	public List<SHBonus> getActiveBonuses()
	{
		return _activeBonuses;
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
		_bonusNode.detachAllChildren();
		_bonuses.clear();
		_showedBonuses.clear();
		_activeBonuses.clear();
		
	}
	
	/** Update cycle for level. Checks collisions, removes bricks, activates
	 * bonuses etc.
	 * @param tpf - time since last update
	 */
	public void update(float tpf)
	{
		if (_input != null)
		{
			_input.update(tpf);
		}
		processActiveBonuses(tpf);
		
		processCollisions();
		
		if (_numDeletebleBricks == 0)
		{
			fireComplete();
		}
		
	}
	
	/** 
	 * Process all collisions (ball with walls, ball with bricks, ball with
	 * paddle
	 */
	private void processCollisions()
	{
		for (SHBall ball : _balls)
		{
			if (ball.getModel().hasCollision(_walls[SHWallType.LEFT.intValue()], 
				false))
			{
				ball.getVelocity().x = -ball.getVelocity().x;
				fireWallHit(SHWallType.LEFT);
			}
			else if (ball.getModel().hasCollision(_walls[SHWallType.RIGHT.intValue()], 
					false))
			{
				ball.getVelocity().x = -ball.getVelocity().x;
				fireWallHit(SHWallType.RIGHT);
			}
			if (ball.getModel().hasCollision(_walls[SHWallType.TOP.intValue()], 
				false)) 
			{
				ball.getVelocity().y = -ball.getVelocity().y;
				fireWallHit(SHWallType.TOP);
			}
			if (ball.getModel().hasCollision(_walls[SHWallType.BOTTOM.intValue()], 
					false))
			{
				if (isBottomWallActive())
				{
					ball.getVelocity().y = -ball.getVelocity().y;
				}
				fireWallHit(SHWallType.BOTTOM);
			}

			SHBrick brick = null;
			for (int i = 0; i < _bricks.size(); i++)
			{
				brick = _bricks.get(i);
				if (ball.getModel().hasCollision(brick.getModel(), false))
				{
					fireBrickHit(brick);
					ball.onHit(brick);
					if (brick.getStrength() <= 0)
					{
						_numDeletebleBricks--;
						deleteBrick(brick);
						i--;
						fireBrickDeleted(brick);
						showBonusIfPresent(brick);
					}
				}
			}
			if (ball.getModel().hasCollision(_paddle.getModel(), false))
			{
				_paddle.onHit(ball);
			}
		}
		
		SHBonus bonus = null;
		for (int i = 0; i < _showedBonuses.size(); i++)
		{
			bonus = _showedBonuses.get(i);
			if (bonus.getModel().hasCollision(_paddle.getModel(), false))
			{
				_bonusNode.detachChild(bonus.getModel());
				_showedBonuses.remove(bonus);
				i--;
				boolean needAdd = true;
				
				if (bonus.isAddictive())
				{
					SHBonus activeBonus = findSuchActiveBonus(bonus);
					if (activeBonus != null)
					{
						needAdd = false;
						activeBonus.increaseDuration(bonus.getDuration());
					}
				}
				if (needAdd)
				{
					_activeBonuses.add(bonus);
					bonus.apply(this);
					fireBonusActivated(bonus);
				}
			} 
			else if (bonus.getModel().hasCollision(
					_walls[SHWallType.BOTTOM.intValue()], false))
			{
				_bonusNode.detachChild(bonus.getModel());
				_showedBonuses.remove(bonus);
				i--;
			}
			
		}
	}
	
	/** 
	 * Decrease duration of bonus by <code>tpf</code>. If duration < 0
	 * bonus is deactivated and removed.
	 */
	private void processActiveBonuses(float tpf)
	{
		SHBonus bonus = null;
		for (int i = 0; i < _activeBonuses.size(); i++)
		{
			bonus = _activeBonuses.get(i);
			bonus.decreaseDuration(tpf);
			if (bonus.getDuration() <= 0)
			{
				_activeBonuses.remove(i);
				bonus.cleanup(this);
				fireBonusDeactivated(bonus);
			}
		}
	}
	
	/**
	 * If some bonus is associated with given brick it is attached to 
	 * <code>_bonusNode</code> and starts its falling down.
	 * @param brick
	 */
	private void showBonusIfPresent(SHBrick brick)
	{
		SHBonus bonus = _bonuses.get(brick);
		if (bonus != null)
		{
			_bonuses.remove(brick);
			_showedBonuses.add(bonus);
			fireBonusShowed(bonus);
			
			bonus.setLocation(brick.getLocation().clone());
			bonus.getModel().addController(new SHDefaultMover(bonus.getModel()));

			_bonusNode.attachChild(bonus.getModel());
			_bonusNode.updateModelBound();
			_bonusNode.updateRenderState();
		}
	}
	
	/** 
	 * Updates number of bricks that can be deleted. This method should be
	 * invoked every time bricks are changed outside the <code>SHLevel</code> 
	 * class or using public methods.
	 */
	public void updateDeletebleBricks()
	{
		_numDeletebleBricks = 0;
		for (SHBrick brick : _bricks)
		{
			if (brick.getStrength() != Integer.MAX_VALUE)
			{
				_numDeletebleBricks++;
			}
		}
	}
	
	/**
	 * Finds such type of bonus which is already activated
	 * @return
	 */
	private SHBonus findSuchActiveBonus(SHBonus bonus)
	{
		SHBonus result = null;
		for (SHBonus activeBonus : _activeBonuses)
		{
			if (activeBonus.getClass() == bonus.getClass())
			{
				result = activeBonus;
				break;
			}
		}
		return result;
	}
	
	private void fireComplete()
	{
		for (ISHLevelListener listener : _listeners)
		{
			listener.completed();
		}
		_numDeletebleBricks--;
	}
	
	private void fireBrickHit(SHBrick brick)
	{
		for (ISHLevelListener listener : _listeners)
		{
			listener.brickHit(brick);
		}
	}
	
	private void fireWallHit(SHWallType wall)
	{
		for (ISHLevelListener listener : _listeners)
		{
			listener.wallHit(wall);
		}
	}
	
	private void fireBrickDeleted(SHBrick brick)
	{
		for (ISHLevelListener listener : _listeners)
		{
			listener.brickDeleted(brick);
		}
	}
	
	private void fireBonusShowed(SHBonus bonus)
	{
		for (ISHLevelListener listener : _listeners)
		{
			listener.bonusShowed(bonus);
		}
	}
	
	private void fireBonusActivated(SHBonus bonus)
	{
		for (ISHLevelListener listener : _listeners)
		{
			listener.bonusActivated(bonus);
		}
	}
	
	private void fireBonusDeactivated(SHBonus bonus)
	{
		for (ISHLevelListener listener : _listeners)
		{
			listener.bonusDeactivated(bonus);
		}
	}
	
}
