/* 
 * SHBmFontParserTest.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.resparser.ISHResourceParser;
import lamao.soh.utils.resparser.SHBmFontParser;

import org.junit.Test;

import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

/**
 * @author lamao
 *
 */
public class SHBmFontParserTest
{
	public static String FONT_PATH = "/data/fonts/snap.fnt";
	public static String FONT_TEX_PATH = "/data/fonts/snap_0.png";
	
	static 
	{
		DisplaySystem.setSystemProvider(new DummySystemProvider());
	}
	
	@Test
	public void testParser()
	{
		SHDummyResManager manager = new SHDummyResManager();
		SHBmFontParser parser = new SHBmFontParser();
		
		HashMap<String, String> args = new HashMap<String, String>();
		args.put(ISHResourceParser.TYPE_KEY, SHResourceManager.TYPE_BMFONT);
		args.put(ISHResourceParser.LABEL_KEY, "model");
		args.put(ISHResourceParser.PATH_KEY, SHTextureParserTest.class
				.getResource(FONT_PATH).getPath());
		args.put(SHBmFontParser.KEY_TEXTURE, SHTextureParserTest.class
				.getResource(FONT_TEX_PATH).getPath());
		
		parser.parse(args, manager);
		assertEquals(1, manager.numAdded);
		
	}

}
