/*
 * ISHPaddleHitHandler.java 24.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core;

import com.jme3.scene.Spatial;
import lamao.soh.core.entities.SHPaddle;

/**
 * Interface for handlers ball hits with paddle.
 * @author lamao
 */
public interface ISHPaddleHitHandler {
    /**
     * Invoked when ball hits paddle.
     * @param ball - ball
     * @param paddle - ball
     */
    public void execute(Spatial ball, SHPaddle paddle);
}
