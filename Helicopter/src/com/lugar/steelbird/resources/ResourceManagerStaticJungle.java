package com.lugar.steelbird.resources;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class ResourceManagerStaticJungle extends ResourceManagerStatic {

    public ResourceManagerStaticJungle(BaseGameActivity baseGameActivity) {
        super(baseGameActivity);

        TextureManager textureManager = baseGameActivity.getTextureManager();
        
        BitmapTextureAtlas treeTextureAtlas = new BitmapTextureAtlas(textureManager, 1000, 1000);

        mTree_1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_1.png", 0, 0);

        mTree_2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_2.png", 200, 0);

        mTree_3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_3.png", 400, 0);

        mTree_4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_4.png", 600, 0);

        mTree_5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_5.png", 800, 0);

        mTreeShadow_1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_1_shadow.png", 0, 200);

        mTreeShadow_2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_2_shadow.png", 200, 200);

        mTreeShadow_3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_3_shadow.png", 400, 200);

        mTreeShadow_4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_4_shadow.png", 600, 200);

        mTreeShadow_5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(treeTextureAtlas,
                baseGameActivity, "palm_5_shadow.png", 800, 200);

        treeTextureAtlas.load();
    }

    @Override
    public TextureRegion getTree_1() {
        return mTree_1;
    }

    @Override
    public TextureRegion getTreeShadow_1() {
        return mTreeShadow_1;
    }

    @Override
    public TextureRegion getTree_2() {
        return mTree_2;
    }

    @Override
    public TextureRegion getTreeShadow_2() {
        return mTreeShadow_2;
    }

    @Override
    public TextureRegion getTree_3() {
        return mTree_3;
    }

    @Override
    public TextureRegion getTreeShadow_3() {
        return mTreeShadow_3;
    }

    @Override
    public TextureRegion getTree_4() {
        return mTree_4;
    }

    @Override
    public TextureRegion getTreeShadow_4() {
        return mTreeShadow_4;
    }

    @Override
    public TextureRegion getTree_5() {
        return mTree_5;
    }

    @Override
    public TextureRegion getTreeShadow_5() {
        return mTreeShadow_5;
    }
}
