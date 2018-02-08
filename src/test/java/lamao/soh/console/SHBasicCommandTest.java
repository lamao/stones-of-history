/* 
 * SHBaseCommand.java Jun 3, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import lamao.soh.core.SHUtils;
import lamao.soh.utils.events.SHEvent;


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
		public String message = null;
		
		@Override
		protected void processCommand(String[] args)
		{
			this.args = args;
			processed = true;
		}
		
		@Override
		protected void error(String message)
		{
			this.message = message;
		}
		
		@Override
		protected void info(String message)
		{
			this.message = message;
		}
	}
	
	private SHDummyCommand command = null;
	
	@BeforeMethod
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
		assertNotNull(command.message);
		
		command.message = null;
		command.processed = false;
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {})));
		assertEquals(0, command.args.length);
		assertTrue(command.processed);
		assertNull(command.message);
		
		command.message = null;
		command.processed = false;
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"ash"})));
		assertEquals(1, command.args.length);
		assertTrue(command.processed);
		assertNull(command.message);
	}
	
	@Test
	public void testRangeProcessing()
	{
		command.message = null;
		command.processed = false;
		command.setMinNumArgs(2);
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name"})));
		assertFalse(command.processed);
		assertNotNull(command.message);
		
		command.message = null;
		command.processed = false;
		command.setMinNumArgs(1);
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name", "arg1"})));
		assertTrue(command.processed);
		assertNull(command.message);
		
		command.message = null;
		command.processed = false;
		command.setMaxNumArgs(2);
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name", "arg1", "arg2", "arg3"})));
		assertFalse(command.processed);
		assertNotNull(command.message);
		
		command.message = null;
		command.processed = false;
		command.setMaxNumArgs(2);
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name", "arg1", "arg2"})));
		assertTrue(command.processed);
		assertNull(command.message);
	}
	
	@Test
	public void testHelpMessage()
	{
		command.message = null;
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name", "?"})));
		assertNotNull(command.message);
		
		command.message = null;
		command.processEvent(new SHEvent("", null, SHUtils.buildEventMap(
				"args", new String[] {"name", "?", "asdg", "asdg"})));
		assertNotNull(command.message);
	}

}
