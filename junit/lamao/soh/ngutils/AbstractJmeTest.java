/* 
 * AbstractTest.java 02.09.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ngutils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.annotations.BeforeSuite;

import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.dummy.DummyKeyInput;
import com.jme.input.dummy.DummyMouseInput;
import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

/**
 * Base class for all JME related tests. 
 * Disables logging in tests and sets dummy system provider.
 * 
 * @author lamao
 *
 */
public class AbstractJmeTest
{
	@BeforeSuite
	public void setupStatic()
	{
		Logger.getLogger("").setLevel(Level.OFF);
		DisplaySystem.setSystemProvider(new DummySystemProvider());
		KeyInput.setProvider(DummyKeyInput.class);
		MouseInput.setProvider(DummyMouseInput.class);
	}
}

	