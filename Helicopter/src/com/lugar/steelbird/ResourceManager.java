package com.lugar.steelbird;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

public class ResourceManager {

    private TextureRegion mHelicopterBody;
    private TextureRegion mHelicopterPropeller;
    private TextureRegion mHelicopterShadow;

    private TextureRegion mBomb;
    private TextureRegion mBullet;

    private TextureRegion mOnScreenControlBaseTextureRegion;
    private TextureRegion mOnScreenControlKnobTextureRegion;

    private TiledTextureRegion mExplosion;

    private TextureRegion mTankBody;
    private TextureRegion mTankTower;
    private TextureRegion mTankShadow;

    private TextureRegion mTree1;
    private TextureRegion mTreeShadow1;


    private TextureRegion mBackGround;

    private TextureRegion mJoystick;

    private ITexture mFontTexture;
    private Font mFont;

    private VertexBufferObjectManager mVertexBufferObjectManager;

    public ResourceManager(BaseGameActivity baseGameActivity) {
        TextureManager textureManager = baseGameActivity.getTextureManager();
        mVertexBufferObjectManager = baseGameActivity.getVertexBufferObjectManager();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        BitmapTextureAtlas helicopterTextureAtlas = new BitmapTextureAtlas(textureManager, 450, 151);
        BitmapTextureAtlas bombTextureAtlas = new BitmapTextureAtlas(textureManager, 13, 45);
        BitmapTextureAtlas bulletTextureAtlas = new BitmapTextureAtlas(textureManager, 11, 31);
        BitmapTextureAtlas onScreenControlTextureAtlas = new BitmapTextureAtlas(textureManager, 256, 128);
        BitmapTextureAtlas explosionTextureAtlas = new BitmapTextureAtlas(textureManager, 1024, 768);
        BitmapTextureAtlas tankTextureAtlas = new BitmapTextureAtlas(textureManager, 132, 107);
        BitmapTextureAtlas backgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 800);
        BitmapTextureAtlas joystickTextureAtlas = new BitmapTextureAtlas(textureManager, 352, 352);
        BitmapTextureAtlas tree1TextureAtlas = new BitmapTextureAtlas(textureManager, 512, 256);

        mHelicopterBody = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helicopterTextureAtlas,
                baseGameActivity, "helicopter_body.png", 0, 0);

        mHelicopterPropeller = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helicopterTextureAtlas,
                baseGameActivity, "helicopter_propeller.png", 150, 0);

        mHelicopterShadow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helicopterTextureAtlas,
                baseGameActivity, "helicopter_shadow.png", 300, 0);

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
                baseGameActivity, "tank_tower.png", 44, 0);

        mTankShadow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tankTextureAtlas,
                baseGameActivity, "tank_shadow.png", 88, 0);

        mBackGround = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas,
                baseGameActivity, "background_big.jpg", 0, 0);

        mJoystick = BitmapTextureAtlasTextureRegionFactory.createFromAsset(joystickTextureAtlas,
                baseGameActivity, "joystick.png", 0, 0);

        mTree1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tree1TextureAtlas,
                baseGameActivity, "tree_1.png", 0, 0);

        mTreeShadow1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tree1TextureAtlas,
                baseGameActivity, "tree_1_shadow.png", 256, 0);

        helicopterTextureAtlas.load();
        bombTextureAtlas.load();
        bulletTextureAtlas.load();
        onScreenControlTextureAtlas.load();
        explosionTextureAtlas.load();
        tankTextureAtlas.load();
        backgroundTextureAtlas.load();
        joystickTextureAtlas.load();
        tree1TextureAtlas.load();

        FontFactory.setAssetBasePath("fonts/");
        this.mFontTexture = new BitmapTextureAtlas(baseGameActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

        this.mFont = FontFactory.createFromAsset(baseGameActivity.getFontManager(), mFontTexture,
                baseGameActivity.getAssets(), "font.ttf", 16, true, Color.WHITE_ABGR_PACKED_INT);

        this.mFont.load();
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

    public TextureRegion getJoystick() {
        return mJoystick;
    }

    public Font getFont() {
        return mFont;
    }

    public TextureRegion getTree1() {
        return mTree1;
    }

    public TextureRegion getTreeShadow1() {
        return mTreeShadow1;
    }

    public TextureRegion getTankShadow() {
        return mTankShadow;
    }
}
