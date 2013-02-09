package com.lugar.steelbird.mathengine;

import android.graphics.PointF;

import com.lugar.steelbird.Config;
import com.lugar.steelbird.mathengine.ammunitions.Bomb;
import com.lugar.steelbird.mathengine.ammunitions.Bullet;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;

import com.lugar.steelbird.resources.ResourceManager;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.math.MathUtils;

import java.util.List;

public abstract class ArmedMovingObject extends MovingObject {

    protected static final float PI = (float) Math.PI;
    protected static final float DEG_TO_PI = (float) (Math.PI / 180.0);

    protected long mLastShoot;
    protected int mTimeRecharge;

    protected Sprite mSpriteShadow;
    protected PointF mPointShadow;

    protected int mHealth;
    protected boolean mAlive;

    protected ResourceManager mResourceManager;

    public ArmedMovingObject(PointF point, TextureRegion textureRegion, ResourceManager resourceManager) {
        super(point, textureRegion, resourceManager.getVertexBufferObjectManager());
        mResourceManager = resourceManager;
        mAlive = true;
    }

    public abstract List<FlyingObject> shoot(long now);

    public void addShadow(TextureRegion textureRegion) {
        mSpriteShadow = new Sprite(
                mSprite.getX() + mPointShadow.x,
                mSprite.getY() + mPointShadow.y,
                textureRegion,
                mSprite.getVertexBufferObjectManager());
        mSpriteShadow.setScale(Config.SCALE);
    }

    @Override
    public void tact(long now, long period) {
        final float nextStep = MathUtils.distance(mPoint.x, mPoint.y, mNextPoint.x, mNextPoint.y);
        if (nextStep > 10) {
            float distance = (float) period / 1000 * mSpeed;
            float m = nextStep - distance;
            float x = (m * mPoint.x + distance * mNextPoint.x) / nextStep;
            float y = (m * mPoint.y + distance * mNextPoint.y) / nextStep;

            mPoint.x = x;
            mPoint.y = y;
        }

        mSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
        mSpriteShadow.setPosition(mSprite.getX() + mPointShadow.x, mSprite.getY() + mPointShadow.y);
    }

    protected void updateAngle() {
        if (mSprite.getRotation() > 360) {
            mSprite.setRotation(mSprite.getRotation() % 360);
            mSpriteShadow.setRotation(mSpriteShadow.getRotation() % 360);
        }

        mSprite.setRotation(mAngle);
        mSpriteShadow.setRotation(mAngle);
    }

    public boolean canShoot(long now, float cameraMinY) {
        return now - mLastShoot > mTimeRecharge && posY() > cameraMinY;
    }

    protected void damage(float health) {
        mHealth -= health;
        if (mHealth <= 0) {
            mAlive = false;
        }
    }

    public Bomb addBomb(float angle) {
        float tX = (float) (posX() + (3 * Config.CAMERA_WIDTH * Math.cos(angle * DEG_TO_PI - PI / 2)));
        float tY = (float) (posY() + (3 * Config.CAMERA_WIDTH * Math.sin(angle * DEG_TO_PI - PI / 2)));
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

    public Sprite getSpriteShadow() {
        return mSpriteShadow;
    }

    public float getHealth() {
        return mHealth;
    }

    public boolean isAlive() {
        return mAlive;
    }
}
