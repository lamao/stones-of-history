/** 
 * XmlFileFilter.java 22.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author lamao
 *
 */
public class XmlFilenameFilter implements FilenameFilter 
{
	private static final String FILE_EXTENSION_XML = ".xml";
	
	@Override
	public boolean accept(File dir, String name)
	{
		return name.endsWith(FILE_EXTENSION_XML);
	}
}
