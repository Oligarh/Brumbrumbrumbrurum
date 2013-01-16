package com.lugar.steelbird.mathengine.bots;

import android.graphics.PointF;

import com.lugar.steelbird.ResourceManager;
import com.lugar.steelbird.mathengine.ArmedMovingObject;
import com.lugar.steelbird.mathengine.ConfigObject;
import com.lugar.steelbird.mathengine.Helicopter;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;

import org.andengine.util.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends ArmedMovingObject {

    private ResourceManager mResourceManager;

    private Helicopter mHelicopter;
    private SoldierType mSoldierType;

    public Soldier(PointF point, ResourceManager resourceManager, Helicopter helicopter) {
        super(point, resourceManager.getSoldier(), resourceManager);

        mResourceManager = resourceManager;

        mSoldierType = getSoldierType();

        switch (mSoldierType) {
            case SOLDIER:
                mHealth = ConfigObject.HEALTH_SOLDIER;
                mTimeRecharge = ConfigObject.RECHARGE_SOLDIER;
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

        mNextPoint = point;
        mSpeed = 0;

        mPointShadow = new PointF(mSprite.getWidthScaled() / 20, mSprite.getWidthScaled() / 20);

        mHelicopter = helicopter;
    }

    @Override
    public void tact(long now, long period) {
        super.tact(now, period);
        updateAngle();

        mSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
        mSpriteShadow.setPosition(mSprite.getX() + mPointShadow.x, mSprite.getY() + mPointShadow.y);
    }

    protected void updateAngle() {
        super.updateAngle();

        float pValueX = mHelicopter.posX();
        float pValueY = mHelicopter.posY();

        float directionX = pValueX - posX();
        float directionY = pValueY - posY();

        float rotationAngle = (float) Math.atan2(directionY, directionX);
        mAngle = MathUtils.radToDeg(rotationAngle) + 90;
    }

    @Override
    public List<FlyingObject> shoot(long now) {
        mLastShoot = now;
        List<FlyingObject> flyingObjects = new ArrayList<FlyingObject>();
        flyingObjects.add(addBullet(mAngle));
        return flyingObjects;
    }

    private SoldierType getSoldierType() {
        if (mSprite.getTextureRegion().equals(mResourceManager.getSoldier_1())) {
            return SoldierType.SOLDIER;
        }
        if (mSprite.getTextureRegion().equals(mResourceManager.getSoldier_3())) {
            return SoldierType.SOLDIER_GUN;
        }
        if (mSprite.getTextureRegion().equals(mResourceManager.getSoldier_2())) {
            return SoldierType.SOLDIER_PISTOL;
        }
        return null;
    }

    public enum SoldierType {
        SOLDIER, SOLDIER_PISTOL, SOLDIER_GUN
    }
}
