/* 
 * SHLevelInfo.java 30.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Class that contains information about one level. 
 * @author lamao
 *
 */

@XStreamAlias("level")
public class SHLevel
{
	/** Unique (within epoch) ID of this level */ 
	@XStreamAlias("id")
	private String id;
	
	/** Level contents (bricks, walls etc) */
	@XStreamAlias("scene")
	private String _scene;
	
	/** Level introduction - historical overview */
	@XStreamAlias("intro")
	private String _intro;
	
	/** Name of the displayed level name */
	@XStreamAlias("name")
	private String _name;

	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getScene()
	{
		return _scene;
	}

	public void setScene(String scene)
	{
		_scene = scene;
	}

	public String getIntro()
	{
		return _intro;
	}

	public void setIntro(String intro)
	{
		_intro = intro;
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
