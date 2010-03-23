/* 
 * SHUtils.java 23.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.math.Vector3f;

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
}
