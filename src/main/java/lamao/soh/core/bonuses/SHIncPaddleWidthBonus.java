/* 
 * SHIncPaddleWidthBonus.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import com.jme3.scene.Spatial;

import lamao.soh.core.SHScene;

/**
 * Increases paddle width by 10%.
 * @author lamao
 *
 */
@SuppressWarnings("serial")
public class SHIncPaddleWidthBonus extends SHBonus
{
	public final static float INCREASE_PERCENT = 0.2f;
	public final static float DURATION = 5;

	
	public SHIncPaddleWidthBonus(Spatial model)
	{
		super(model);
		setDuration(DURATION);
	}
	
	public SHIncPaddleWidthBonus()
	{
		this(null);
	}
	
	@Override
	public void apply(SHScene scene)
	{
		Spatial paddle = scene.getEntity("paddle", "paddle").getModel();
		paddle.getLocalScale().x *= (1 + INCREASE_PERCENT);
		paddle.updateModelBound();
	}

	@Override
	public void cleanup(SHScene scene)
	{
		Spatial paddle = scene.getEntity("paddle", "paddle").getModel();
		paddle.getLocalScale().x /= (1 + INCREASE_PERCENT);
		paddle.updateModelBound();
	}
	
	

}
