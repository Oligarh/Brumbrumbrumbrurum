package com.lugar.steelbird.mathengine.bots;

import android.graphics.PointF;

import com.lugar.steelbird.Config;
import com.lugar.steelbird.ResourceManager;
import com.lugar.steelbird.mathengine.ArmedMovingObject;
import com.lugar.steelbird.mathengine.ConfigObject;
import com.lugar.steelbird.mathengine.Helicopter;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends ArmedMovingObject {

    private ResourceManager mResourceManager;

    private Helicopter mHelicopter;
    private SoldierType mSoldierType;

    public Soldier(PointF point, PointF nextPoint, ResourceManager resourceManager, Helicopter helicopter) {
        super(point, resourceManager.getSoldier(), resourceManager);

        mResourceManager = resourceManager;

        mNextPoint = nextPoint;
        mSpeed = 2;

        mPointShadow = new PointF(mMainSprite.getWidthScaled() / 20, mMainSprite.getWidthScaled() / 20);

        mSoldierType = getSoldierType();

        switch (mSoldierType) {
            case SOLDIER_LEFT:
            case SOLDIER_RIGHT:
                mHealth = ConfigObject.HEALTH_SOLDIER;
                mTimeRecharge = ConfigObject.RECHARGE_SOLDIER_LEFT_RIGHT;
                break;
            case SOLDIER_PISTOL:
                mHealth = ConfigObject.HEALTH_SOLDIER;
                mTimeRecharge = ConfigObject.RECHARGE_SOLDIER_PISTOL;
                break;
            case SOLDIER_GUN:
                mHealth = ConfigObject.HEALTH_SOLDIER;
                mTimeRecharge = ConfigObject.RECHARGE_SOLDIER_GUN;
                break;
        }

        mHelicopter = helicopter;
    }

    @Override
    public void tact(long now, long period) {
        super.tact(now, period);
        updateAngle();

        mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
        mSpriteShadow.setPosition(mMainSprite.getX() + mPointShadow.x, mMainSprite.getY() + mPointShadow.y);
    }

    private void updateAngle() {

        if (mMainSprite.getRotation() > 360) {
            mMainSprite.setRotation(mMainSprite.getRotation() % 360);
            mSpriteShadow.setRotation(mSpriteShadow.getRotation() % 360);
        }

        float pValueX = mHelicopter.posX();
        float pValueY = mHelicopter.posY();

        float directionX = pValueX - posX();
        float directionY = pValueY - posY();

        float rotationAngle = (float) Math.atan2(directionY, directionX);
        mAngle = MathUtils.radToDeg(rotationAngle) + 90;

        mMainSprite.setRotation(mAngle);
        mSpriteShadow.setRotation(mAngle);
    }

    @Override
    public List<FlyingObject> shoot(long now) {
        mLastShoot = now;
        List<FlyingObject> flyingObjects = new ArrayList<FlyingObject>();
        flyingObjects.add(addBullet(mAngle));
        return flyingObjects;
    }

    @Override
    public void addShadow(TextureRegion textureRegion) {
        mSpriteShadow = new Sprite(
                mMainSprite.getX() + mPointShadow.x,
                mMainSprite.getY() + mPointShadow.y,
                textureRegion,
                mMainSprite.getVertexBufferObjectManager());
        mSpriteShadow.setScale(Config.SCALE);
    }

    private SoldierType getSoldierType() {
        if (mMainSprite.getTextureRegion().equals(mResourceManager.getSoldier_1())) {
            return SoldierType.SOLDIER_LEFT;
        }
        if (mMainSprite.getTextureRegion().equals(mResourceManager.getSoldier_2())) {
            return SoldierType.SOLDIER_RIGHT;
        }
        if (mMainSprite.getTextureRegion().equals(mResourceManager.getSoldier_3())) {
            return SoldierType.SOLDIER_GUN;
        }
        if (mMainSprite.getTextureRegion().equals(mResourceManager.getSoldier_4())) {
            return SoldierType.SOLDIER_PISTOL;
        }
        return null;
    }

    public enum SoldierType {
        SOLDIER_LEFT, SOLDIER_RIGHT, SOLDIER_PISTOL, SOLDIER_GUN
    }

}
