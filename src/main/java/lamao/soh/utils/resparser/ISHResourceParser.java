/* 
 * ISHResourceParser.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;

import java.util.Map;

import lamao.soh.utils.SHResourceManager;

/**
 * Interface for parser of one resource (one line) from configuration file. 
 * @author lamao
 *
 */
public interface ISHResourceParser
{
	/* Names of common parameters */
	public final static String TYPE_KEY = "type";
	public final static String LABEL_KEY = "label";
	public final static String PATH_KEY = "path";
	
	/**
	 * Parses map of given <key, value> pairs. It should create and particular
	 * resource and add it to {@link SHResourceManager}.<br>
	 * <b>NOTE:</b> all keys and values (except values in quotes `"`) must
	 * be lower case. String values remains unchanged (because they typically
	 * define file names).
	 * 
	 * @param args - pairs of <key, value>
	 * @param manager - manager where loaded resource should be stored
	 */
	public void parse(Map<String, String> args, SHResourceManager manager);
}
