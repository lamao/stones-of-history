/* 
 * SHBaseCommandTest.java Jun 3, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

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
	/** Key for argument in event map */
	public final static String ARGS_KEY = "args";
	
	/** Minimum number of arguments. NOTE: args[0] is command name */ 
	private int _minNumArgs = -1;
	
	/** Maximum number of arguments. NOTE: args[0] is command name */
	private int _maxNumArgs = Integer.MAX_VALUE;
	
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
		String[] args = (String[]) event.params.get(ARGS_KEY);
		if (args != null && args.length - 1 >= _minNumArgs && args.length - 1 <= _maxNumArgs)
		{
			processCommand(args);
		}
	}
	
	protected abstract void processCommand(String[] args);

}
