package com.lugar.steelbird.mathengine.bots;

import android.graphics.PointF;

import com.lugar.steelbird.Config;
import com.lugar.steelbird.mathengine.ArmedMovingObject;
import com.lugar.steelbird.mathengine.ConfigObject;
import com.lugar.steelbird.mathengine.Helicopter;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;

import com.lugar.steelbird.resources.ResourceManager;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Tank extends ArmedMovingObject {

    private Sprite mTower;
    private float mAngleTower;

    private Helicopter mHelicopter;

    public Tank(PointF point, PointF nextPoint, ResourceManager resourceManager, Helicopter helicopter) {
        super(point, resourceManager.getTankBody(), resourceManager);

        mNextPoint = nextPoint;

        mSpeed = Config.CAMERA_HEIGHT / 100;
        mHealth = ConfigObject.HEALTH_TANK;
        mTimeRecharge = ConfigObject.RECHARGE_TANK;

        mPointShadow = new PointF(mSprite.getWidthScaled() / 20, mSprite.getWidthScaled() / 20);

        mHelicopter = helicopter;

        mTower = new Sprite(0, 0, resourceManager.getTankTower(), resourceManager.getVertexBufferObjectManager());
        mSprite.attachChild(mTower);
    }

    @Override
    public void tact(long now, long period) {
        super.tact(now, period);
        updateAngle();
    }

    protected void updateAngle() {
        final float directionX = mPoint.x - mNextPoint.x;
        final float directionY = mPoint.y - mNextPoint.y;
        final float rotationAngle = MathUtils.atan2(directionY, directionX);
        mAngle = MathUtils.radToDeg(rotationAngle) - 90;
        updateTowerAngle();
        super.updateAngle();
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
}
