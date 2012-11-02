/* 
 * SHLevelInfo.java 30.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Class that contains information about one level. 
 * @author lamao
 *
 */

@XStreamAlias("level")
public class SHLevelInfo
{
	/** Year of the level */
	@XStreamAlias("year")
	private float _year;
	
	/** Level contents (bricks, walls etc) */
	@XStreamAlias("models")
	private String _models;
	
	/** Level introduction - historical overview */
	@XStreamAlias("intro")
	private String _intro;
	
	/** Indicates if this level was completed */
	@XStreamAlias("completed")
	private boolean _completed = false;
	
	/** Name of the displayed level name */
	@XStreamAlias("name")
	private String _name;

	public float getYear()
	{
		return _year;
	}

	public void setYear(float year)
	{
		_year = year;
	}

	public String getModels()
	{
		return _models;
	}

	public void setModels(String models)
	{
		_models = models;
	}

	public String getIntro()
	{
		return _intro;
	}

	public void setIntro(String intro)
	{
		_intro = intro;
	}

	public boolean isCompleted()
	{
		return _completed;
	}

	public void setCompleted(boolean completed)
	{
		_completed = completed;
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

}
