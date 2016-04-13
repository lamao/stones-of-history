/*
 * SHCollisionProcessor.java 16.09.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.CollisionData;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * @author lamao
 */
public class SHCollisionProcessor implements ISHCollisionProcessor {
    private static final String EVENT_NAME_PATTERN = "scene-collision-%s-%s";

    /**
     * Pairs of entity types to check for collision.
     */
    private List<SHCollisionTask> collisionTasks = new LinkedList<SHCollisionTask>();

    private SHEventDispatcher dispatcher;

    private boolean enabled = true;

    /**
     * Creates collision processor
     * @param dispatcher - used to fire events when found collision
     */
    public SHCollisionProcessor(
                    SHEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SHCollisionTask> getCollisionTasks() {
        return collisionTasks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCollisionTask(SHCollisionTask task) {
        collisionTasks.add(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCollisionTasks(List<SHCollisionTask> tasks) {
        this.collisionTasks = tasks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCollisionTask(SHCollisionTask task) {
        collisionTasks.remove(task);
    }

    public SHEventDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(SHEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Checks if this model is entity and return it if so
     * @param model - model to check
     * @return entity or null if model is not an entity
     */
    private SHEntity getEntity(Spatial model) {
        if (model instanceof SHEntity) {
            return (SHEntity) model;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processCollisions(Node rootNode) {
        if (!isEnabled()) {
            return;
        }

        List<SHEvent> eventsToSend = new LinkedList<SHEvent>();
        Node source;
        Node dest;
        for (SHCollisionTask task : collisionTasks) {
            source = (Node) rootNode.getChild(task.getSourceType());
            dest = (Node) rootNode.getChild(task.getDestType());
            if (source != null && dest != null) {
                for (Spatial sourceModel : source.getChildren()) {
                    for (Spatial destModel : dest.getChildren()) {
                        CollisionResults results = new CollisionResults();
                        sourceModel.collideWith(destModel, results);

                        SHEntity sourceEntity = getEntity(sourceModel);
                        SHEntity destEntity = getEntity(destModel);
                        for (CollisionResult collisionResult : results) {
                            String event = getEventName(task.getSourceType(), task.getDestType());
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("data", collisionResult);
                            params.put("src", sourceEntity);
                            params.put("dst", destEntity);
                            eventsToSend.add(new SHEvent(event, this, params));
                        }
                    }
                }
            }
        }

        for (SHEvent event : eventsToSend) {
            dispatcher.addEvent(event);
        }
    }

    private String getEventName(String sourceType, String destType) {
        return String.format(EVENT_NAME_PATTERN, sourceType, destType);
    }
}
