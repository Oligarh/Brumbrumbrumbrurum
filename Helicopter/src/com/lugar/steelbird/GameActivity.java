package com.lugar.steelbird;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;
import com.lugar.steelbird.helicopter.HelicopterType;
import com.lugar.steelbird.mathengine.MathEngine;
import com.lugar.steelbird.mathengine.PlayerFrag;

import com.lugar.steelbird.resources.ResourceManager;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSCounter;
import org.andengine.input.touch.controller.MultiTouchController;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;

public class GameActivity extends BaseGameActivity {

    private MathEngine mMathEngine;
    private Scene mScene;
    private Camera mCamera;
    private ResourceManager mResourceManager;
    private FPSCounter mFpsCounter;

    private LinearLayout layoutResult;
    private TextView mCountFrags;
    private TextView mDamage;
    private TextView mCarried;
    private TextView mMoney;
    private TextView mExperience;

    @Override
    public EngineOptions onCreateEngineOptions() {

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        Config.CAMERA_WIDTH = displaymetrics.widthPixels;
        Config.CAMERA_HEIGHT = displaymetrics.heightPixels;
        Config.SCALE = (float) displaymetrics.widthPixels / Config.ETALON_WIDTH;
        Config.SCENE_SPEED = displaymetrics.heightPixels / 30;
        mCamera = new Camera(0, 0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT);

        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT), mCamera);
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        mResourceManager = new ResourceManager(this, LocationType.JUNGLE, HelicopterType.LIGHT);

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

        mScene = new Scene();
        mScene.setBackground(new Background(0.29f, 0.31f, 0.37f));

        mEngine.setTouchController(new MultiTouchController());

        mFpsCounter = new FPSCounter();
        mEngine.registerUpdateHandler(mFpsCounter);

        layoutResult = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_result, null);
        mCountFrags = (TextView) layoutResult.findViewById(R.id.count_frags);
        mDamage = (TextView) layoutResult.findViewById(R.id.count_damage);
        mCarried = (TextView) layoutResult.findViewById(R.id.count_carried);
        mMoney = (TextView) layoutResult.findViewById(R.id.money);
        mExperience = (TextView) layoutResult.findViewById(R.id.experience);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addContentView(layoutResult,  new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });

        Intent intent = getIntent();
        int resID = getResources().getIdentifier(intent.getStringExtra("file"), "raw", getPackageName());
        String locationID = intent.getStringExtra("locationID");
        mMathEngine = new MathEngine(this, resID, locationID);
        mMathEngine.start();



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

    public void showResult(final PlayerFrag playerFrag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCountFrags.setText(String.valueOf(playerFrag.getFrag()));
                mDamage.setText(String.valueOf(playerFrag.getDamage()));
                mCarried.setText(String.valueOf(playerFrag.getCarried()));
                mMoney.setText(String.valueOf(playerFrag.getMoney()));
                mExperience.setText(String.valueOf(playerFrag.getExperience()));
                layoutResult.setVisibility(View.VISIBLE);
            }
        });
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

    public FPSCounter getFpsCounter() {
        return mFpsCounter;
    }

    public void next(View view) {
        onBackPressed();
    }
}