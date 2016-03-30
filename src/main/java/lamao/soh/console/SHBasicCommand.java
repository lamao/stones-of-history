/* 
 * SHBaseCommandTest.java Jun 3, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import com.jme3.renderer.ColorRGBA;

import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * Basic ISHEventHandler implementation to be used as game console command 
 * handler 
 * @author lamao
 *
 */
public abstract class SHBasicCommand implements ISHEventHandler
{
	/** Minimum number of arguments. NOTE: args[0] is command name */ 
	private int _minNumArgs = -1;
	
	/** Maximum number of arguments. NOTE: args[0] is command name */
	private int _maxNumArgs = Integer.MAX_VALUE;
	
	/** Link to console for internal usage */
	private SHConsoleState _console = null;
	
	/** Argument to get help message about command */
	public final static String HELP_ARG = "?";
	
	public SHBasicCommand()
	{
	}
	
	public SHBasicCommand(int minNumArgs, int maxNumArgs)
	{
		_minNumArgs = minNumArgs;
		_maxNumArgs = maxNumArgs;
	}

	public int getMinNumArgs()
	{
		return _minNumArgs;
	}

	public void setMinNumArgs(int minNumArgs)
	{
		_minNumArgs = minNumArgs;
	}

	public int getMaxNumArgs()
	{
		return _maxNumArgs;
	}

	public void setMaxNumArgs(int maxNumArgs)
	{
		_maxNumArgs = maxNumArgs;
	}

	@Override
	public void processEvent(SHEvent event)
	{
		String[] args = event.getParameter(SHConsoleState.ARGS_KEY, String[].class);
		_console = event.getParameter(SHConsoleState.CONSOLE_KEY, SHConsoleState.class);
		if (args == null)
		{
			error("Command is not typed");
		}
		else if (args.length >= 2 && args[1].equals(HELP_ARG))
		{
			info(getHelpMessage());
		}
		else if (args.length - 1 < _minNumArgs || args.length - 1 > _maxNumArgs)
		{
			error("Invalid number of arguments: " + (args.length - 1) + 
					". Must be [" + _minNumArgs + ".." + _maxNumArgs + "]");
		}
		else
		{
			processCommand(args);
		}
	}
	
	protected abstract void processCommand(String[] args);
	
	/** Return a one-line help message about how this command should be used */
	protected String getHelpMessage()
	{
		return "NO HELP";
	}
	
	protected void print(String message, ColorRGBA color)
	{
		if (_console != null)
		{
			_console.print(message, color);
		}
	}
	
	protected void info(String message)
	{
		if (_console != null)
		{
			_console.info(message);
		}
	}
	
	protected void warning(String message)
	{
		if (_console != null)
		{
			_console.warning(message);
		}
	}
	
	protected void error(String message)
	{
		if (_console != null)
		{
			_console.error(message);
		}
	}

}
