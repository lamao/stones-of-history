/* 
 * SHBallBrickCollisionHandler.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import java.nio.FloatBuffer;

import com.jme.intersection.CollisionData;
import com.jme.intersection.CollisionResults;
import com.jme.intersection.TriangleCollisionResults;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jme.util.geom.BufferUtils;

import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.bonuses.SHDefaultMover;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBallBrickCollisionHandler extends SHAbstractCollisiontHandler
{
	public SHBallBrickCollisionHandler(SHEventDispatcher dispatcher,
			SHScene scene)
	{
		super(dispatcher, scene);
	}

	@Override
	public void processEvent(SHEvent event)
	{
		SHBrick brick = event.getParameter("dst", SHBrick.class);
		SHBall ball = event.getParameter("src", SHBall.class);
		
		dispatcher.addEventEx("level-brick-hit", this, "brick", brick);
		onHit(ball, brick);
		if (brick.getStrength() <= 0)
		{
			scene.remove(brick);
			dispatcher.addEventEx("level-brick-deleted", this, "brick", brick);
			
			SHBonus bonus = brick.getBonus();
			if (bonus != null)
			{
				bonus.addController(new SHDefaultMover(bonus));
				Vector3f bonusLocation = brick.getLocation().clone();
				bonusLocation.y = 0;
				bonus.setLocation(bonusLocation);
				bonus.updateGeometricState(0, true);
				scene.add(bonus.getType(), bonus);
				bonus.updateGeometricState(0, true);
				dispatcher.addEventEx("level-bonus-extracted", this, 
						"bonus", bonus);
			}
		}
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
	public void onHit(SHBall ball, SHBrick brick)
	{
		if (ball.isSuper())
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
		ball.findCollisions(brick, results);
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
			totalNormal.y = 0;
			
			
			Vector3f ballVelocity = ball.getVelocity();
			float velocityAngle = SHUtils.angle(ballVelocity.mult(-1));
			float normalAngle = SHUtils.angle(totalNormal);
			float resultAngle = velocityAngle + 2 * (normalAngle - velocityAngle);
			float speed = ballVelocity.length();
			ballVelocity.x = FastMath.cos(resultAngle) * speed;
			ballVelocity.z = -FastMath.sin(resultAngle) * speed;
		}
		
	}
	
	/**
	 * Computes normal for given triangle.
	 */
	private Vector3f computeTriNormal(Vector3f v1, Vector3f v2, Vector3f v3)
	{
		
		Vector3f result = v1.subtractLocal(v2);
		result = v1.cross(v3.subtract(v2));
		result.normalizeLocal();//.multLocal(-1);
		return result;
	}

}
