/* 
 * SDAConsoleCommand.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

/**
 * Command handler for console.
 * @author lamao
 */
public interface ISHCommandHandler
{
	/** Executes given command
	 * @param command - full string of command including its name.
	 */
	public String execute(String[] args);
}
