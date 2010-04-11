/* 
 * SHUtils.java 23.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.SharedMesh;
import com.jme.scene.SharedNode;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;

/**
 * Different utility methods
 * @author lamao
 *
 */
public class SHUtils
{

	public static boolean areEqual(Vector3f v1, Vector3f v2, float accuracy)
	{
		return Math.abs(v1.x - v2.x) < accuracy &&
			   Math.abs(v1.y - v2.y) < accuracy &&
			   Math.abs(v1.z - v2.z) < accuracy;
	}
	
	public static boolean inRange(float value, float p1, float p2)
	{
		return (p1 <= value && value <= p2) || (p2 <= value && value <= p1);
	}
	
	/** 
	 * Calculates angle between vector and (1, 0, 0), using only X and Y. 
	 * Angle can be from 0 to 2 * PI.
	 */
	public static float angle(Vector3f vector)
	{
		float angle = (float)Math.acos(vector.x / Math.abs(vector.length()));
		if (vector.y < 0)
		{
			angle = (float)(2 * Math.PI - angle);
		}
		return angle;
	}
	
	/**
	 * Creates shared mesh or shared node using <code>source</code>
	 * @param name - name of shared spatial
	 * @param source - source model
	 * @return shared spatial
	 */
	public static Spatial createSharedModel(String name, Spatial source)
	{
		Spatial result = null;
		if (source instanceof TriMesh)
		{
			result = new SharedMesh(name, (TriMesh)source);
		}
		else if (source instanceof Node)
		{
			result = new SharedNode(name, (Node)source);
		}
		return result;
	}
	
}
