package com.lugar.steelbird;

import android.view.Display;
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
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;

public class GameActivity extends BaseGameActivity {

    private MathEngine mMathEngine;
    private Scene mScene;
    private Camera mCamera;
    private ResourceManager mResourceManager;

    @Override
    public EngineOptions onCreateEngineOptions() {

        Display display = getWindowManager().getDefaultDisplay();
        Config.SCALE = (float) display.getWidth() / Config.ETALON_WIDTH;
        Config.CAMERA_WIDTH = display.getWidth();
        Config.CAMERA_HEIGHT = display.getHeight();

        mCamera = new Camera(0, 0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT);

        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT), mCamera);
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);

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

        mMathEngine = new MathEngine(this);
        mMathEngine.start();

        final View view = getLayoutInflater().inflate(R.layout.ui_layout, null);
        final ControlImageView joystikMove = (ControlImageView) view.findViewById(R.id.joystik_move);
        final RotateControl joystikAngle = (RotateControl) view.findViewById(R.id.joystik_angle);

        joystikMove.setHelicopter(mMathEngine.getHelicopter());

        joystikAngle.setHelicopter(mMathEngine.getHelicopter());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });

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