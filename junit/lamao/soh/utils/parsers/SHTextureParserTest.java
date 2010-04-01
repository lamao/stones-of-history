/* 
 * SHTextureParser.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.parsers;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import lamao.soh.utils.SHResourceManager;

import org.junit.Test;

import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

/**
 * @author lamao
 *
 */
public class SHTextureParserTest
{
	public static String TEXTURE_PATH = "/data/textures/bubble.jpg";
	
	static 
	{
		DisplaySystem.setSystemProvider(new DummySystemProvider());
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
	}
	
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
