/** 
 * Pair.java 23.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model;

import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;

/**
 * Pair of epoch and level
 * @author lamao
 *
 */
public class SHEpochLevelItem
{
	private SHEpoch epoch;
	
	private SHLevel level;

	public SHEpochLevelItem()
	{
	}

	public SHEpochLevelItem(SHEpoch epoch, SHLevel level)
	{
		this.epoch = epoch;
		this.level = level;
	}

	public SHEpoch getEpoch()
	{
		return epoch;
	}

	public void setEpoch(SHEpoch epoch)
	{
		this.epoch = epoch;
	}

	public SHLevel getLevel()
	{
		return level;
	}

	public void setLevel(SHLevel level)
	{
		this.level = level;
	}

}
