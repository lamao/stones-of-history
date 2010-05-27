/* 
 * SHBall.java 23.10.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.entities;

import java.nio.FloatBuffer;

import lamao.soh.core.SHEntity;
import lamao.soh.core.SHUtils;

import com.jme.intersection.CollisionData;
import com.jme.intersection.CollisionResults;
import com.jme.intersection.TriangleCollisionResults;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.SharedMesh;
import com.jme.scene.SharedNode;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.util.geom.BufferUtils;

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
	
	/** Ball is super (e.i. it destroys all bricks which can be destroyed by
	 * on hit and does not change its direction when hit them)
	 */
	private boolean _super = false;
	
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
	
	public boolean isSuper()
	{
		return _super;
	}

	public void setSuper(boolean value)
	{
		_super = value;
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
		if (isSuper())
		{
			if (brick.getStrength() != Integer.MAX_VALUE)
			{
				brick.setStrength(0);
				return;
			}
		}
		
		brick.hit();
		if (brick.isGlass())
		{
			return;
		}
		
		
		
		CollisionResults results = new TriangleCollisionResults();
		getRoot().findCollisions(brick.getRoot(), results);
		if (results.getNumber() > 0 && 
			results.getCollisionData(0).getTargetTris().size() > 0)
		{
			CollisionData data = results.getCollisionData(0);
			TriMesh target = (TriMesh)data.getTargetMesh();
			
			Vector3f totalNormal = new Vector3f();
			Vector3f v1 = new Vector3f();
			Vector3f v2 = new Vector3f();
			Vector3f v3 = new Vector3f();
			FloatBuffer vertices = target.getVertexBuffer();
			int[] indexBuffer = new int[3];
			int index = 0;
			for (int i = 0; i < data.getTargetTris().size(); i++)
			{
				index = data.getTargetTris().get(i);
				target.getTriangle(index, indexBuffer);
				BufferUtils.populateFromBuffer(v1, vertices, indexBuffer[0]);
				BufferUtils.populateFromBuffer(v2, vertices, indexBuffer[1]);
				BufferUtils.populateFromBuffer(v3, vertices, indexBuffer[2]);
				
				totalNormal.addLocal(computeTriNormal(v1, v2, v3));
			}
			
			totalNormal.divideLocal(data.getSourceTris().size());
			totalNormal.z = 0;
			
			
			float velocityAngle = SHUtils.angle(_velocity.mult(-1));
			float normalAngle = SHUtils.angle(totalNormal);
			float resultAngle = velocityAngle + 2 * (normalAngle - velocityAngle);
			float speed = _velocity.length();
			_velocity.x = FastMath.cos(resultAngle) * speed;
			_velocity.y = FastMath.sin(resultAngle) * speed;
			
			
		}
		
	}
	
	private Vector3f computeTriNormal(Vector3f v1, Vector3f v2, Vector3f v3)
	{
		
		Vector3f result = v1.subtractLocal(v2);
		result = v1.cross(v3.subtract(v2));
		result.normalizeLocal();//.multLocal(-1);
		return result;
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
		
		newModel.setModelBound(model.getWorldBound().clone(null));
		newModel.updateModelBound();
			
		SHBall result = new SHBall(newModel, _velocity.clone());
		result.setType(getType());
		result.setName("ball" + this);
		result.setLocation(getLocation().clone());
		
		return result;
	}
	
}
