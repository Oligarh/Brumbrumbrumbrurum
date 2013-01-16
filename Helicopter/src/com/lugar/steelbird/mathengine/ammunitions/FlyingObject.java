package com.lugar.steelbird.mathengine.ammunitions;

import android.graphics.PointF;
import com.lugar.steelbird.Config;
import com.lugar.steelbird.mathengine.MovingObject;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

public class FlyingObject extends MovingObject {

    protected int mDamage;

    public FlyingObject(PointF point, PointF nextPoint, float angle,
            TextureRegion mainTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
        super(point, mainTextureRegion, vertexBufferObjectManager);
        mNextPoint = nextPoint;
        mSprite.setRotation(angle);
        mSprite.setScale(Config.SCALE);
    }

    public int getDamage() {
        return mDamage;
    }

    @Override
    public void tact(long now, long period) {
        float distance = (float) period / 1000 * mSpeed;
        float nextStep = MathUtils.distance(mPoint.x, mPoint.y, mNextPoint.x, mNextPoint.y);
        float m = nextStep - distance;
        float x = (m * mPoint.x + distance * mNextPoint.x) / nextStep;
        float y = (m * mPoint.y + distance * mNextPoint.y) / nextStep;
        mPoint.x = x;
        mPoint.y = y;
        mSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
    }
}
