/* 
 * SHTtFontParser.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;


import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.resparser.ISHResourceParser;
import lamao.soh.utils.resparser.SHTtFontParser;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
/**
 * @author lamao
 *
 */
public class SHTtFontParserTest
{
	public static String FONT_PATH = "/data/fonts/snap.ttf";
	
	static 
	{
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
	}
	
	@Test
	public void testParser()
	{
		SHDummyResManager manager = new SHDummyResManager();
		SHTtFontParser parser = new SHTtFontParser();
		
		HashMap<String, String> args = new HashMap<String, String>();
		args.put(ISHResourceParser.TYPE_KEY, SHResourceManager.TYPE_BMFONT);
		args.put(ISHResourceParser.LABEL_KEY, "model");
		args.put(ISHResourceParser.PATH_KEY, SHTextureParserTest.class
				.getResource(FONT_PATH).getPath());
		
		parser.parse(args, manager);
		assertEquals(1, manager.numAdded);
		
		args.put(ISHResourceParser.PATH_KEY, "asdfh");
		parser.parse(args, manager);
		assertEquals(1, manager.numAdded);
		
	}
}
