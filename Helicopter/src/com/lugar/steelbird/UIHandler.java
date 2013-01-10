package com.lugar.steelbird;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.lugar.steelbird.mathengine.Helicopter;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

public class UIHandler extends CameraScene {

    public UIHandler(Camera pCamera, final Helicopter mHelicopter, TextureRegion joystick,
            VertexBufferObjectManager vertexBufferObjectManager, final FPSCounter fpsCounter, Font font) {

        super(pCamera);

        setBackgroundEnabled(false);
        final Text fpsText = new Text(15, 15, font, "FPS: XXXXX", "FPS: XXXXX".length(), vertexBufferObjectManager);
        attachChild(fpsText);
        registerUpdateHandler(new TimerHandler(1 / 2.0f, true, new ITimerCallback() {
            //@Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                fpsText.setText("FPS: " + (int) fpsCounter.getFPS());
            }
        }));

        final float widthJoystick = joystick.getWidth();
        final float heightJoystick = joystick.getHeight();

        final Sprite moveSprite = new Sprite(
                0 - widthJoystick / 2 + widthJoystick / 2 * Config.SCALE,
                Config.CAMERA_HEIGHT - heightJoystick / 2 - heightJoystick / 2 * Config.SCALE,
                joystick, vertexBufferObjectManager) {

            boolean mGrabbed = false;

            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,
                    final float pTouchAreaLocalY) {

                switch(pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.mGrabbed = true;
                        break;
                    case TouchEvent.ACTION_MOVE:
                        if(this.mGrabbed) {
                            final float offsetX = getWidthScaled() / 2 - pSceneTouchEvent.getX();
                            final float offsetY = Config.CAMERA_HEIGHT - getHeightScaled() / 2 -
                                    pSceneTouchEvent.getY();
                            mHelicopter.setNextPoint(
                                    new PointF(mHelicopter.posX() - Config.CAMERA_WIDTH * offsetX,
                                            mHelicopter.posY() - Config.CAMERA_WIDTH * offsetY));
                        }
                        break;
                    case TouchEvent.ACTION_UP:
                        if(this.mGrabbed) {
                            mHelicopter.setNextPoint(mHelicopter.getPoint());
                            this.mGrabbed = false;
                        }
                        break;
                }
                return true;
            }
        };
        moveSprite.setScale(Config.SCALE);

        final Sprite rotateSprite = new Sprite(
                Config.CAMERA_WIDTH - widthJoystick / 2 - widthJoystick / 2 * Config.SCALE,
                Config.CAMERA_HEIGHT - heightJoystick / 2 - heightJoystick / 2 * Config.SCALE,
                joystick, vertexBufferObjectManager) {

            boolean mGrabbed = false;

            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,
                    final float pTouchAreaLocalY) {

                switch(pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.mGrabbed = true;
                        break;
                    case TouchEvent.ACTION_MOVE:
                        if(this.mGrabbed) {
                            final float directionX = Config.CAMERA_WIDTH - getWidthScaled() / 2 - pSceneTouchEvent.getX();
                            final float directionY = Config.CAMERA_HEIGHT - getHeightScaled() / 2 - pSceneTouchEvent.getY();
                            final float rotationAngle = MathUtils.atan2(directionY, directionX);
                            mHelicopter.setAngle(MathUtils.radToDeg(rotationAngle) - 90);
                        }
                        break;
                    case TouchEvent.ACTION_UP:
                        if(this.mGrabbed) {
                            mHelicopter.setNextPoint(mHelicopter.getPoint());
                            this.mGrabbed = false;
                        }
                        break;
                }
                return true;
            }
        };
        rotateSprite.setScale(Config.SCALE);

        attachChild(moveSprite);
        attachChild(rotateSprite);
        registerTouchArea(moveSprite);
        registerTouchArea(rotateSprite);

        setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                if (pSceneTouchEvent.getMotionEvent().getAction() != MotionEvent.ACTION_MOVE) {
                    mHelicopter.setNextPoint(mHelicopter.getPoint());
                }
                return true;
            }
        });
    }
}
