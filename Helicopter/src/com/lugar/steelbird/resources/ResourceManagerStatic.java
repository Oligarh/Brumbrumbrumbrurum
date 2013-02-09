package com.lugar.steelbird.resources;

import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public abstract class ResourceManagerStatic {

    protected TextureRegion mTree_1;
    protected TextureRegion mTreeShadow_1;
    protected TextureRegion mTree_2;
    protected TextureRegion mTreeShadow_2;
    protected TextureRegion mTree_3;
    protected TextureRegion mTreeShadow_3;
    protected TextureRegion mTree_4;
    protected TextureRegion mTreeShadow_4;
    protected TextureRegion mTree_5;
    protected TextureRegion mTreeShadow_5;
    
    public ResourceManagerStatic(BaseGameActivity baseGameActivity) {
    }

    public abstract TextureRegion getTree_1();
    public abstract TextureRegion getTreeShadow_1();
    public abstract TextureRegion getTree_2();
    public abstract TextureRegion getTreeShadow_2();
    public abstract TextureRegion getTree_3();
    public abstract TextureRegion getTreeShadow_3();
    public abstract TextureRegion getTree_4();
    public abstract TextureRegion getTreeShadow_4();
    public abstract TextureRegion getTree_5();
    public abstract TextureRegion getTreeShadow_5();
}
