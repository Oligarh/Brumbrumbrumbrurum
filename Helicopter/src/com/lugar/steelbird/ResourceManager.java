package com.lugar.steelbird;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import java.util.Random;

public class ResourceManager {

    private TextureRegion mHelicopterBody;
    private TextureRegion mHelicopterPropeller;
    private TextureRegion mHelicopterShadow;

    private TextureRegion mBomb;
    private TextureRegion mBullet;

    private TextureRegion mOnScreenControlBaseTextureRegion;
    private TextureRegion mOnScreenControlKnobTextureRegion;

    private TextureRegion mTankBody;
    private TextureRegion mTankTower;
    private TextureRegion mTankShadow;

    private TextureRegion mPalm_1;
    private TextureRegion mPalmShadow_1;
    private TextureRegion mPalm_2;
    private TextureRegion mPalmShadow_2;
    private TextureRegion mPalm_3;
    private TextureRegion mPalmShadow_3;
    private TextureRegion mPalm_4;
    private TextureRegion mPalmShadow_4;
    private TextureRegion mPalm_5;
    private TextureRegion mPalmShadow_5;

    private TextureRegion mSoldier_1;
    private TextureRegion mSoldier_2;
    private TextureRegion mSoldier_3;

    private TextureRegion mSoldierShadow_1;
    private TextureRegion mSoldierShadow_2;
    private TextureRegion mSoldierShadow_3;

    private TextureRegion mEnemy;
    private TextureRegion mEnemyShadow;

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
        BitmapTextureAtlas tankTextureAtlas = new BitmapTextureAtlas(textureManager, 132, 107);
        BitmapTextureAtlas backgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 800);
        BitmapTextureAtlas joystickTextureAtlas = new BitmapTextureAtlas(textureManager, 352, 352);
        BitmapTextureAtlas soldierTextureAtlas = new BitmapTextureAtlas(textureManager, 132, 100);
        BitmapTextureAtlas palmTextureAtlas = new BitmapTextureAtlas(textureManager, 1000, 1000);
        BitmapTextureAtlas enemyShadowTextureAtlas = new BitmapTextureAtlas(textureManager, 490, 190);

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

        mTankBody = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tankTextureAtlas,
                baseGameActivity, "tank_body.png", 0, 0);

        mTankTower = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tankTextureAtlas,
                baseGameActivity, "tank_tower.png", 44, 0);

        mTankShadow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tankTextureAtlas,
                baseGameActivity, "tank_shadow.png", 88, 0);

        mBackGround = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas,
                baseGameActivity, "background.jpg", 0, 0);

        mJoystick = BitmapTextureAtlasTextureRegionFactory.createFromAsset(joystickTextureAtlas,
                baseGameActivity, "joystick.png", 0, 0);

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

        mPalm_1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_1.png", 0, 0);

        mPalm_2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_2.png", 200, 0);

        mPalm_3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_3.png", 400, 0);

        mPalm_4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_4.png", 600, 0);

        mPalm_5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_5.png", 800, 0);

        mPalmShadow_1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_1_shadow.png", 0, 200);

        mPalmShadow_2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_2_shadow.png", 200, 200);

        mPalmShadow_3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_3_shadow.png", 400, 200);

        mPalmShadow_4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_4_shadow.png", 600, 200);

        mPalmShadow_5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(palmTextureAtlas,
                baseGameActivity, "palm_5_shadow.png", 800, 200);

        mEnemy = BitmapTextureAtlasTextureRegionFactory.createFromAsset(enemyShadowTextureAtlas,
                baseGameActivity, "enemy.png", 0, 0);

        mEnemyShadow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(enemyShadowTextureAtlas,
                baseGameActivity, "enemy_shadow.png", 245, 0);

        helicopterTextureAtlas.load();
        bombTextureAtlas.load();
        bulletTextureAtlas.load();
        onScreenControlTextureAtlas.load();
        tankTextureAtlas.load();
        backgroundTextureAtlas.load();
        joystickTextureAtlas.load();
        palmTextureAtlas.load();
        soldierTextureAtlas.load();
        enemyShadowTextureAtlas.load();

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

    public TextureRegion getTankShadow() {
        return mTankShadow;
    }

    public TextureRegion getSoldier_1() {
        return mSoldier_1;
    }

    public TextureRegion getSoldier_2() {
        return mSoldier_2;
    }

    public TextureRegion getSoldier_3() {
        return mSoldier_3;
    }

    public TextureRegion getSoldierShadow(ITextureRegion textureRegion) {
        if (textureRegion == mSoldier_1) {
            return mSoldierShadow_1;
        }
        if (textureRegion == mSoldier_2) {
            return mSoldierShadow_2;
        }
        if (textureRegion == mSoldier_3) {
            return mSoldierShadow_3;
        }
        return null;
    }

    public TextureRegion getEnemy() {
        return mEnemy;
    }

    public TextureRegion getEnemyShadow() {
        return mEnemyShadow;
    }

    public TextureRegion getSoldier() {
        switch (new Random().nextInt(3)) {
            case 0:
                return mSoldier_1;
            case 1:
                return mSoldier_2;
            case 2:
                return mSoldier_3;
            default:
                return null;
        }
    }

    public TextureRegion getPalm() {
        switch (new Random().nextInt(5)) {
            case 0:
                return mPalm_1;
            case 1:
                return mPalm_2;
            case 2:
                return mPalm_3;
            case 3:
                return mPalm_4;
            case 4:
                return mPalm_5;
        }
        return mPalm_1;
    }

    public TextureRegion getPalmShadow(ITextureRegion textureBody) {
        if (textureBody == mPalm_1) {
            return mPalmShadow_1;
        }
        if (textureBody == mPalm_2) {
            return mPalmShadow_2;
        }
        if (textureBody == mPalm_3) {
            return mPalmShadow_3;
        }
        if (textureBody == mPalm_4) {
            return mPalmShadow_4;
        }
        if (textureBody == mPalm_5) {
            return mPalmShadow_5;
        }
        return mPalmShadow_1;
    }
}
