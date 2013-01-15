package com.lugar.steelbird.mathengine.statics;

import android.graphics.PointF;

import com.lugar.steelbird.Config;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Enemy extends StaticObject {

    protected Sprite mSpriteShadow;
    protected float mAngle;

    public Enemy(PointF point, TextureRegion textureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
        super(point, textureRegion, vertexBufferObjectManager);
        mAngle = mRandom.nextInt(360);
        mSprite.setRotation(mAngle);
        mSprite.setScale(Config.SCALE);
    }

    public Sprite getSpriteShadow() {
        return mSpriteShadow;
    }

    public void addShadow(TextureRegion textureRegion) {
        mSpriteShadow = new Sprite(
                mSprite.getX() + textureRegion.getWidth() * Config.SCALE / 20,
                mSprite.getY() + textureRegion.getWidth() * Config.SCALE / 20,
                textureRegion,
                mSprite.getVertexBufferObjectManager());
        mSpriteShadow.setScale(Config.SCALE);
        mSpriteShadow.setRotation(mAngle);
    }
}
