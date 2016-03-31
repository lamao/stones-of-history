/* 
 * SHMaterialGroup.java 25.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

/**
 * Incapsulate information about material (MaterialState, BlendState and
 * TextureState)
 * @author lamao
 *
 */
class SHMaterialGroup
{

    public static final String MATERIAL_COLOR_AMBIENT = "Ambient";
    public static final String MATERIAL_COLOR_DIFFUSE = "Diffuse";
    public static final String MATERIAL_COLOR_SPECULAR = "Specular";
    public static final String MATERIAL_ATTR_SHININESS = "Shininess";

    public SHMaterialGroup()
    {
        m = new Material();
        m.setColor(MATERIAL_COLOR_AMBIENT, new ColorRGBA(.2f, .2f, .2f, 1));
        m.setColor(MATERIAL_COLOR_DIFFUSE, new ColorRGBA(.8f, .8f, .8f, 1));
        m.setColor(MATERIAL_COLOR_SPECULAR, ColorRGBA.White.clone());
    }

    Material m;
    Texture t;
}
