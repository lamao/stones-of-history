/* 
 * SHLevelInfoTest.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;

import lamao.soh.core.SHUtils;
import lamao.soh.core.model.entity.SHLevel;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import com.thoughtworks.xstream.XStream;

/**
 * @author lamao
 *
 */
public class SHLevelTest
{
	public static float YEAR = 1234.1f;
	public static String MODELS = "file/with/models.dps";
	public static String INTRO = "Some intro";
	public static boolean COMPLETED = false;
	public static String NAME = "Very first level";
	public static String XML_LEVEL_TEMPLATE = 
			"<level>\n" +
			"  <year>" + YEAR + "</year>\n" +
			"  <models>%s</models>\n" +
			"  <intro>%s</intro>\n" +
			"  <completed>%b</completed>\n" + 
			"  <name>%s</name>\n" +
			"</level>";
	public static String XML_LEVEL_WITHOUT_COMPLETED_TEMPLATE = 
			"<level>\n" +
			"  <year>" + YEAR + "</year>\n" +
			"  <models>%s</models>\n" +
			"  <intro>%s</intro>\n" +
			"  <name>%s</name>\n" +
			"</level>";
	public static String XML_LEVEL = String.format(XML_LEVEL_TEMPLATE, 
			MODELS, INTRO, COMPLETED, NAME);
	public static String XML_LEVEL_WITHOUT_COMPLETED = String.format(
			XML_LEVEL_WITHOUT_COMPLETED_TEMPLATE, MODELS, INTRO, NAME); 

			
	
	@Test
	public void testToXml()
	{
		SHLevel level = new SHLevel();
		level.setYear(YEAR);
		level.setModels(MODELS);
		level.setIntro(INTRO);
		level.setCompleted(COMPLETED);
		level.setName(NAME);
		
		
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevel.class);
        
        String xml = xstream.toXML(level);
        assertEquals(XML_LEVEL.replaceAll(" ", ""), xml.replaceAll(" ", ""));
	}
	
	@Test
	public void testFromXml()
	{
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevel.class);
        
        SHLevel level = (SHLevel) xstream.fromXML(XML_LEVEL);
        assertTrue(SHUtils.inRange(level.getYear(), YEAR - 0.00001f, YEAR + 0.00001f));
        assertEquals(MODELS, level.getModels());
        assertEquals(INTRO, level.getIntro());
        assertEquals(COMPLETED, level.isCompleted());
        assertEquals(NAME, level.getName());
	}
	
	@Test
	public void testFromXmlWithoutCompleted() {
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevel.class);
		
        SHLevel level = (SHLevel) xstream.fromXML(XML_LEVEL_WITHOUT_COMPLETED);
        assertTrue(SHUtils.inRange(level.getYear(), YEAR - 0.00001f, YEAR + 0.00001f));
        assertEquals(MODELS, level.getModels());
        assertEquals(INTRO, level.getIntro());
        assertEquals(false, level.isCompleted());
        assertEquals(NAME, level.getName());
	}

}
