package com.lugar.steelbird;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

public class ResourceManager {

    private TextureRegion mHelicopterBody;
    private TextureRegion mHelicopterPropeller;

    private TextureRegion mBomb;
    private TextureRegion mBullet;

    private TextureRegion mOnScreenControlBaseTextureRegion;
    private TextureRegion mOnScreenControlKnobTextureRegion;

    private TiledTextureRegion mExplosion;

    private TextureRegion mTankBody;
    private TextureRegion mTankTower;

    private TextureRegion mBackGround;

    private VertexBufferObjectManager mVertexBufferObjectManager;

    public ResourceManager(BaseGameActivity baseGameActivity) {
        TextureManager textureManager = baseGameActivity.getTextureManager();
        mVertexBufferObjectManager = baseGameActivity.getVertexBufferObjectManager();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        BitmapTextureAtlas helicopterTextureAtlas = new BitmapTextureAtlas(textureManager, 300, 151);
        BitmapTextureAtlas bombTextureAtlas = new BitmapTextureAtlas(textureManager, 13, 45);
        BitmapTextureAtlas bulletTextureAtlas = new BitmapTextureAtlas(textureManager, 11, 31);
        BitmapTextureAtlas onScreenControlTextureAtlas = new BitmapTextureAtlas(textureManager, 256, 128);
        BitmapTextureAtlas explosionTextureAtlas = new BitmapTextureAtlas(textureManager, 1024, 768);
        BitmapTextureAtlas tankTextureAtlas = new BitmapTextureAtlas(textureManager, 162, 197);
        BitmapTextureAtlas backgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 800);

        mHelicopterBody = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helicopterTextureAtlas,
                baseGameActivity, "helicopter_body.png", 0, 0);

        mHelicopterPropeller = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helicopterTextureAtlas,
                baseGameActivity, "helicopter_propeller.png", 150, 0);

        mBomb = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bombTextureAtlas,
                baseGameActivity, "bomb.png", 0, 0);

        mBullet = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bulletTextureAtlas,
                baseGameActivity, "bullet.png", 0, 0);

        mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                onScreenControlTextureAtlas, baseGameActivity, "onscreen_control_base.png", 0, 0);

        mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                onScreenControlTextureAtlas, baseGameActivity, "onscreen_control_knob.png", 128, 0);

        mExplosion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(explosionTextureAtlas,
                baseGameActivity, "explosion.png", 0, 0, 8, 6);

        mTankBody = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tankTextureAtlas,
                baseGameActivity, "tank_body.png", 0, 0);

        mTankTower = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tankTextureAtlas,
                baseGameActivity, "tank_tower.png", 81, 0);

        mBackGround = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas,
                baseGameActivity, "background_big.jpg", 0, 0);

        helicopterTextureAtlas.load();
        bombTextureAtlas.load();
        bulletTextureAtlas.load();
        onScreenControlTextureAtlas.load();
        explosionTextureAtlas.load();
        tankTextureAtlas.load();
        backgroundTextureAtlas.load();
    }

    public TextureRegion getHelicopterBody() {
        return mHelicopterBody;
    }

    public TextureRegion getHelicopterPropeller() {
        return mHelicopterPropeller;
    }

    public TextureRegion getBomb() {
        return mBomb;
    }

    public TextureRegion getBullet() {
        return mBullet;
    }

    public TextureRegion getOnScreenControlBaseTextureRegion() {
        return mOnScreenControlBaseTextureRegion;
    }

    public TextureRegion getOnScreenControlKnobTextureRegion() {
        return mOnScreenControlKnobTextureRegion;
    }

    public TiledTextureRegion getExplosion() {
        return mExplosion;
    }

    public TextureRegion getTankBody() {
        return mTankBody;
    }

    public TextureRegion getTankTower() {
        return mTankTower;
    }

    public TextureRegion getBackGround() {
        return mBackGround;
    }

    public VertexBufferObjectManager getVertexBufferObjectManager() {
        return mVertexBufferObjectManager;
    }
}
