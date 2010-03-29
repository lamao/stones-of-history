/* 
 * SHBall.java 23.10.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.SharedMesh;
import com.jme.scene.SharedNode;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;

/**
 * Ball entity.
 * @author lamao
 *
 */
public class SHBall extends SHEntity
{
	/** Number of created shared objects within class. Used internally for 
	 * naming 
	 */
	private static int numSharedObjects = 0;
	
	/** Ball velocity */
	private Vector3f _velocity;
	
	public SHBall(Spatial model, Vector3f velocity)
	{
		super(model);
		_velocity = velocity;
	}
	
	/** Create ball with velocity = (0, 1, 0) */
	public SHBall(Spatial model)
	{
		this(model, Vector3f.UNIT_Y.clone());
	}
	
	/** Ball without model and same parameters as in {@link #SHBall(Spatial)} */
	public SHBall()
	{
		this(null);
	}

	public Vector3f getVelocity()
	{
		return _velocity;
	}

	public void setVelocity(Vector3f velocity)
	{
		_velocity = velocity;
	}
	
	public void setVelocity(float x, float y, float z)
	{
		_velocity.set(x, y, z);
	}

	/** 
	 * Reacts on collision with brick. If brick is glass ball does not change
	 * its velocity (e.i it moves thought the brick)<br> 
	 * <b>NOTE:</b> velocity after repulsing is 
	 * calculated based on <code>getLocalTranslation()</code>. So both 
	 * ball and brick models must be centered around their locations.<br>
	 * <b>NOTE:</b> It is supposed that bricks really intersects with ball.
	 * Method doesn't check this.
	 */
	public void onHit(SHBrick brick)
	{
		brick.hit();
		if (brick.isGlass())
			return;
		
		BoundingBox box = (BoundingBox)brick.getModel().getWorldBound();
		Vector3f boxLoc = brick.getModel().getLocalTranslation();
		
		// if ball hits up or bottom side change velocity.y component
		// if ball hits left or right side change velocity.x component
		// else ball hits in corner repulse it back
		if (SHUtils.inRange(getModel().getLocalTranslation().x, 
							boxLoc.x - box.xExtent,
							boxLoc.x + box.xExtent))
		{
			_velocity.y = -_velocity.y;
		}
		else if (SHUtils.inRange(getModel().getLocalTranslation().y, 
							boxLoc.y - box.yExtent,
							boxLoc.y + box.yExtent))
		{
			_velocity.x = -_velocity.x;
		}
		else if (Math.abs(Math.abs(_velocity.x) - Math.abs(_velocity.y)) < 0.001f)
		{
			_velocity.multLocal(-1);
		}
		else 
		{
			float boxLeft = boxLoc.x - box.xExtent;
			float boxRight = boxLoc.x + box.xExtent;
			float boxUp = boxLoc.y + box.yExtent;
			float boxDown = boxLoc.y - box.yExtent;
			
			if (getLocation().y < boxLoc.y)
			{
				if (Math.abs(boxLeft - getLocation().x) < Math.abs(boxDown - getLocation().y) ||
					Math.abs(boxRight - getLocation().x) < Math.abs(boxDown - getLocation().y))
				{
					_velocity.y = -_velocity.y;
				}
				else
				{
					_velocity.x = -_velocity.x;
				}
			}
			else
			{
				if (Math.abs(boxLeft - getLocation().x) < Math.abs(boxUp - getLocation().y) ||
					Math.abs(boxRight - getLocation().x) < Math.abs(boxUp - getLocation().y))
				{
					_velocity.y = -_velocity.y;
				}
				else
				{
					_velocity.x = -_velocity.x;
				}
			}
			
//			if (Math.abs(_velocity.y) > Math.abs(_velocity.x))
//			{ // from up or from down
//				_velocity.y = -_velocity.y;
//			}
//			else 
//			{ // from left or from right
//				_velocity.x = -_velocity.x;
//			}
		}
	}
	
	private boolean isCornerHit(SHBrick brick)
	{
		Vector3f location = new Vector3f(
				Math.abs(getLocation().x - brick.getLocation().x), 
				Math.abs(getLocation().y - brick.getLocation().y), 0);
		BoundingBox bound = (BoundingBox)brick.getModel().getWorldBound();
		location.subtractLocal(bound.xExtent, bound.yExtent, 0);
		
		return Math.abs(location.x - location.y) < 0.001f;
	}
	
	private boolean movesToBrickCenter(SHBrick brick)
	{
		Vector3f newPosition = getLocation().add(_velocity.mult(0.001f));
		return newPosition.distance(brick.getLocation()) < 
				getLocation().distance(brick.getLocation());
	}
	
	@Override
	public SHBall clone()
	{
		Spatial model = getModel();
		Spatial newModel = getModel();
		if (model instanceof TriMesh)
		{
			newModel = new SharedMesh("ball" + numSharedObjects++, 
					(TriMesh)model);
		}
		else if (model instanceof Node)
		{
			newModel = new SharedNode("ball" + numSharedObjects++, 
					(Node)model);
		}
		
		newModel.setLocalTranslation(model.getLocalTranslation().clone());
		newModel.setModelBound(model.getWorldBound().clone(null));
		newModel.updateModelBound();
			
		return new SHBall(newModel, _velocity.clone());
	}
	
}
