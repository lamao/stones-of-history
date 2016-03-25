/* 
 * SHBreakoutEntityFactory.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.Map;

import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.entities.SHBottomWall;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.utils.SHResourceManager;

import com.jme.scene.Spatial;

/**
 * Entity factory for stones of history.
 * @author lamao
 *
 */
public class SHBreakoutEntityFactory implements ISHEntityFactory
{
	private SHResourceManager manager;
	
	public SHBreakoutEntityFactory(SHResourceManager manager)
	{
		super();
		this.manager = manager;
	}

	@Override
	public SHEntity createEntity(Map<String, String> metadata)
	{
		if (metadata == null)
		{
			return null;
		}
		String type = metadata.get("type");
		
		SHEntity entity = null;
		if (type != null)
		{
			if (type.equals("brick"))
			{
				SHBrick brick = new SHBrick();
				String value = metadata.get("strength");
				if (value != null)
				{
					if (value.equals("super"))
					{
						brick.setStrength(Integer.MAX_VALUE);
					}
					else 
					{
						brick.setStrength(Integer.parseInt(value));
					}
				}
				value = metadata.get("glass");
				if (value != null)
				{
					brick.setGlass(Boolean.parseBoolean(value));
				}
				
				value = metadata.get("bonus");
				if (value != null)
				{
					SHBonus bonus = (SHBonus)createEntity(SHUtils.buildMap(
							"type bonus|name " + value));
					brick.setBonus(bonus);
				}
				
				entity = brick;
			}
			else if (type.equals("bottom-wall"))
			{
				entity = new SHBottomWall();
			}
			else if (type.equals("wall"))
			{
				entity = new SHEntity();
			}
			else if (type.equals("bonus"))
			{
				try
				{
					String bonusName = metadata.get("name");
					String className = SHUtils.getClassName(
							"lamao.soh.core.bonuses.SH", bonusName, "Bonus"); 
					Class<?> klass = Class.forName(className);
					entity = (SHBonus)klass.newInstance();
					Spatial model = (Spatial)manager
						.get(SHResourceManager.TYPE_MODEL, bonusName);
					entity.setModel(SHUtils.createSharedModel(entity + "bonus", 
							model));
				}
				catch (ClassNotFoundException e)
				{
				}
				catch (InstantiationException e)
				{
				}
				catch (IllegalAccessException e)
				{
				}
			}
			if (entity != null)
			{
				entity.setType(type);
			}
		}
		return entity;
	}

}
