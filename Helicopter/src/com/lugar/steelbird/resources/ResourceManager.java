package com.lugar.steelbird.resources;

import com.lugar.steelbird.LocationType;
import com.lugar.steelbird.helicopter.HelicopterType;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import java.util.Random;

public class ResourceManager {

    private ResourceManagerArmed mResourceManagerArmed;
    private ResourceManagerStatic mResourceManagerStatic;
    private ResourceManagerHelicopter mResourceManagerHelicopter;

    private TextureRegion mBomb;
    private TextureRegion mBullet;

    private TextureRegion mOnScreenControlBaseTextureRegion;
    private TextureRegion mOnScreenControlKnobTextureRegion;

    private TextureRegion mEnemy;
    private TextureRegion mEnemyShadow;

    private TextureRegion mBackGround;

    private TextureRegion mJoystick;

    private Font mFont;

    private VertexBufferObjectManager mVertexBufferObjectManager;

    public ResourceManager(BaseGameActivity baseGameActivity, LocationType locationType, HelicopterType helicopterType) {

        mVertexBufferObjectManager = baseGameActivity.getVertexBufferObjectManager();

        TextureManager textureManager = baseGameActivity.getTextureManager();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        switch (locationType) {
            case JUNGLE:
                mResourceManagerArmed = new ResourceManagerArmedJungle(baseGameActivity);
                mResourceManagerStatic = new ResourceManagerStaticJungle(baseGameActivity);
                break;
        }

        mResourceManagerHelicopter = new ResourceManagerHelicopter(baseGameActivity, helicopterType);

        BitmapTextureAtlas bombTextureAtlas = new BitmapTextureAtlas(textureManager, 13, 45);
        BitmapTextureAtlas bulletTextureAtlas = new BitmapTextureAtlas(textureManager, 11, 31);
        BitmapTextureAtlas onScreenControlTextureAtlas = new BitmapTextureAtlas(textureManager, 256, 128);
        BitmapTextureAtlas joystickTextureAtlas = new BitmapTextureAtlas(textureManager, 352, 352);
        BitmapTextureAtlas enemyShadowTextureAtlas = new BitmapTextureAtlas(textureManager, 490, 190);
        BitmapTextureAtlas backgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 800);

        mBomb = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bombTextureAtlas,
                baseGameActivity, "bomb.png", 0, 0);

        mBullet = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bulletTextureAtlas,
                baseGameActivity, "bullet.png", 0, 0);

        mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                onScreenControlTextureAtlas, baseGameActivity, "onscreen_control_base.png", 0, 0);

        mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                onScreenControlTextureAtlas, baseGameActivity, "onscreen_control_knob.png", 128, 0);

        mBackGround = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas,
                baseGameActivity, "background.jpg", 0, 0);

        mJoystick = BitmapTextureAtlasTextureRegionFactory.createFromAsset(joystickTextureAtlas,
                baseGameActivity, "joystick.png", 0, 0);

        mEnemy = BitmapTextureAtlasTextureRegionFactory.createFromAsset(enemyShadowTextureAtlas,
                baseGameActivity, "enemy.png", 0, 0);

        mEnemyShadow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(enemyShadowTextureAtlas,
                baseGameActivity, "enemy_shadow.png", 245, 0);

        bombTextureAtlas.load();
        bulletTextureAtlas.load();
        onScreenControlTextureAtlas.load();
        backgroundTextureAtlas.load();
        joystickTextureAtlas.load();

        enemyShadowTextureAtlas.load();

        FontFactory.setAssetBasePath("fonts/");
        ITexture fontTexture = new BitmapTextureAtlas(baseGameActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

        mFont = FontFactory.createFromAsset(baseGameActivity.getFontManager(), fontTexture,
                baseGameActivity.getAssets(), "font.ttf", 16, true, Color.WHITE_ABGR_PACKED_INT);

        mFont.load();
    }

    public TextureRegion getHelicopterBody() {
        return mResourceManagerHelicopter.getHelicopterBody();
    }

    public TextureRegion getHelicopterPropeller() {
        return mResourceManagerHelicopter.getHelicopterPropeller();
    }

    public TextureRegion getHelicopterShadow() {
        return mResourceManagerHelicopter.getHelicopterShadow();
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
        return mResourceManagerArmed.getTankBody();
    }

    public TextureRegion getTankTower() {
        return mResourceManagerArmed.getTankTower();
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
        return mResourceManagerArmed.getTankShadow();
    }

    public TextureRegion getSoldier_1() {
        return mResourceManagerArmed.getSoldier_1();
    }

    public TextureRegion getSoldier_2() {
        return mResourceManagerArmed.getSoldier_2();
    }

    public TextureRegion getSoldier_3() {
        return mResourceManagerArmed.getSoldier_3();
    }

    public TextureRegion getSoldierShadow(ITextureRegion textureRegion) {
        if (textureRegion == mResourceManagerArmed.getSoldier_1()) {
            return mResourceManagerArmed.getSoldierShadow_1();
        }
        if (textureRegion == mResourceManagerArmed.getSoldier_2()) {
            return mResourceManagerArmed.getSoldierShadow_2();
        }
        if (textureRegion == mResourceManagerArmed.getSoldier_3()) {
            return mResourceManagerArmed.getSoldierShadow_3();
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
                return mResourceManagerArmed.getSoldier_1();
            case 1:
                return mResourceManagerArmed.getSoldier_2();
            case 2:
                return mResourceManagerArmed.getSoldier_3();
            default:
                return null;
        }
    }

    public TextureRegion getTree() {
        switch (new Random().nextInt(5)) {
            case 0:
                return mResourceManagerStatic.getTree_1();
            case 1:
                return mResourceManagerStatic.getTree_2();
            case 2:
                return mResourceManagerStatic.getTree_3();
            case 3:
                return mResourceManagerStatic.getTree_4();
            case 4:
                return mResourceManagerStatic.getTree_5();
        }
        return mResourceManagerStatic.getTree_1();
    }

    public TextureRegion getTreeShadow(ITextureRegion textureBody) {
        if (textureBody == mResourceManagerStatic.getTree_1()) {
            return mResourceManagerStatic.getTreeShadow_1();
        }
        if (textureBody == mResourceManagerStatic.getTree_2()) {
            return mResourceManagerStatic.getTreeShadow_2();
        }
        if (textureBody == mResourceManagerStatic.getTree_3()) {
            return mResourceManagerStatic.getTreeShadow_3();
        }
        if (textureBody == mResourceManagerStatic.getTree_4()) {
            return mResourceManagerStatic.getTreeShadow_4();
        }
        if (textureBody == mResourceManagerStatic.getTree_5()) {
            return mResourceManagerStatic.getTreeShadow_5();
        }
        return mResourceManagerStatic.getTreeShadow_1();
    }
}