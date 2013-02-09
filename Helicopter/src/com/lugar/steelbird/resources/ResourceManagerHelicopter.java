package com.lugar.steelbird.resources;

import com.lugar.steelbird.helicopter.HelicopterType;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class ResourceManagerHelicopter {

    private TextureRegion mHelicopterBody;
    private TextureRegion mHelicopterPropeller;
    private TextureRegion mHelicopterShadow;

    public ResourceManagerHelicopter(BaseGameActivity baseGameActivity, HelicopterType helicopterType) {

        BitmapTextureAtlas helicopterTextureAtlas = null;

        switch (helicopterType) {
            case LIGHT:
                helicopterTextureAtlas = new BitmapTextureAtlas(baseGameActivity.getTextureManager(), 450, 151);
                mHelicopterBody = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helicopterTextureAtlas,
                        baseGameActivity, "helicopter_body.png", 0, 0);
                mHelicopterPropeller = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helicopterTextureAtlas,
                        baseGameActivity, "helicopter_propeller.png", 150, 0);
                mHelicopterShadow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helicopterTextureAtlas,
                        baseGameActivity, "helicopter_shadow.png", 300, 0);
                break;
        }

        if (helicopterTextureAtlas != null) {
            helicopterTextureAtlas.load();
        }
    }

    public TextureRegion getHelicopterBody() {
        return mHelicopterBody;
    }

    public TextureRegion getHelicopterPropeller() {
        return mHelicopterPropeller;
    }

    public TextureRegion getHelicopterShadow() {
        return mHelicopterShadow;
    }
}
