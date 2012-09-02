/* 
 * SHTextureParser.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;

import java.util.HashMap;

import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.resparser.ISHResourceParser;
import lamao.soh.utils.resparser.SHTextureParser;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author lamao
 *
 */
public class SHTextureParserTest
{
	public static String TEXTURE_PATH = "/data/textures/bubble.jpg";
	
	@Test
	public void testParser()
	{
		SHDummyResManager manager = new SHDummyResManager();
		SHTextureParser parser = new SHTextureParser();
		
		HashMap<String, String> args = new HashMap<String, String>();
		args.put(ISHResourceParser.TYPE_KEY, SHResourceManager.TYPE_MODEL);
		args.put(ISHResourceParser.LABEL_KEY, "model");
		args.put(ISHResourceParser.PATH_KEY, SHTextureParserTest.class
				.getResource(TEXTURE_PATH).getPath());
		
		parser.parse(args, manager);
		assertEquals(1, manager.numAdded);
		
		args.put(SHTextureParser.KEY_FILTERING, "bilinear");
		parser.parse(args, manager);
		assertEquals(2, manager.numAdded);
		
		args.put(SHTextureParser.KEY_FILTERING, "trilinear");
		parser.parse(args, manager);
		assertEquals(3, manager.numAdded);
		
		args.put(ISHResourceParser.PATH_KEY, "asdfh");
		parser.parse(args, manager);
		assertEquals(3, manager.numAdded);
		
	}
	

}
