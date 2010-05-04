/* 
 * SHBreakoutEntityFactory.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.Map;

/**
 * @author lamao
 *
 */
public class SHBreakoutEntityFactory implements ISHEntityFactory
{
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
			if (entity != null)
			{
				entity.setType(type);
			}
		}
		return entity;
	}

}
