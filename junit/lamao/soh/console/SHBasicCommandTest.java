/* 
 * SHBaseCommand.java Jun 3, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import static org.junit.Assert.*;
import lamao.soh.core.SHUtils;
import lamao.soh.utils.events.SHEvent;

import org.junit.Before;
import org.junit.Test;

/**
 * @author lamao
 *
 */
public class SHBasicCommandTest
{
	
	class SHDummyCommand extends SHBasicCommand
	{
		public String[] args;
		public boolean processed = false;
		
		@Override
		protected void processCommand(String[] args)
		{
			this.args = args;
			processed = true;
		}
	}
	
	private SHDummyCommand command = null;
	
	@Before
	public void setUp()
	{
		command = new SHDummyCommand();
	}
	
	@Test
	public void testConstructors()
	{
		assertEquals(-1, command.getMinNumArgs());
		assertEquals(Integer.MAX_VALUE, command.getMaxNumArgs());
	}
	
	@Test
	public void testBasicProcess()
	{
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", null)));
		assertNull(command.args);
		assertFalse(command.processed);
		
		
		command.processed = false;
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {})));
		assertEquals(0, command.args.length);
		assertTrue(command.processed);
		
		command.processed = false;
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"ash"})));
		assertEquals(1, command.args.length);
		assertTrue(command.processed);
	}
	
	@Test
	public void testRangeProcessing()
	{
		command.processed = false;
		command.setMinNumArgs(2);
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name"})));
		assertFalse(command.processed);
		
		command.processed = false;
		command.setMinNumArgs(1);
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name", "arg1"})));
		assertTrue(command.processed);
		
		command.processed = false;
		command.setMaxNumArgs(2);
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name", "arg1", "arg2", "arg3"})));
		assertFalse(command.processed);
		
		command.processed = false;
		command.setMaxNumArgs(2);
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name", "arg1", "arg2"})));
		assertTrue(command.processed);
	}

}
