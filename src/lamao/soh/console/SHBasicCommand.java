/* 
 * SHBaseCommandTest.java Jun 3, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import com.jme.renderer.ColorRGBA;

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
		String[] args = (String[]) event.params.get(SHConsoleState.ARGS_KEY);
		_console = (SHConsoleState)event.params.get(SHConsoleState.CONSOLE_KEY);
		if (args == null)
		{
			error("Command is not typed");
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
