package com.lugar.steelbird;

import android.graphics.PointF;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lugar.steelbird.mathengine.MathEngine;
import com.lugar.steelbird.ui.widjets.ControlImageView;
import com.lugar.steelbird.ui.widjets.RotateControl;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.controller.MultiTouchController;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.math.MathUtils;

public class GameActivity extends BaseGameActivity {

    private MathEngine mMathEngine;
    private Scene mScene;
    private Camera mCamera;
    private ResourceManager mResourceManager;

//    private PointF mControlRotatingCenter;
//    private PointF mControlMovingCenter;
//
//    private float mRadius;
//
//    private int mMovingID = -1;
//    private int mRotatingID = -1;

    @Override
    public EngineOptions onCreateEngineOptions() {

        Display display = getWindowManager().getDefaultDisplay();
        Config.SCALE = (float) display.getWidth() / Config.ETALON_WIDTH;
        Config.CAMERA_WIDTH = display.getWidth();
        Config.CAMERA_HEIGHT = display.getHeight();

//        mRadius = Config.CAMERA_HEIGHT * 0.15f;
//
//        mControlMovingCenter = new PointF(mRadius, Config.CAMERA_HEIGHT - mRadius);
//        mControlRotatingCenter = new PointF(Config.CAMERA_WIDTH - mRadius, Config.CAMERA_HEIGHT - mRadius);

        mCamera = new Camera(0, 0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT);

        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT), mCamera);
//        engineOptions.getTouchOptions().setNeedsMultiTouch(true);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        mResourceManager = new ResourceManager(this);

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

        mScene = new Scene();
        mScene.setBackground(new Background(0.29f, 0.31f, 0.37f));

        mEngine.setTouchController(new MultiTouchController());

        mMathEngine = new MathEngine(this);
        mMathEngine.start();


//        final View view = getLayoutInflater().inflate(R.layout.ui_layout, null);
//        final ControlImageView joystikMove = (ControlImageView) view.findViewById(R.id.joystik_move);
//        final RotateControl joystikAngle = (RotateControl) view.findViewById(R.id.joystik_angle);

//        joystikMove.setHelicopter(mMathEngine.getHelicopter());
//
//        joystikAngle.setHelicopter(mMathEngine.getHelicopter());

//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                final int action = motionEvent.getAction();
//                switch (action & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_DOWN:
//                    case MotionEvent.ACTION_POINTER_DOWN:
//                        if (motionEvent.getX() > mControlMovingCenter.x - mRadius &&
//                                motionEvent.getX() < mControlMovingCenter.x + mRadius &&
//                                motionEvent.getY() > mControlMovingCenter.y - mRadius &&
//                                motionEvent.getY() < mControlMovingCenter.y + mRadius) {
//                            final int index = motionEvent.getActionIndex();
//                            mMovingID = motionEvent.getPointerId(index);
//                        } else if (motionEvent.getX() > mControlRotatingCenter.x - mRadius &&
//                                motionEvent.getX() < mControlRotatingCenter.x + mRadius &&
//                                motionEvent.getY() > mControlRotatingCenter.y - mRadius &&
//                                motionEvent.getY() < mControlRotatingCenter.y + mRadius) {
//                            final int index = motionEvent.getActionIndex();
//                            mRotatingID = motionEvent.getPointerId(index);
//                        }
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (mMovingID != -1 && motionEvent.findPointerIndex(mMovingID) == motionEvent.getActionIndex()) {
//                            final float offsetX = mControlMovingCenter.x - motionEvent.getX();
//                            final float offsetY = mControlMovingCenter.y - motionEvent.getY();
//                            mMathEngine.getHelicopter().setNextPoint(
//                                    new PointF(mMathEngine.getHelicopter().posX() - Config.CAMERA_WIDTH * offsetX,
//                                    mMathEngine.getHelicopter().posY() - Config.CAMERA_WIDTH * offsetY));
//                        } else if (mRotatingID != -1 && motionEvent.findPointerIndex(mRotatingID) == motionEvent.getActionIndex()) {
//                            final float directionX = mControlRotatingCenter.x - motionEvent.getX();
//                            final float directionY = mControlRotatingCenter.y - motionEvent.getY();
//                            final float rotationAngle = MathUtils.atan2(directionY, directionX);
//                            mMathEngine.getHelicopter().setAngle(MathUtils.radToDeg(rotationAngle) - 90);
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_POINTER_UP:
//                        if (mMovingID != -1 && motionEvent.findPointerIndex(mMovingID) == motionEvent.getActionIndex()) {
//                            mMovingID = -1;
//                            mMathEngine.getHelicopter().setNextPoint(mMathEngine.getHelicopter().getPoint());
//                        } else if (mRotatingID != -1 && motionEvent.findPointerIndex(mRotatingID) == motionEvent.getActionIndex()) {
//                            mRotatingID = -1;
//                        }
//
//                        break;
//                }
//                return true;
//            }
//        });

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                addContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT));
//            }
//        });

        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mMathEngine.stop(true);
        mEngine.stop();
    }

    public ResourceManager getResourceManager() {
        return mResourceManager;
    }

    public Scene getScene() {
        return mScene;
    }

    public Camera getCamera() {
        return mCamera;
    }
}