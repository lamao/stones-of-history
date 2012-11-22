/* 
 * SHPlayerInfo.java 30.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;

import java.util.HashSet;
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
	
	/** List of all available epochs */
	@XStreamAlias("completed-levels")
	private Set<String> completedLevels = new HashSet<String>();

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public Set<String> getCompletedLevels()
	{
		return completedLevels;
	}

	public void setCompletedLevels(Set<String> completedLevels)
	{
		this.completedLevels = completedLevels;
	}

	@Override
	public String toString()
	{
		return _name;
	}
}
