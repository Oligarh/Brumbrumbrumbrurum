package com.lugar.steelbird.mathengine;

import android.graphics.PointF;
import com.lugar.steelbird.Config;
import com.lugar.steelbird.ResourceManager;
import com.lugar.steelbird.mathengine.ammunitions.Bullet;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class Helicopter extends ArmedMovingObject {

    private WeaponType mWeaponType = WeaponType.BULLET;

    private Sprite mPropellerSprite;

    private float mOffsetBullet;

    public Helicopter(PointF point, ResourceManager resourceManager) {
        super(point, resourceManager.getHelicopterBody(), resourceManager);

        mSpeed = 80;
        mPropellerSprite = new Sprite(0, 0, resourceManager.getHelicopterPropeller(),
                resourceManager.getVertexBufferObjectManager());

        mPointShadow = new PointF(mMainSprite.getWidthScaled() / 5, mMainSprite.getWidthScaled() / 5);

        mMainSprite.attachChild(mPropellerSprite);
        mMainSprite.setScale(Config.SCALE);
        mOffsetBullet = mMainSprite.getWidthScaled() / 9;

        mTimeRecharge = ConfigObject.RECHARGE_HELICOPTER_BULLET;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.mWeaponType = weaponType;
    }

    @Override
    public void tact(long now, long period) {
        updateAngle();

        if (distance(mPoint.x, mPoint.y, mNextPoint.x, mNextPoint.y) > 10) {
            float distance = (float) period / 1000 * mSpeed;
            float nextStep = distance(mPoint.x, mPoint.y, mNextPoint.x, mNextPoint.y);
            float m = nextStep - distance;
            float x = (m * mPoint.x + distance * mNextPoint.x) / nextStep;
            float y = (m * mPoint.y + distance * mNextPoint.y) / nextStep;

            mPoint.x = x;
            mPoint.y = y;
        }

        mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
        mSpriteShadow.setPosition(mMainSprite.getX() + mPointShadow.x, mMainSprite.getY() + mPointShadow.y);
//        mPoint.x = mMainSprite.getX() + mPointOffset.x;
//        mPoint.y = mMainSprite.getY() + mPointOffset.y;
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

    private void updateAngle() {
        if (mPropellerSprite.getRotation() > 360) {
            mPropellerSprite.setRotation(mPropellerSprite.getRotation() % 360);
        }

        if (mMainSprite.getRotation() > 360) {
            mMainSprite.setRotation(mMainSprite.getRotation() % 360);
            mSpriteShadow.setRotation(mSpriteShadow.getRotation() % 360);
        }

        mPropellerSprite.setRotation(mPropellerSprite.getRotation() + 20);
        mMainSprite.setRotation(mAngle);
        mSpriteShadow.setRotation(mAngle);
//        mAngle = mMainSprite.getRotation();
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

    @Override
    public void addShadow(TextureRegion textureRegion) {
        mSpriteShadow = new Sprite(
                mMainSprite.getX() + mPointShadow.x,
                mMainSprite.getY() + mPointShadow.y,
                textureRegion,
                mMainSprite.getVertexBufferObjectManager());
        mSpriteShadow.setScale(Config.SCALE);
    }

    enum WeaponType {
        BULLET, BOMB
    }
}
