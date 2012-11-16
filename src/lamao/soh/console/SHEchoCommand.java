/* 
 * SHEchoCommand.java Jun 4, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

/**
 * Echo command. Just for testing
 * @author lamao
 *
 */
public class SHEchoCommand extends SHBasicCommand
{
	public static final String ECHO_LEVEL_INFO = "info";
	public static final String ECHO_LEVEL_WARNING = "warning";
	public static final String ECHO_LEVEL_ERROR = "error";
	
	public SHEchoCommand()
	{
		super(1, 2);
	}
	
	@Override
	protected void processCommand(String[] args)
	{
		if (args.length == 2 )
		{
			print(args[1], SHConsoleState.DEFAULT_COLOR);
		}
		else if (args[1].equals(ECHO_LEVEL_INFO))
		{
			info(args[2]);
		}
		else if (args[1].equals(ECHO_LEVEL_WARNING))
		{
			warning(args[2]);
		}
		else if (args[1].equals(ECHO_LEVEL_ERROR))
		{
			error(args[2]);
		}
	}
	
	@Override
	protected String getHelpMessage()
	{
		return "Arguments: [info|warning|error] <message>";
	}

}
