package com.lugar.steelbird.mathengine;

import android.graphics.PointF;

import com.lugar.steelbird.Config;
import com.lugar.steelbird.ResourceManager;
import com.lugar.steelbird.mathengine.ammunitions.Bullet;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;

import org.andengine.entity.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Helicopter extends ArmedMovingObject {

    private WeaponType mWeaponType = WeaponType.BULLET;
    private Sprite mPropellerSprite;
    private float mOffsetBullet;
    private PlayerFrag mPlayerFrag;

    public Helicopter(PointF point, ResourceManager resourceManager) {
        super(point, resourceManager.getHelicopterBody(), resourceManager);

        mHealth = ConfigObject.DEFAULT_HEALTH_HELICOPTER;
        mTimeRecharge = ConfigObject.RECHARGE_HELICOPTER_BULLET;
        mSpeed = Config.CAMERA_HEIGHT / 4;

        mPropellerSprite = new Sprite(0, 0, resourceManager.getHelicopterPropeller(),
                resourceManager.getVertexBufferObjectManager());

        mSprite.attachChild(mPropellerSprite);
        mSprite.setScale(Config.SCALE);

        mPointShadow = new PointF(mSprite.getWidthScaled() / 5, mSprite.getWidthScaled() / 5);
        mOffsetBullet = mSprite.getWidthScaled() / 9;

        mPlayerFrag = new PlayerFrag();
    }

    public void setWeaponType(WeaponType weaponType) {
        this.mWeaponType = weaponType;
    }

    @Override
    public void tact(long now, long period) {
        super.tact(now, period);
        updateAngle();
    }

    @Override
    public List<FlyingObject> shoot(long now) {
        List<FlyingObject> flyingObjects = new ArrayList<FlyingObject>();
        switch (mWeaponType) {
            case BULLET:
                flyingObjects.add(addLeftBullet());
                flyingObjects.add(addRightBullet());
                break;
            case BOMB:
                flyingObjects.add(addBomb(mAngle));
                break;
        }
        mLastShoot = now;
        return flyingObjects;
    }

    public PointF getPoint() {
        return mPoint;
    }

    public void setNextPoint(PointF point) {
        mNextPoint = point;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    protected void updateAngle() {
        if (mPropellerSprite.getRotation() > 360) {
            mPropellerSprite.setRotation(mPropellerSprite.getRotation() % 360);
        }
        mPropellerSprite.setRotation(mPropellerSprite.getRotation() + 20);
        super.updateAngle();
    }

    public Bullet addLeftBullet() {
        float sXLeft = (float) (posX() - (mOffsetBullet * Math.cos(mAngle * DEG_TO_PI)));
        float sYLeft = (float) (posY() - (mOffsetBullet * Math.sin(mAngle * DEG_TO_PI)));
        return addBullet(sXLeft, sYLeft, mAngle);
    }

    public Bullet addRightBullet() {
        float sXRight = (float) (posX() + (mOffsetBullet * Math.cos(mAngle * DEG_TO_PI)));
        float sYRight = (float) (posY() + (mOffsetBullet * Math.sin(mAngle * DEG_TO_PI)));
        return addBullet(sXRight, sYRight, mAngle);
    }

    enum WeaponType {
        BULLET, BOMB
    }

    public void addFrag() {
        mPlayerFrag.addFrag();
    }

    public void addDamage(float damage) {
        mPlayerFrag.addDamage(damage);
    }

    public void carriedDamage(float damage) {
        mPlayerFrag.addCarried(damage);
    }

    public PlayerFrag getPlayerFrag() {
        return mPlayerFrag;
    }
}
