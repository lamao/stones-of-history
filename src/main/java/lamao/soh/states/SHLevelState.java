/*
 * SHLevelState.java 25.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.states;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import lamao.soh.core.SHScene;
import lamao.soh.core.input.SHBreakoutInputHandler;
import lamao.soh.core.model.SHEpochLevelItem;
import lamao.soh.ui.controllers.SHInGameScreenController;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme3.light.PointLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import de.lessvoid.nifty.Nifty;

/**
 * Game state for actual game for one level (from starting to winning or loosing).
 * @author lamao
 */
public class SHLevelState extends AbstractAppState {

    /** Level for playing */
    private SHScene scene = null;

    /** Indicates where draw bounding volumes or not */
    private boolean drawBounds = false;

    /** Indicates whether draw normals for scene */
    private boolean drawNormals = false;

    private boolean pause = false;

    /** Dispatcher used to fire events */
    private SHEventDispatcher dispatcher;

    /** Container for nifty UI elements */
    private Nifty nifty;

    private String startNiftyScreen;

    private SHInGameScreenController inGameScreenController;


    private SimpleApplication application;

    private Node rootNode;

    private Camera camera;

    private InputManager inputManager;

    private SHBreakoutInputHandler paddleInputListener;

    // TODO: Move to constructor scene
    public SHLevelState(
                    SimpleApplication application,
                    SHEventDispatcher dispatcher,
                    Nifty nifty,
                    String startNiftyScreen,
                    SHInGameScreenController inGameScreenController,
                    InputManager inputManager) {
        super();

        this.application = application;
        rootNode = application.getRootNode();
        camera = application.getCamera();
        this.dispatcher = dispatcher;
        this.nifty = nifty;
        this.startNiftyScreen = startNiftyScreen;
        this.inGameScreenController = inGameScreenController;
        this.inputManager = inputManager;
        setEnabled(false);

        PointLight light = new PointLight();
        light.setPosition(new Vector3f(0, 3, 3));
        light.setColor(ColorRGBA.White.clone());
        rootNode.addLight(light);

        camera.setLocation(new Vector3f(0, 13, 18));
        camera.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);

        initInputListeners();

    }

    private void initInputListeners() {
        inputManager.addMapping("paddle-left", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping("paddle-right", new MouseAxisTrigger(MouseInput.AXIS_X, false));

        paddleInputListener = new SHBreakoutInputHandler(scene);
        paddleInputListener.setConstraints(-7, 7);
    }

    private void enabledInputListeners() {
        inputManager.addListener(paddleInputListener, "paddle-left", "paddle-right");
    }

    private void disableInputListeners() {
        inputManager.removeListener(paddleInputListener);
    }

    public SHScene getScene() {
        return scene;
    }

    public void setScene(SHScene scene) {
        if (this.scene != null && this.scene != scene) {
            rootNode.detachChild(this.scene.getRootNode());
        }
        this.scene = scene;
        paddleInputListener.setScene(scene);
    }

    @Override
    public void update(float tpf) {
        nifty.update();
        if (!pause) {
            super.update(tpf);
            scene.update(tpf);
            dispatcher.update(tpf);
        }
    }

    @Override
    public void render(RenderManager renderManager) {
        super.render(renderManager);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!isEnabled() && enabled) {
            if (!rootNode.hasChild(scene.getRootNode())) {
                rootNode.attachChild(scene.getRootNode());
            }
            enabledInputListeners();
            inputManager.setCursorVisible(false);
            setPause(false);
            nifty.gotoScreen(startNiftyScreen);
        } else if (isEnabled() && !enabled) {
            disableInputListeners();
            nifty.exit();
        }
        super.setEnabled(enabled);
    }

    @Override
    public void cleanup() {
        super.cleanup();

    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isPause() {
        return pause;
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

    /**
     * Used in Spring context
     * @return
     */
    public Node getRootNode() {
        return rootNode;
    }
}
