/** 
 * SHAboutScreenController.java 08.01.2013
 * 
 * Copyright 2013 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

/**
 * @author lamao
 *
 */
public class SHAboutScreenController extends SHBasicScreenController
{
	private static final String ABOUT_CONTENTS = 
		"Stones of History\n" +
		"\n" +
		"Author: LAMAO\n" +
		"\n" +
		"Used tools and frameworks: \n" +
		"\tjMonkeyEngine\n" +
		"\tNiftyGui\n" +
		"\tSpring IoC\n" +
		"\tXStream\n" +
		"\tDeleD\n" +
		"\tBlender\n" +
		"\n" +
		"2010-2013";

	public String getAboutContents()
	{
		return ABOUT_CONTENTS;
	}
}
