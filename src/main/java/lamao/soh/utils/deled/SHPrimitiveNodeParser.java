/* 
 * SHPrimitiveNodeParser.java 25.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.util.Map;

import com.jme.scene.Node;
import com.jme.scene.Spatial;

/**
 * Adds parsed spatial to the root node. It builds just a 3D jME scene and 
 * don't care about any game-related features.
 * @author lamao
 *
 */
class SHPrimitiveNodeParser extends SHPrimitiveParser
{
	/** Node for adding constructed spatials */
	private Node _rootNode;
	
	public SHPrimitiveNodeParser(Map<String, SHMaterialGroup> materials,
			Node rootNode)
	{
		super(materials);
		_rootNode = rootNode;
	}
	
	@Override
	public void spatialConstructed(org.w3c.dom.Node node, Spatial spatial)
	{
		_rootNode.attachChild(spatial);
		
	}

}
