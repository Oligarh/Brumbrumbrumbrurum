package com.lugar.steelbird.mathengine.statics;

import android.graphics.PointF;
import com.lugar.steelbird.Config;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class StaticObject {

    protected PointF mPoint;
    protected PointF mPointOffset;

    protected Sprite mSprite;
    protected Sprite mSpriteShadow;

    public StaticObject(PointF point, TextureRegion textureRegion,
                        VertexBufferObjectManager vertexBufferObjectManager) {

        mPoint = point;
        mPointOffset = new PointF(textureRegion.getWidth() / 2, textureRegion.getHeight() / 2);

        mSprite = new Sprite(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y,
                textureRegion, vertexBufferObjectManager);

        mSprite.setScale(Config.SCALE);
    }

    public float posX() {
        return mPoint.x;
    }

    public float posY() {
        return mPoint.y;
    }

    public Sprite getSprite() {
        return mSprite;
    }

    public Sprite getSpriteShadow() {
        return mSpriteShadow;
    }

    public void setPoint(float newY) {
        mPoint.y = newY;
        mSprite.setPosition(mPoint.x - mPointOffset.x, mPoint.y - mPointOffset.y);
    }

    public void addShadow(TextureRegion textureRegion) {
        mSpriteShadow = new Sprite(
                mSprite.getX() + textureRegion.getWidth() * Config.SCALE / 20,
                mSprite.getY() + textureRegion.getWidth() * Config.SCALE / 20,
                textureRegion,
                mSprite.getVertexBufferObjectManager());
        mSpriteShadow.setScale(Config.SCALE);
    }
}
