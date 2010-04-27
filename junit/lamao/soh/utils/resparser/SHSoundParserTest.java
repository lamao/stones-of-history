/* 
 * SHSoundParserTest.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import lamao.soh.utils.SHResourceManager;

import org.junit.Test;

/**
 * @author lamao
 *
 */
public class SHSoundParserTest
{
public static String TEXTURE_PATH = "/data/sounds/line.ogg";
	
	static 
	{
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
	}
	
	@Test
	public void testParser()
	{
		// XXX: JVM error in C code (OpenAL32.dll+0xc059)
//		SHDummyResManager manager = new SHDummyResManager();
//		SHSoundParser parser = new SHSoundParser();
//		
//		HashMap<String, String> args = new HashMap<String, String>();
//		args.put(ISHResourceParser.TYPE_KEY, SHResourceManager.TYPE_SOUND);
//		args.put(ISHResourceParser.LABEL_KEY, "sound");
//		args.put(ISHResourceParser.PATH_KEY, SHSoundParser.class
//				.getResource(TEXTURE_PATH).getPath());
//		
//		parser.parse(args, manager);
//		assertEquals(1, manager.numAdded);
//		
//		args.put(SHSoundParser.KEY_STREAM, "true");
//		parser.parse(args, manager);
//		assertEquals(2, manager.numAdded);
//		
//		args.put(SHSoundParser.KEY_LOOP, "true");
//		parser.parse(args, manager);
//		assertEquals(3, manager.numAdded);
//		
//		args.put(ISHResourceParser.PATH_KEY, "asdfh");
//		parser.parse(args, manager);
//		assertEquals(3, manager.numAdded);
		
	}
}
