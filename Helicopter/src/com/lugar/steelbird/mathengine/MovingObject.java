package com.lugar.steelbird.mathengine;

import android.graphics.PointF;

import com.lugar.steelbird.mathengine.statics.StaticObject;

import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class MovingObject extends StaticObject {

    protected PointF mNextPoint;
    protected int mSpeed;
    protected float mAngle;

    public MovingObject(PointF point, TextureRegion textureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
        super(point, textureRegion, vertexBufferObjectManager);
        mNextPoint = point;
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

    public abstract void tact(long now, long period);
}
