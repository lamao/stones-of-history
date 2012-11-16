/* 
 * SHEpochInfo.java 30.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;

import java.util.ArrayList;
import java.util.List;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Class that contains information about single epoch
 * @author lamao
 *
 */
@XStreamAlias("epoch")
public class SHEpoch
{
	/** Displayed name of the epoch */
	@XStreamAlias("name")
	private String _name;
	
	/** Minimal year of the level in epoch */
	@XStreamOmitField
	private float _minYear = Float.MAX_VALUE;
	
	/** Maximum year of the level in epoch */
	@XStreamOmitField
	private float _maxYear = Float.MIN_VALUE;
	
	/** List of levels in epoch */
	@XStreamAlias("levels")
	private List<SHLevel> _levels = new ArrayList<SHLevel>();

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public float getMinYear()
	{
		return _minYear;
	}

	public void setMinYear(float minYear)
	{
		_minYear = minYear;
	}

	public float getMaxYear()
	{
		return _maxYear;
	}

	public void setMaxYear(float maxYear)
	{
		_maxYear = maxYear;
	}

	public List<SHLevel> getLevels()
	{
		return _levels;
	}

	public void setLevels(List<SHLevel> levels)
	{
		_levels = levels;
	}
	
}
