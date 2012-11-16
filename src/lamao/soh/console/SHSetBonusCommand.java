/* 
 * SHSetBonusCommand.java 27.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */

package lamao.soh.console;

import lamao.soh.core.ISHEntityFactory;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.entities.SHBrick;

/**
 * Adds bonus to brick 
 * @author lamao
 *
 */
public class SHSetBonusCommand extends SHBasicCommand
{
	private SHScene scene;
	private ISHEntityFactory entityFactory;
	
	public SHSetBonusCommand(SHScene scene, ISHEntityFactory entityFactory)
	{
		super(2, 2);
		this.scene = scene;
		this.entityFactory = entityFactory;
	}
	
	@Override
	public void processCommand(String[] args)
	{
		String brickName = args[1];
		String bonusName = args[2];
		SHBonus bonus = (SHBonus) entityFactory.createEntity(SHUtils.buildMap(
				"type bonus|name " + bonusName));
		if (bonus == null)
		{
			warning("Can't create bonus <" + args[2] + ">");
		}
		else 
		{
			//TODO: Change hardcoded 'brick' type to constant from brick class.
			SHBrick brick = (SHBrick) scene.getEntity("brick", brickName);
			if (brick == null)
			{
				warning("Can't find brick <" + args[1] + ">");
			}
			else
			{
				brick.setBonus(bonus);
			}
		}

	}
	
	@Override
	protected String getHelpMessage()
	{
		return "Arguments: <bonus name> <brick name>";
	}
}
