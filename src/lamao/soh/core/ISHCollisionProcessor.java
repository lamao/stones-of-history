/* 
 * ISHCollisionProcessor.java 16.09.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.List;

import com.jme.scene.Node;

/**
 * @author lamao
 *
 */
public interface ISHCollisionProcessor
{

	public abstract List<SHCollisionTask> getCollisionTasks();

	public abstract void addCollisionTask(SHCollisionTask task);

	public abstract void removeCollisionTask(SHCollisionTask task);

	public abstract void processCollisions(Node rootNode);

}