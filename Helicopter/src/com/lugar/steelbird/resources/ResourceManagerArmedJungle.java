package com.lugar.steelbird.resources;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

public class ResourceManagerArmedJungle extends ResourceManagerArmed {

    public ResourceManagerArmedJungle(BaseGameActivity baseGameActivity) {
        super(baseGameActivity);

        TextureManager textureManager = baseGameActivity.getTextureManager();

        BitmapTextureAtlas tankTextureAtlas = new BitmapTextureAtlas(textureManager, 132, 107);
        BitmapTextureAtlas soldierTextureAtlas = new BitmapTextureAtlas(textureManager, 132, 100);

        mTankBody = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tankTextureAtlas,
                baseGameActivity, "tank_body.png", 0, 0);

        mTankTower = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tankTextureAtlas,
                baseGameActivity, "tank_tower.png", 44, 0);

        mTankShadow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tankTextureAtlas,
                baseGameActivity, "tank_shadow.png", 88, 0);

        mSoldier_1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(soldierTextureAtlas,
                baseGameActivity, "soldier_1.png", 0, 0);

        mSoldier_2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(soldierTextureAtlas,
                baseGameActivity, "soldier_2.png", 27, 0);

        mSoldier_3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(soldierTextureAtlas,
                baseGameActivity, "soldier_3.png", 54, 0);

        mSoldierShadow_1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(soldierTextureAtlas,
                baseGameActivity, "soldier_shadow1.png", 0, 38);

        mSoldierShadow_2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(soldierTextureAtlas,
                baseGameActivity, "soldier_shadow2.png", 27, 38);

        mSoldierShadow_3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(soldierTextureAtlas,
                baseGameActivity, "soldier_shadow3.png", 54, 38);

        tankTextureAtlas.load();
        soldierTextureAtlas.load();
    }

    @Override
    public TextureRegion getTankBody() {
        return mTankBody;
    }

    @Override
    public TextureRegion getTankTower() {
        return mTankTower;
    }

    @Override
    public TextureRegion getTankShadow() {
        return mTankTower;
    }

    @Override
    public TextureRegion getSoldier_1() {
        return mSoldier_1;
    }

    @Override
    public TextureRegion getSoldier_2() {
        return mSoldier_2;
    }

    @Override
    public TextureRegion getSoldier_3() {
        return mSoldier_3;
    }

    @Override
    public TextureRegion getSoldierShadow_1() {
        return mSoldierShadow_1;
    }

    @Override
    public TextureRegion getSoldierShadow_2() {
        return mSoldierShadow_2;
    }

    @Override
    public TextureRegion getSoldierShadow_3() {
        return mSoldierShadow_3;
    }
}
