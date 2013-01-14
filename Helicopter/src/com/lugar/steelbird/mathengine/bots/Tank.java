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

public class Tank extends ArmedMovingObject {

    private Sprite mTower;
    private Helicopter mHelicopter;

    private float mAngleTower;

    public Tank(PointF point, PointF nextPoint, ResourceManager resourceManager, Helicopter helicopter) {
        super(point, resourceManager.getTankBody(), resourceManager);

        mNextPoint = nextPoint;
        mSpeed = 2;

        mPointShadow = new PointF(mMainSprite.getWidthScaled() / 20, mMainSprite.getWidthScaled() / 20);

        mHealth = ConfigObject.HEALTH_TANK;
        mTimeRecharge = ConfigObject.RECHARGE_TANK;

        mHelicopter = helicopter;

        mTower = new Sprite(0, 0, resourceManager.getTankTower(), resourceManager.getVertexBufferObjectManager());
        mMainSprite.attachChild(mTower);
    }

    @Override
    public void tact(long now, long period) {
        super.tact(now, period);
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
        updateTowerAngle();
    }

    private void updateAngle() {

        if (mMainSprite.getRotation() > 360) {
            mMainSprite.setRotation(mMainSprite.getRotation() % 360);
            mSpriteShadow.setRotation(mSpriteShadow.getRotation() % 360);
        }

        final float directionX = mPoint.x - mNextPoint.x;
        final float directionY = mPoint.y - mNextPoint.y;
        final float rotationAngle = MathUtils.atan2(directionY, directionX);
        mAngle = MathUtils.radToDeg(rotationAngle) - 90;

        mMainSprite.setRotation(mAngle);
        mSpriteShadow.setRotation(mAngle);
    }

    @Override
    public List<FlyingObject> shoot(long now) {
        mLastShoot = now;
        List<FlyingObject> flyingObjects = new ArrayList<FlyingObject>();
        flyingObjects.add(addBomb(mAngleTower));
        return flyingObjects;
    }

    private void updateTowerAngle() {
        float pValueX = mHelicopter.posX();
        float pValueY = mHelicopter.posY();

        float directionX = pValueX - posX();
        float directionY = pValueY - posY();

        float rotationAngle = (float) Math.atan2(directionY, directionX);
        mAngleTower = MathUtils.radToDeg(rotationAngle) + 90;
        mTower.setRotation(mAngleTower - mAngle);
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
}
