/* 
 * SHDoubleBonusTest.java 29.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import java.util.ArrayList;
import java.util.List;

import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBall;

import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jme.scene.Node;
import com.jme.scene.Spatial;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author lamao
 *
 */
public class SHDoubleBallBonusTest
{
	@Mock
	private SHScene scene;
	
	private SHDoubleBallBonus bonus = new SHDoubleBallBonus();
	
	@BeforeMethod
	public void setUp()
	{
		initMocks(this);
	}
	
	@Test
	public void testDuration()
	{
		assertTrue(Math.abs(bonus.getDuration() - SHDoubleBallBonus.DURATION) < 
				0.001f);
	}
	
	@Test
	public void testBonusApply()
	{
		List<Spatial> balls = new ArrayList<Spatial>();
		SHBall ball = SHEntityCreator.createDefaultBall();
		ball.setVelocity(-1, 0, -2);
		balls.add(SHEntityCreator.createDefaultBall());
		balls.add(ball);
		when(scene.get("ball")).thenReturn(balls);
		doAnswer(new AddToListAnswer(balls)).when(scene).add(any(SHBall.class));
		
		// double balls
		bonus.apply(scene);
		verify(scene, times(2)).add(any(SHBall.class));
		assertEquals(1, balls.get(2).getControllerCount());
		assertEquals(1, balls.get(3).getControllerCount());
		float angle = SHUtils.angle(((SHBall)balls.get(0)).getVelocity()) - 
				SHUtils.angle(((SHBall)balls.get(2)).getVelocity());
		assertTrue(Math.abs(angle) - Math.PI / 4 < 0.001f);
		angle = SHUtils.angle(((SHBall)balls.get(1)).getVelocity()) - 
				SHUtils.angle(((SHBall)balls.get(3)).getVelocity());
		assertTrue(Math.abs(angle) - Math.PI / 4 < 0.001f, Float.toString(angle));
	}
	
	@Test
	public void testBonusCleanup()
	{
		List<Spatial> list = new ArrayList<Spatial>();
		SHBall ball = SHEntityCreator.createDefaultBall();
		ball.setVelocity(-1, 2, 0);
		list.add(SHEntityCreator.createDefaultBall());
		list.add(ball);
		list.add(SHEntityCreator.createDefaultBall());
		when(scene.get("ball")).thenReturn(list);
		
		Node rootNode = mock(Node.class);
		when(scene.getRootNode()).thenReturn(rootNode);
		
		bonus.cleanup(scene);
		verify(scene, times(1)).remove(eq("ball"), any(SHBall.class));
		verify(rootNode).updateRenderState();
	}
	
	private class AddToListAnswer implements Answer<Object>
	{
		
		private List<Spatial> list;
		
		public AddToListAnswer(List<Spatial> list)
		{
			super();
			this.list = list;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object answer(InvocationOnMock invocation) throws Throwable
		{
			Object[] args = invocation.getArguments();
			Spatial ball = (Spatial)args[0];
			list.add(ball);
		    return null;
		}
	}
}
