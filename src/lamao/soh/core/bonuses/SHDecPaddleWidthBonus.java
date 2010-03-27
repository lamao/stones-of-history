/* 
 * SHDecPaddleWidthBonus.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHLevel;

import com.jme.scene.Spatial;

/**
 * Decreases paddle width by 10%
 * @author lamao
 *
 */
public class SHDecPaddleWidthBonus extends SHBonus
{
	public final static float DECREASE_PERCENT = 0.2f;
	public final static float DURATION = 5;

	
	public SHDecPaddleWidthBonus(Spatial model)
	{
		super(model);
		setDuration(DURATION);
	}
	
	public SHDecPaddleWidthBonus()
	{
		this(null);
	}
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#apply(lamao.soh.core.SHLevel)
	 */
	@Override
	public void apply(SHLevel level)
	{
		Spatial paddle = level.getPaddle().getModel();
		paddle.getLocalScale().x *= (1 - DECREASE_PERCENT);
		paddle.updateModelBound();
	}

	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#cleanup(lamao.soh.core.SHLevel)
	 */
	@Override
	public void cleanup(SHLevel level)
	{
		Spatial paddle = level.getPaddle().getModel();
		paddle.getLocalScale().x /= (1 - DECREASE_PERCENT);
		paddle.updateModelBound();
	}
}
