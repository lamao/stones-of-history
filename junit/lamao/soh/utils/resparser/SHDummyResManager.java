/* 
 * SHDummyResourceManager.java 1.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;

import lamao.soh.utils.SHResourceManager;

/**
 * Dummy resource manager. Used for testing puproses.
 * @author lamao
 *
 */
public class SHDummyResManager extends SHResourceManager
{
	public int numAdded = 0;

	protected SHDummyResManager()
	{
	}

	@Override
	public void add(String type, String label, Object resource)
	{
		numAdded++;
	}
}
