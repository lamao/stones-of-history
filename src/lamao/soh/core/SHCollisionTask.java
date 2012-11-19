/* 
 * SHCollisionTask.java 30.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

/**
 * Defines collision task for scene.
 * @author lamao
 *
 */
public class SHCollisionTask
{
	/** Type of source entity */
	private String sourceType = null;
	
	/** Type of destination entity */
	private String destType = null;
	
	/** Bounding or triangle collision detection */
	private boolean checkTris = false;

	public SHCollisionTask(String sourceType, String destType, boolean checkTris)
	{
		this.checkTris = checkTris;
		this.destType = destType;
		this.sourceType = sourceType;
	}
	
	public String getSourceType()
	{
		return sourceType;
	}

	public void setSourceType(String sourceType)
	{
		this.sourceType = sourceType;
	}

	public String getDestType()
	{
		return destType;
	}

	public void setDestType(String destType)
	{
		this.destType = destType;
	}

	public boolean isCheckTris()
	{
		return checkTris;
	}

	public void setCheckTris(boolean checkTris)
	{
		this.checkTris = checkTris;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if (obj == this)
		{
			result = true;
		}
		else if (obj instanceof SHCollisionTask)
		{
			SHCollisionTask task = (SHCollisionTask)obj;
			result = sourceType != null && sourceType.equals(task.sourceType) && 
					destType != null && destType.equals(task.destType);
		}
		return result;
	}
	
	@Override
	public String toString()
	{
		return "<" + sourceType + ", " + destType + ", " + checkTris + ">"; 
	}
}
