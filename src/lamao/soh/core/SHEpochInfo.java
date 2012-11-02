/* 
 * SHEpochInfo.java 30.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Class that contains information about single epoch
 * @author lamao
 *
 */
@XStreamAlias("epoch")
public class SHEpochInfo
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
	private List<SHLevelInfo> _levels = new ArrayList<SHLevelInfo>();

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

	public List<SHLevelInfo> getLevels()
	{
		return _levels;
	}

	public void setLevels(List<SHLevelInfo> levels)
	{
		_levels = levels;
	}
	
	/**
	 * Calculates min year and max year from its level's years
	 */
	public void updateYears()
	{
		_minYear = Float.MAX_VALUE;
		_maxYear = Float.MIN_VALUE;
		for (SHLevelInfo level : getLevels())
		{
			if (level.getYear() < _minYear)
			{
				_minYear = level.getYear();
			}
			if (level.getYear() > _maxYear)
			{
				_maxYear = level.getYear();
			}
		}
		
	}
	
	/**
	 * Sort levels according to their level
	 */
	public void sortLevels()
	{
		Collections.sort(_levels, new Comparator<SHLevelInfo>() {
			@Override
			public int compare(SHLevelInfo o1, SHLevelInfo o2)
			{
				return (o1.getYear() < o2.getYear()) ? -1 : 1;
			}
		});
	}
	
}
