/* 
 * SHSetBonusCommand.java 27.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */

package lamao.soh.console;

import lamao.soh.core.SHBreakoutEntityFactory;
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
	
	public SHSetBonusCommand(SHScene scene)
	{
		super(2, 2);
		this.scene = scene;
	}
	
	@Override
	public void processCommand(String[] args)
	{
		String brickName = args[1];
		String bonusName = args[2];
		SHBonus bonus = (SHBonus) new SHBreakoutEntityFactory().createEntity(SHUtils.buildMap(
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
