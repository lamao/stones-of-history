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
	List<SHCollisionTask> getCollisionTasks();

	/**
	 * Add collision task
	 * @param task - collision task
	 */
	void addCollisionTask(SHCollisionTask task);
	
	/**
	 * Sets collisions tasks. Replaces current tasks
	 * @param tasks - list of tasks to set
	 */
	void setCollisionTasks(List<SHCollisionTask> tasks);

	/**
	 * Remove collision task
	 * @param task
	 */
	void removeCollisionTask(SHCollisionTask task);

	/**
	 * Check for collission between entities in this rootNode and fire event if
	 * it was happened. Only those pairs will be checked that have collision task
	 * @param rootNode - rootNode of the scene
	 */
	void processCollisions(Node rootNode);

}