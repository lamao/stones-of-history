/* 
 * SHBall.java 23.10.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.entities;

import lamao.soh.core.SHEntity;

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
@SuppressWarnings("serial")
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
	
	/** Create ball with velocity = (0, 0, -1) */
	public SHBall(Spatial model)
	{
		this(model, new Vector3f(0, 0, -1));
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
