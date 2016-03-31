/*
 * SHLevelState.java 25.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.states;

import com.jme3.app.state.AbstractAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import lamao.soh.core.Application;
import lamao.soh.core.SHScene;
import lamao.soh.core.model.SHEpochLevelItem;
import lamao.soh.ui.controllers.SHInGameScreenController;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme3.light.PointLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.Renderer;

import de.lessvoid.nifty.Nifty;

/**
 * Game state for actual game for one level (from starting to winning or loosing).
 * @author lamao
 */
public class SHLevelState extends AbstractAppState {
    public static final String NAME = "Level state";

    /** Level for playing */
    private SHScene scene = null;

    /** Indicates where draw bounding volumes or not */
    private boolean drawBounds = false;

    /** Indicates whether draw normals for scene */
    private boolean drawNormals = false;

    private boolean _pause = false;

    /** Dispatcher used to fire events */
    private SHEventDispatcher dispatcher;

    /** Container for nifty UI elements */
    private Nifty nifty;

    private String startNiftyScreen;

    private SHInGameScreenController inGameScreenController;

    private Node rootNode;

    private Camera camera;

    // TODO: Move to constructor scene and inputhandler
    public SHLevelState(
                    SHEventDispatcher dispatcher,
                    Nifty nifty,
                    String startNiftyScreen,
                    SHInGameScreenController inGameScreenController) {
        super();

        this.dispatcher = dispatcher;
        this.nifty = nifty;
        this.startNiftyScreen = startNiftyScreen;
        this.inGameScreenController = inGameScreenController;

        PointLight light = new PointLight();
        light.setPosition(new Vector3f(0, 3, 3));
        light.setColor(ColorRGBA.White.clone());
        rootNode.addLight(light);

        camera.setLocation(new Vector3f(0, 13, 18));
        camera.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);

    }

    public SHScene getScene() {
        return scene;
    }

    public void setScene(SHScene scene) {
        if (this.scene != null && this.scene != scene) {
            rootNode.detachChild(this.scene.getRootNode());
        }
        this.scene = scene;
        rootNode.attachChild(this.scene.getRootNode());
    }

    @Override
    public void update(float tpf) {
        nifty.update();
        if (!_pause) {
            super.update(tpf);
            scene.update(tpf);
            dispatcher.update(tpf);
        }
    }

    @Override
    public void render(RenderManager renderManager) {
        super.render(renderManager);
        nifty.render(false);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!isEnabled() && enabled) {
            setPause(false);
            nifty.gotoScreen(startNiftyScreen);
        } else if (isEnabled() && !enabled) {
            nifty.exit();
        }
        super.setEnabled(enabled);
    }

    @Override
    public void cleanup() {
        super.cleanup();

    }

    public void setPause(boolean pause) {
        _pause = pause;
    }

    public boolean isPause() {
        return _pause;
    }

    public boolean isDrawBounds() {
        return drawBounds;
    }

    public void setDrawBounds(boolean drawBounds) {
        this.drawBounds = drawBounds;
    }

    public boolean isDrawNormals() {
        return drawNormals;
    }

    public void setDrawNormals(boolean drawNormals) {
        this.drawNormals = drawNormals;
    }

    public void setLevelInfo(SHEpochLevelItem levelInfo) {
        inGameScreenController.setLevelInfo(levelInfo);
    }
}
