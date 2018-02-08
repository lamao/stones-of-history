/* 
 * SHConsoleTest.java Jun 4, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;


import java.util.HashMap;
import java.util.Map;

import lamao.soh.ngutils.AbstractJmeTest;
import lamao.soh.utils.events.ISHEventHandler;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.jme3.input.KeyInput;

/**
 * @author lamao
 *
 */
@Test(enabled = false)
public class SHConsoleStateTest extends AbstractJmeTest
{
	class SHDummyCommand extends SHBasicCommand
	{
		public String[] command;
		
		@Override
		protected void processCommand(String[] args)
		{
			command = args;
		}
	}
	
	private SHDummyCommand command = new SHDummyCommand();
	private SHConsoleState console;
	
	@BeforeMethod
	public void setUpTest()
	{
		console = new SHConsoleState(null);
	}

	
	@Test(enabled = false)
	public void testConstructors()
	{
		assertEquals(SHConsoleState.PROMT, console.getContents()[0]);
		assertTrue(console.getSupportedCommands().contains("exit"));
		assertTrue(console.getSupportedCommands().contains("echo"));
	}
	
	@Test(enabled = false)
	public void testDefaultConstructor()
	{
		SHConsoleState console = new SHConsoleState(null);
//		assertEquals(console.getName(), SHConsoleState.STATE_NAME);
		assertTrue(console.getSupportedCommands().contains("exit"));
		assertTrue(console.getSupportedCommands().contains("echo"));
	}
	
	@Test(enabled = false)
	public void testAddRemoveCommand()
	{
		console.add("a", command);
		assertEquals(3, console.getSupportedCommands().size());
		assertTrue(console.getSupportedCommands().contains("a"));
	}
	
	@Test(enabled = false)
	public void testExitCommand()
	{
		console.setEnabled(true);
		
		console.execute("exit");
		assertFalse(console.isEnabled());
	}
	
	@Test(enabled = false)
	public void testExecute()
	{
		console.execute("non-existent-command");
		assertEquals(SHConsoleState.PROMT, console.getContents()[0]);
		assertFalse(console.getContents()[1].isEmpty());
		assertTrue(console.getContents()[2].isEmpty());
		
		console.add("a", command);
		console.execute("a arg1 arg2");
		assertEquals("a", command.command[0]);
		assertEquals("arg1", command.command[1]);
		assertEquals("arg2", command.command[2]);
		
		command.command = null;
		console.add("aaaa", command);
		console.execute("aaaa arg1 arg2");
		assertEquals("aaaa", command.command[0]);
		assertEquals("arg1", command.command[1]);
		assertEquals("arg2", command.command[2]);
	}
	
	@Test(enabled = false)
	public void testPrint()
	{
		String INFO = "info";
		String WARNING = "warning";
		String ERROR = "error";
		String MESSAGE = "message";
		
		console.info(INFO);
		assertEquals(SHConsoleState.PROMT, console.getContents()[0]);
		assertEquals(INFO, console.getContents()[1]);
		
		console.warning(WARNING);
		assertEquals(SHConsoleState.PROMT, console.getContents()[0]);
		assertEquals(WARNING, console.getContents()[1]);
		assertEquals(INFO, console.getContents()[2]);
		
		console.error(ERROR);
		assertEquals(SHConsoleState.PROMT, console.getContents()[0]);
		assertEquals(ERROR, console.getContents()[1]);
		assertEquals(WARNING, console.getContents()[2]);
		assertEquals(INFO, console.getContents()[3]);
		
		console.print(MESSAGE, SHConsoleState.DEFAULT_COLOR);
		assertEquals(SHConsoleState.PROMT, console.getContents()[0]);
		assertEquals(MESSAGE, console.getContents()[1]);
		assertEquals(ERROR, console.getContents()[2]);
		assertEquals(WARNING, console.getContents()[3]);
		assertEquals(INFO, console.getContents()[4]);
		
		for (int i = 0; i < 8; i++)
		{
			console.info("a");
		}		
		String[] contents = console.getContents();
		for (int i = 1; i < 9; i++)
		{
			assertEquals("a", contents[i]);
		}
		assertEquals(MESSAGE, contents[9]);
	}
	
//	@Test(enabled = false)
//	public void testSetActive()
//	{
//		console.onKey('a', KeyInput.KEY_A, true);
//		assertEquals(SHConsoleState.PROMT, console.getContents()[0]);
//
//		console.setActive(true);
//		console.onKey('a', KeyInput.KEY_A, true);
//		assertEquals(SHConsoleState.PROMT + "a", console.getContents()[0]);
//
//		console.setActive(false);
//		assertEquals(SHConsoleState.PROMT + "a", console.getContents()[0]);
//
//		console.setActive(true);
//		assertEquals(SHConsoleState.PROMT, console.getContents()[0]);
//	}
	
//	@Test(enabled = false)
//	public void testWalkingThroughHistory()
//	{
//		console.setActive(true);
//		console.onKey('a', KeyInput.KEY_UP, true);
//		console.onKey('a', KeyInput.KEY_DOWN, true);
//	}
	
	@Test(enabled = false)
	public void testSetCommands()
	{
		Map<String, ISHEventHandler> commands = new HashMap<String, ISHEventHandler>();
		commands.put("cmd1", Mockito.mock(ISHEventHandler.class));
		commands.put("cmd2", Mockito.mock(ISHEventHandler.class));
		
		console.setCommands(commands);
		
		assertEquals(console.getSupportedCommands().size(), 4);
	}
	

}
