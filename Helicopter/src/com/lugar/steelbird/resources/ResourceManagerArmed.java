package com.lugar.steelbird.resources;

import com.lugar.steelbird.LocationType;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public abstract class ResourceManagerArmed {

    protected TextureRegion mTankBody;
    protected TextureRegion mTankTower;
    protected TextureRegion mTankShadow;

    protected TextureRegion mSoldier_1;
    protected TextureRegion mSoldier_2;
    protected TextureRegion mSoldier_3;

    protected TextureRegion mSoldierShadow_1;
    protected TextureRegion mSoldierShadow_2;
    protected TextureRegion mSoldierShadow_3;

    public ResourceManagerArmed(BaseGameActivity baseGameActivity) {

    }

    public abstract TextureRegion getTankBody();
    public abstract TextureRegion getTankTower();
    public abstract TextureRegion getTankShadow();
    public abstract TextureRegion getSoldier_1();
    public abstract TextureRegion getSoldier_2();
    public abstract TextureRegion getSoldier_3();
    public abstract TextureRegion getSoldierShadow_1();
    public abstract TextureRegion getSoldierShadow_2();
    public abstract TextureRegion getSoldierShadow_3();
}
