/* 
 * SHBall.java 23.10.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.nio.FloatBuffer;

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
		
		CollisionResults results = new TriangleCollisionResults();
		getModel().findCollisions(brick.getModel(), results);
		if (results.getNumber() > 0 && 
			results.getCollisionData(0).getTargetTris().size() > 0)
		{
			CollisionData data = results.getCollisionData(0);
			TriMesh target = (TriMesh)data.getTargetMesh();
			
			Vector3f totalNormal = new Vector3f();
			Vector3f normal = new Vector3f();
			FloatBuffer normals = target.getNormalBuffer();
			int[] indexBuffer = new int[3];
			int index = 0;
			for (int i = 0; i < data.getTargetTris().size(); i++)
			{
				index = data.getTargetTris().get(i);
				target.getTriangle(index, indexBuffer);
				BufferUtils.populateFromBuffer(normal, normals, indexBuffer[0]);
				totalNormal.addLocal(normal);
				BufferUtils.populateFromBuffer(normal, normals, indexBuffer[1]);
				totalNormal.addLocal(normal);
				BufferUtils.populateFromBuffer(normal, normals, indexBuffer[2]);
				totalNormal.addLocal(normal);
			}
			
			totalNormal.divideLocal(data.getSourceTris().size() * 3);
			totalNormal.z = 0;
			
			
			float velocityAngle = SHUtils.angle(_velocity.mult(-1));
			float normalAngle = SHUtils.angle(totalNormal);
			float resultAngle = velocityAngle + 2 * (normalAngle - velocityAngle);
			float speed = _velocity.length();
			_velocity.x = FastMath.cos(resultAngle) * speed;
			_velocity.y = FastMath.sin(resultAngle) * speed;
			
			
		}
		
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
