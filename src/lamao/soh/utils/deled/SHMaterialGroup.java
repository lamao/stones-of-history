/* 
 * SHMaterialGroup.java 25.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;

/**
 * Incapsulate information about material (MaterialState, BlendState and
 * TextureState)
 * @author lamao
 *
 */
class SHMaterialGroup
{
    public SHMaterialGroup() 
    {
        m = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
        m.setAmbient(new ColorRGBA(.2f, .2f, .2f, 1));
        m.setDiffuse(new ColorRGBA(.8f, .8f, .8f, 1));
        m.setSpecular(ColorRGBA.white.clone());
        m.setEnabled(true);
    }

    public void createBlendState() 
    {
        if (as != null)
        {
            return;
        }
        as = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
        as.setBlendEnabled(true);
        as.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        as.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
        as.setTestEnabled(true);
        as.setTestFunction(BlendState.TestFunction.GreaterThan);
        as.setEnabled(true);
    }

    MaterialState m;
    TextureState ts;
    BlendState as;
}
