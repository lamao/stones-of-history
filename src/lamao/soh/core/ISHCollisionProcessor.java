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

	/**
	 * Get all collision tasks specified for this processor
	 * @return
	 */
	public abstract List<SHCollisionTask> getCollisionTasks();

	/**
	 * Add collision task
	 * @param task - collision task
	 */
	public abstract void addCollisionTask(SHCollisionTask task);

	/**
	 * Remove collision task
	 * @param task
	 */
	public abstract void removeCollisionTask(SHCollisionTask task);

	/**
	 * Check for collission between entities in this rootNode and fire event if
	 * it was happened. Only those pairs will be checked that have collision task
	 * @param rootNode - rootNode of the scene
	 */
	public abstract void processCollisions(Node rootNode);

}