package com.lugar.steelbird.mathengine;

import android.graphics.PointF;
import android.util.FloatMath;
import com.lugar.steelbird.Config;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Random;

public abstract class MovingObject {

    protected PointF mPoint;
    protected PointF mNextPoint;
    protected PointF mPointOffset;

    protected Sprite mMainSprite;

    protected int mSpeed;
    protected float mAngle;
    protected int mHealth;

    protected boolean mAlive;

    protected Random mRandom = new Random();

    public MovingObject(PointF point, TextureRegion mainTextureRegion,
                        VertexBufferObjectManager vertexBufferObjectManager) {

        mPoint = point;
        mNextPoint = point;

        mPointOffset = new PointF(mainTextureRegion.getWidth() / 2, mainTextureRegion.getHeight() / 2);

        mMainSprite = new Sprite(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y,
                mainTextureRegion, vertexBufferObjectManager);

        mMainSprite.setScale(Config.SCALE);
    }

    protected void damage(float health) {
        mHealth -= health;
        if (mHealth <= 0) {
            mAlive = false;
        }
    }

    public boolean isAlive() {
        return mAlive;
    }

    public float posX() {
        return mPoint.x;
    }

    public float posY() {
        return mPoint.y;
    }

    public void setPoint(PointF mPoint) {
        this.mPoint = mPoint;
    }

    public void setY(float y) {
        mPoint.y = y;
    }

    public float getAngle() {
        return mAngle;
    }

    public Sprite getMainSprite() {
        return mMainSprite;
    }

    public abstract void tact(long now, long period);
//    {
//        float distance = (float) period / 1000 * mSpeed;
//        float nextStep = distance(mPoint.x, mPoint.y, mNextPoint.x, mNextPoint.y);
//        float m = nextStep - distance;
//        float x = (m * mPoint.x + distance * mNextPoint.x) / nextStep;
//        float y = (m * mPoint.y + distance * mNextPoint.y) / nextStep;
//
//        mPoint.x = x;
//        mPoint.y = y;
//
//        mMainSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
//    }

    public static float distance(float x, float y, float x2, float y2) {
        float dx = x - x2;
        float dy = y - y2;
        return FloatMath.sqrt(dx * dx + dy * dy);
    }
}
