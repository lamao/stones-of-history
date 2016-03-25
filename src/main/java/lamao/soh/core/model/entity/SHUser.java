/* 
 * SHPlayerInfo.java 30.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Player info
 * @author lamao
 *
 */
@XStreamAlias("player")
public class SHUser
{
	/** Player name */
	@XStreamAlias("name")
	private String _name;
	
	/** 
	 * Information about completed levels where key is epoch ID and string
	 * set is set of ID of completed levels in this epoch 
	 */
	@XStreamAlias("completed-levels")
	private Map<String, Set<String>> completedLevels = new HashMap<String, Set<String>>();

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public Map<String, Set<String>> getCompletedLevels()
	{
		return completedLevels;
	}

	public void setCompletedLevels(Map<String, Set<String>> completedLevels)
	{
		this.completedLevels = completedLevels;
	}
	
	/**
	 * Mark level as completed
	 * @param epochId ID of parent epoch containing this level
	 * @param levelId ID of level to mark
	 */
	public void addCompletedLevel(String epochId, String levelId) {
		if (completedLevels.get(epochId) == null) {
			completedLevels.put(epochId, new HashSet<String>());
		}
		completedLevels.get(epochId).add(levelId);
	}

	@Override
	public String toString()
	{
		return _name;
	}
}
