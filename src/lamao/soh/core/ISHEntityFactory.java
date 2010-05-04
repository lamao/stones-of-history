/* 
 * SHEntityFactory.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.Map;

/**
 * Factory for crating entities from metadata
 * @author lamao
 *
 */
public interface ISHEntityFactory
{
	public SHEntity createEntity(Map<String, String> metadata);
}
