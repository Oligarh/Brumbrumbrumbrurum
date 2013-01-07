package com.lugar.steelbird.mathengine.ammunitions;

import android.graphics.PointF;
import com.lugar.steelbird.mathengine.ConfigObject;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Bullet extends FlyingObject {

    public Bullet(PointF point, PointF nextPoint, float angle,
                  TextureRegion mainTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
        super(point, nextPoint, angle, mainTextureRegion, vertexBufferObjectManager);
        mDamage = ConfigObject.DAMAGE_POOL;
        mSpeed = ConfigObject.SPEED_POOL;
    }
}
