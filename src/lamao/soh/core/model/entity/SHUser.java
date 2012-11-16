/* 
 * SHPlayerInfo.java 30.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Player info
 * @author lamao
 *
 */
@XStreamAlias("player")
public class SHUser
{
	/** Lives remains */
	@XStreamAlias("lives")
	private int _lives;
	
	/** Player name */
	@XStreamAlias("name")
	private String _name;
	
	/** Currently played epoch */
	@XStreamAlias("current-epoch")
	private SHEpoch _currentEpoch;
	
	/** List of all available epochs */
	@XStreamAlias("epochs")
	private List<SHEpoch> _epochs = new ArrayList<SHEpoch>();

	public int getLives()
	{
		return _lives;
	}

	public void setLives(int lives)
	{
		_lives = lives;
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public SHEpoch getCurrentEpoch()
	{
		return _currentEpoch;
	}

	public void setCurrentEpoch(SHEpoch currentEpoch)
	{
		_currentEpoch = currentEpoch;
	}

	public List<SHEpoch> getEpochs()
	{
		return _epochs;
	}

	public void setEpochs(List<SHEpoch> epochs)
	{
		_epochs = epochs;
	}
	
	
	@Override
	public String toString()
	{
		return _name;
	}
}
