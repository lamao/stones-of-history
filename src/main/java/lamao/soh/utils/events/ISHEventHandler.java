/* 
 * ISHEventHandler.java 27.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

/**
 * Handler for event from <{@link SHEventDispatcher}
 * @author lamao
 *
 */
public interface ISHEventHandler
{
	void processEvent(SHEvent event);
}
