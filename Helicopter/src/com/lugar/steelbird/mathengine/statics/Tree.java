package com.lugar.steelbird.mathengine.statics;

import android.graphics.PointF;
import com.lugar.steelbird.Config;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Random;

public class Tree extends StaticObject {

    protected Sprite mSpriteShadow;
    protected Random mRandom = new Random();
    protected float mAngle;
    protected float mScale;

    public Tree(PointF point, TextureRegion textureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
        super(point, textureRegion, vertexBufferObjectManager);
        mAngle = mRandom.nextInt(360);
        mScale = Config.SCALE - Config.SCALE / 4 + mRandom.nextFloat() * Config.SCALE / 2;
        mSprite.setRotation(mAngle);
        mSprite.setScale(mScale);
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
        mSpriteShadow.setScale(mScale);
        mSpriteShadow.setRotation(mAngle);
    }
}
