/* 
 * SHModelParserTest.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;

import java.util.HashMap;

import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.resparser.ISHResourceParser;
import lamao.soh.utils.resparser.SHModelParser;


import org.testng.annotations.Test;
import static org.testng.Assert.*;
/**
 * @author lamao
 *
 */
public class SHModelParserTest
{
	
	public static String MODEL_PATH = "/data/models/monkey.jme";
	
	@Test
	public void testParser()
	{
		SHDummyResManager manager = new SHDummyResManager();
		SHModelParser parser = new SHModelParser();
		
		HashMap<String, String> args = new HashMap<String, String>();
		args.put(ISHResourceParser.TYPE_KEY, SHResourceManager.TYPE_MODEL);
		args.put(ISHResourceParser.LABEL_KEY, "model");
		args.put(ISHResourceParser.PATH_KEY, SHModelParser.class
				.getResource(MODEL_PATH).getPath());
		
		parser.parse(args, manager);
		assertEquals(1, manager.numAdded);
		
		args.put(ISHResourceParser.PATH_KEY, "asdfh");
		parser.parse(args, manager);
		assertEquals(1, manager.numAdded);
	}
}
