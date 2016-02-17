package com.natashaleonteva.backgroundmusic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by n.leonteva on 03.02.16.
 */
public class FontHandler {
    private static BitmapFont regular;
    public static BitmapFont getFont(){
        if (regular!=null)
            return regular;
        regular= new BitmapFont(Gdx.files.internal("regular.fnt"));
        regular.setUseIntegerPositions(true);
        regular.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return regular;
    }

}
