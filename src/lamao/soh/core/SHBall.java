/* 
 * SHBall.java 23.10.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;

/**
 * Ball entity.
 * @author lamao
 *
 */
public class SHBall extends SHEntity
{
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
		if (brick.isGlass())
			return;
		
		BoundingBox box = (BoundingBox)brick.getModel().getWorldBound();
		
		// if ball hits up or bottom side change velocity.y component
		// if ball hits left or right side change velocity.x component
		// else ball hits in corner repulse it back
		if (SHUtils.inRange(getModel().getLocalTranslation().x, 
							box.getCenter().x - box.xExtent,
							box.getCenter().x + box.xExtent))
		{
			_velocity.y = -_velocity.y;
		}
		else if (SHUtils.inRange(getModel().getLocalTranslation().y, 
							box.getCenter().y - box.yExtent,
							box.getCenter().y + box.yExtent))
		{
			_velocity.x = -_velocity.x;
		}
		else 
		{
			_velocity.multLocal(-1);
		}
			
	}
	
}
