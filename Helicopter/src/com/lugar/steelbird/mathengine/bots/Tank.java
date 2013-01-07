package com.lugar.steelbird.mathengine.bots;

import android.graphics.PointF;
import com.lugar.steelbird.ResourceManager;
import com.lugar.steelbird.mathengine.ArmedMovingObject;
import com.lugar.steelbird.mathengine.ConfigObject;
import com.lugar.steelbird.mathengine.Helicopter;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Tank extends ArmedMovingObject {

    private Sprite mTower;
    private Helicopter mHelicopter;

    private float mAngleTower;

    public Tank(PointF point, ResourceManager resourceManager, Helicopter helicopter) {
        super(point, resourceManager.getTankBody(), resourceManager);

        mHealth = ConfigObject.HEALTH_TANK;
        mTimeRecharge = ConfigObject.RECHARGE_TANK;

        mHelicopter = helicopter;

        mTower = new Sprite(0, 0, resourceManager.getTankTower(), resourceManager.getVertexBufferObjectManager());
        mMainSprite.attachChild(mTower);
    }

    @Override
    public void tact(long now, long period) {
        super.tact(now, period);
        updateTowerAngle();
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
        mTower.setRotation(mAngleTower);
    }
}
