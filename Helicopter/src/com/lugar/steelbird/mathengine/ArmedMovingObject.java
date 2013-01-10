package com.lugar.steelbird.mathengine;

import android.graphics.PointF;
import com.lugar.steelbird.Config;
import com.lugar.steelbird.ResourceManager;
import com.lugar.steelbird.mathengine.ammunitions.Bomb;
import com.lugar.steelbird.mathengine.ammunitions.Bullet;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;
import org.andengine.opengl.texture.region.TextureRegion;

import java.util.List;

public abstract class ArmedMovingObject extends MovingObject {

    protected static final float PI = (float) Math.PI;
    protected static final float DEG_TO_PI = (float) (Math.PI / 180.0);

    protected long mLastShoot;
    protected int mTimeRecharge;

    protected ResourceManager mResourceManager;

    public ArmedMovingObject(PointF point, TextureRegion mainTextureRegion, ResourceManager resourceManager) {
        super(point, mainTextureRegion, resourceManager.getVertexBufferObjectManager());
        mResourceManager = resourceManager;
        mAlive = true;
    }

    @Override
    public void tact(long now, long period) {

    }

    public boolean canShoot(long now, float cameraMinY) {
        return now - mLastShoot > mTimeRecharge && posY() > cameraMinY;
    }

    public float getHealth() {
        return mHealth;
    }

    public abstract List<FlyingObject> shoot(long now);

    public Bomb addBomb(float angle) {
        float tX = (float) (posX() + (Config.CAMERA_WIDTH * Math.cos(angle * DEG_TO_PI - PI / 2)));
        float tY = (float) (posY() + (Config.CAMERA_WIDTH * Math.sin(angle * DEG_TO_PI - PI / 2)));
        return new Bomb(new PointF(posX(), posY()), new PointF(tX, tY), angle,
                mResourceManager.getBomb(), mResourceManager.getVertexBufferObjectManager());
    }

    public Bullet addBullet(float angle) {
        return addBullet(posX(), posY(), angle);
    }

    public Bullet addBullet(float sX, float sY, float angle) {
        float tX = (float) (sX + (Config.CAMERA_WIDTH * Math.cos(angle * DEG_TO_PI - PI / 2)));
        float tY = (float) (sY + (Config.CAMERA_WIDTH * Math.sin(angle * DEG_TO_PI - PI / 2)));
        return new Bullet(new PointF(sX, sY), new PointF(tX, tY), angle, mResourceManager.getBullet(),
                mResourceManager.getVertexBufferObjectManager());
    }
}
