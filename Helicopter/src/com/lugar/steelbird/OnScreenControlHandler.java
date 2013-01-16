package com.lugar.steelbird;

import com.lugar.steelbird.mathengine.Helicopter;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.util.math.MathUtils;

import javax.microedition.khronos.opengles.GL10;

public class OnScreenControlHandler {

    public OnScreenControlHandler(GameActivity gameActivity, final Helicopter helicopter, ResourceManager resourceManager) {

        final PhysicsHandler physicsHandler = new PhysicsHandler(helicopter.getSprite());
        helicopter.getSprite().registerUpdateHandler(physicsHandler);

        final float x1 = 0;
        final float y = Config.CAMERA_HEIGHT - resourceManager.getOnScreenControlBaseTextureRegion().getHeight();
        final AnalogOnScreenControl velocityOnScreenControl = new AnalogOnScreenControl(x1, y, gameActivity.getCamera(),
                resourceManager.getOnScreenControlBaseTextureRegion(), resourceManager.getOnScreenControlKnobTextureRegion(),
                0.1f, gameActivity.getVertexBufferObjectManager(), new AnalogOnScreenControl.IAnalogOnScreenControlListener() {

            @Override
            public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
                physicsHandler.setVelocity(pValueX * 100, pValueY * 100);
            }

            @Override
            public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {

            }
        });
        velocityOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        velocityOnScreenControl.getControlBase().setAlpha(0.5f);

        gameActivity.getScene().setChildScene(velocityOnScreenControl);

        /* Rotation control (right). */
        final float x2 = Config.CAMERA_WIDTH - resourceManager.getOnScreenControlBaseTextureRegion().getWidth();
        final AnalogOnScreenControl rotationOnScreenControl = new AnalogOnScreenControl(x2, y, gameActivity.getCamera(),
                resourceManager.getOnScreenControlBaseTextureRegion(),
                resourceManager.getOnScreenControlKnobTextureRegion(), 0.1f,
                gameActivity.getVertexBufferObjectManager(), new AnalogOnScreenControl.IAnalogOnScreenControlListener() {
            @Override
            public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX,
                    final float pValueY) {
                if(pValueX == x1 && pValueY == x1) {
                    helicopter.getSprite().setRotation(x1);
                } else {
                    helicopter.getSprite().setRotation(MathUtils.radToDeg((float) Math.atan2(pValueX, -pValueY)));
                }
            }

            @Override
            public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {

            }
        });
        rotationOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        rotationOnScreenControl.getControlBase().setAlpha(0.5f);

        velocityOnScreenControl.setChildScene(rotationOnScreenControl);
    }
}
