/* 
 * SHUtils.java 23.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.HashMap;
import java.util.Map;

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
	/**
	 * Checks if two vectors are equal with specified accuracy.
	 */
	public static boolean areEqual(Vector3f v1, Vector3f v2, float accuracy)
	{
		return Math.abs(v1.x - v2.x) < accuracy &&
			   Math.abs(v1.y - v2.y) < accuracy &&
			   Math.abs(v1.z - v2.z) < accuracy;
	}
	
	/**
	 * Checks if <code>value</code> is in rang p1..p2
	 */
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
	
	/**
	 * Converts string name of format 'first-second-other' to class name of 
	 * format <code>[prefix]FirstSecondOther[suffix]</code>
	 * @param prefix - prefix, typically packet name and begin of class name
	 * @param name - name, typically class name
	 * @param suffix - suffix, typically end of class name 
	 */ 
	public static String getClassName(String prefix, String name, String suffix)
	{
		StringBuffer buffer = new StringBuffer(name);
		buffer.setCharAt(0, Character.toUpperCase(buffer.charAt(0)));
	
		for (int i = 0; i < buffer.length(); i++)
		{
			if (buffer.charAt(i) == '-')
			{
				buffer.deleteCharAt(i);
				buffer.setCharAt(i, Character.toUpperCase(buffer.charAt(i)));
				i--;
			}
		}
		buffer.insert(0, prefix);
		buffer.append(suffix);
		return buffer.toString();
	}
	
	/**
	 * Builds map from string 'key value|key value|...'
	 * @param data
	 * @return
	 */
	public static Map<String, String> buildMap(String data)
	{
		String items[] = data.split("[\\s|]");
		Map<String, String> result = null;
		if (items.length > 1)
		{
			result = new HashMap<String, String>();
			for (int i = 0; i < items.length; i += 2)
			{
				result.put(items[i], items[i + 1]);
			}
		}
		return result;
	}
	
	/**
	 * Builds map from array. The size of array must be 2 * n and 
	 * 0, 2, 4, ..., 2 * i items must be strings.<br>
	 * Designed to be used in event manager.
	 * @param data - array key-vaule pairs.
	 * @return map
	 */
	public static Map<String, Object> buildEventMap(Object... data)
	{
		Map<String, Object> parameters = null;
		if (data != null && data.length % 2 == 0)
		{
			parameters = new HashMap<String, Object>();
			for (int i = 0; i < data.length; i += 2)
			{
				parameters.put((String)data[i], data[i + 1]);
			}
		}
		return parameters;
	}
	
}
