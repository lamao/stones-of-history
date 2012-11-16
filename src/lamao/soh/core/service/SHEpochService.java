/** 
 * SHEpochServie.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;

/**
 * @author lamao
 *
 */
public class SHEpochService
{
	/**
	 * Sort levels according to their level
	 */
	public void sortLevels(SHEpoch epoch)
	{
		List<SHLevel> levels = epoch.getLevels();
		Collections.sort(levels, new Comparator<SHLevel>() {
			@Override
			public int compare(SHLevel o1, SHLevel o2)
			{
				return (o1.getYear() < o2.getYear()) ? -1 : 1;
			}
		});
		
		epoch.setLevels(levels);
	}
	
	/**
	 * Calculates min year and max year from its level's years
	 */
	public void updateYears(SHEpoch epoch)
	{
		float minYear = Float.MAX_VALUE;
		float maxYear = Float.MIN_VALUE;
		for (SHLevel level : epoch.getLevels())
		{
			if (level.getYear() < minYear)
			{
				minYear = level.getYear();
			}
			if (level.getYear() > maxYear)
			{
				maxYear = level.getYear();
			}
		}
		
		epoch.setMinYear(minYear);
		epoch.setMaxYear(maxYear);
		
	}
	

}
