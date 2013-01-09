package com.lugar.steelbird.mathengine;

import android.graphics.PointF;
import android.util.Log;
import com.lugar.steelbird.Config;
import com.lugar.steelbird.GameActivity;
import com.lugar.steelbird.ResourceManager;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;
import com.lugar.steelbird.mathengine.bots.Tank;
import com.lugar.steelbird.mathengine.statics.StaticObject;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.math.MathUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MathEngine implements Runnable {

    private static final int UPDATE_PERIOD = 40;

    private float mCriticalDistance;
    private float mRotateBackgroundDistance;


    private Camera mCamera;
    private Scene mSceneBackground;
    private Scene mSceneMO;
    private Scene mSceneFO;
    private Scene mSceneHO;
    private ResourceManager mResourceManager;

    private boolean mAlive;
    private Thread mGameLoop;
    private long mLastUpdateScene;
    private boolean mPaused = false;

    private Helicopter mHelicopter;

    private final List<ArmedMovingObject> mArmedMovingObjects = new ArrayList<ArmedMovingObject>();
    private final List<FlyingObject> mFlyingObjects = new ArrayList<FlyingObject>();
    private final List<FlyingObject> mBotFlyingObjects = new ArrayList<FlyingObject>();
    private final List<StaticObject> mStaticObjects = new ArrayList<StaticObject>();

    public MathEngine(GameActivity gameActivity) {

        mResourceManager = gameActivity.getResourceManager();

        mCamera = gameActivity.getCamera();

        mSceneBackground = gameActivity.getScene();

        mRotateBackgroundDistance = mResourceManager.getBackGround().getHeight() * Config.SCALE;

        mStaticObjects.add(new StaticObject(new PointF(
                mCamera.getCenterX(),
                mCamera.getCenterY() - mRotateBackgroundDistance),
                mResourceManager.getBackGround(),
                gameActivity.getVertexBufferObjectManager()));

        mStaticObjects.add(new StaticObject(new PointF(
                mCamera.getCenterX(),
                mCamera.getCenterY()),
                mResourceManager.getBackGround(),
                gameActivity.getVertexBufferObjectManager()));

        mStaticObjects.add(new StaticObject(new PointF(
                mCamera.getCenterX(),
                mCamera.getCenterY() + mRotateBackgroundDistance),
                mResourceManager.getBackGround(),
                gameActivity.getVertexBufferObjectManager()));

        for (StaticObject staticObject : mStaticObjects) {
            mSceneBackground.attachChild(staticObject.getSprite());
        }

        UIScene scene = new UIScene(mCamera);
        scene.setBackgroundEnabled(false);

        mSceneHO = new Scene();
        mSceneHO.setBackgroundEnabled(false);
        mSceneHO.setChildScene(scene);

        mSceneMO = new Scene();
        mSceneMO.setBackgroundEnabled(false);
        mSceneMO.setChildScene(mSceneHO);

        mSceneFO = new Scene();
        mSceneFO.setBackgroundEnabled(false);
        mSceneFO.setChildScene(mSceneMO);

        mSceneBackground.setChildScene(mSceneFO);

        mHelicopter = new Helicopter(new PointF(Config.CAMERA_WIDTH / 2, Config.CAMERA_HEIGHT * 0.8f),
                mResourceManager);

        mCriticalDistance = mHelicopter.getMainSprite().getWidthScaled() * 0.3f;

        final Sprite moveSprite = new Sprite(
                0 - mResourceManager.getJoystik().getWidth() / 2 +
                        mResourceManager.getJoystik().getWidth() / 2 * Config.SCALE,
                Config.CAMERA_HEIGHT -
                        mResourceManager.getJoystik().getHeight() / 2 -
                        mResourceManager.getJoystik().getHeight() / 2 * Config.SCALE,
                mResourceManager.getJoystik(),
                mResourceManager.getVertexBufferObjectManager()) {
            boolean mGrabbed = false;

            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                switch(pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.mGrabbed = true;
                        break;
                    case TouchEvent.ACTION_MOVE:
                        if(this.mGrabbed) {
//                            if (pSceneTouchEvent.getX() < getWidthScaled() && pSceneTouchEvent.getY() < getHeightScaled()) {
                                final float offsetX = getWidthScaled() / 2 - pSceneTouchEvent.getX();
                                final float offsetY = Config.CAMERA_HEIGHT - getHeightScaled() / 2 -
                                        pSceneTouchEvent.getY();
                                mHelicopter.setNextPoint(
                                        new PointF(mHelicopter.posX() - Config.CAMERA_WIDTH * offsetX,
                                                mHelicopter.posY() - Config.CAMERA_WIDTH * offsetY));
//                            } else {
//                                mHelicopter.setNextPoint(mHelicopter.getPoint());
//                                this.mGrabbed = false;
//                            }
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
                Config.CAMERA_WIDTH - mResourceManager.getJoystik().getWidth() / 2 -
                        mResourceManager.getJoystik().getWidth() / 2 * Config.SCALE,
                Config.CAMERA_HEIGHT -
                        mResourceManager.getJoystik().getHeight() / 2 -
                        mResourceManager.getJoystik().getHeight() / 2 * Config.SCALE,
                mResourceManager.getJoystik(),
                mResourceManager.getVertexBufferObjectManager()) {
            boolean mGrabbed = false;

            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
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

        scene.attachChild(moveSprite);
        scene.attachChild(rotateSprite);
        scene.registerTouchArea(moveSprite);
        scene.registerTouchArea(rotateSprite);

        scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                mHelicopter.setNextPoint(mHelicopter.getPoint());
                return true;
            }
        });

        addHeroObject(mHelicopter);
    }

    public void start() {
        mAlive = true;
        mGameLoop = new Thread(this);
        mGameLoop.setDaemon(true);
        mGameLoop.start();
    }

    public void stop(boolean interrupt) {
        Log.d("Math engine stop: ", String.valueOf(interrupt));
        mAlive = false;
        if (interrupt) {
            mGameLoop.interrupt();
        }
    }

    private long getElapsedTimeMillis() {
        long now = System.currentTimeMillis();

        if (mLastUpdateScene == 0) {
            mLastUpdateScene = now;
        }

        long period = now - mLastUpdateScene;
        mLastUpdateScene = now;

        return period;
    }

    @Override
    public void run() {
        try {
            while (mAlive) {

                long period = getElapsedTimeMillis();

                if (!mPaused) {
                    tact(period);
                }

                Thread.sleep(UPDATE_PERIOD - period > 0 ? UPDATE_PERIOD - period : UPDATE_PERIOD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void tact(long time) {

        final long now = System.currentTimeMillis();

        // tact Helicopter
        mHelicopter.tact(now, time);
        if (mHelicopter.canShoot(now)) {
            final List<FlyingObject> flyingObjects = mHelicopter.shoot(now);
            for (FlyingObject flyingObject : flyingObjects) {
                addHelicopterFlyingObject(flyingObject);
            }
        }
        // END tact Helicopter

        // tact bombs of bots
        for (Iterator<FlyingObject> flyingIterator = mBotFlyingObjects.iterator(); flyingIterator.hasNext(); ) {
            FlyingObject flyingObject = flyingIterator.next();
            flyingObject.tact(now, time);
            if (MovingObject.distance(flyingObject.posX(), flyingObject.posY(), mHelicopter.posX(), mHelicopter.posY())
                    < mCriticalDistance) {
                mHelicopter.damage(flyingObject.getDamage());
                if (!mHelicopter.isAlive()) {
                    System.out.println("~~~~~ GAME OVER!");
                }
                removeFlyingObject(flyingObject, flyingIterator);
            }
        }
        // END tact bombs of bots

        // tact bombs of Helicopter
        for (Iterator<FlyingObject> flyingIterator = mFlyingObjects.iterator(); flyingIterator.hasNext(); ) {
            FlyingObject flyingObject = flyingIterator.next();
            flyingObject.tact(now, time);
            for (Iterator<ArmedMovingObject> botIterator = mArmedMovingObjects.iterator(); botIterator.hasNext(); ) {
                ArmedMovingObject botObject = botIterator.next();
                if (flyingObject.getMainSprite().collidesWith(botObject.getMainSprite())) {
                    botObject.damage(flyingObject.getDamage());
                    if (!botObject.isAlive()) {

//                      TODO добавить замену спрайта бота на груду горящего метала
//                        final float x = botObject.posX();
//                        final float y = botObject.posY();
//
//                        AnimatedSprite animatedSprite = new AnimatedSprite(
//                                x - mResourceManager.getExp().getWidth(0) / 2,
//                                y - mResourceManager.getExp().getHeight(0) / 2,
//                                mResourceManager.getExp(), mGameActivity.getVertexBufferObjectManager());
//
//                        animatedSprite.setScale(Config.SCALE * 2);
//
//                        animatedSprite.animate(60, 0);
//
//                        mScene.attachChild(animatedSprite);

                        removeArmedMovingObject(botObject, botIterator);
                    }
                    removeFlyingObject(flyingObject, flyingIterator);
                }
            }

            if (flyingObject.posX() < mCamera.getXMin() || flyingObject.posY() < mCamera.getYMin() ||
                    flyingObject.posX() > mCamera.getXMax() || flyingObject.posY() > mCamera.getYMax()) {
                removeFlyingObject(flyingObject, flyingIterator);
            }
        }
        // END tact bombs of Helicopter

        // tact bots
        for (Iterator<ArmedMovingObject> botIterator = mArmedMovingObjects.iterator(); botIterator.hasNext(); ) {
            ArmedMovingObject botObject = botIterator.next();
            botObject.tact(now, time);

            if (botObject.canShoot(now)) {
                final List<FlyingObject> flyingObjects = botObject.shoot(now);
                for (FlyingObject flyingObject : flyingObjects) {
                    addBotFlyingObject(flyingObject);
                }
            }

            if (botObject.posY() > mCamera.getYMax() + botObject.getMainSprite().getHeightScaled() / 2) {
                removeArmedMovingObject(botObject, botIterator);
            }
        }
        // END tact bots

        updateBackground(time);
    }

    public void addHeroObject(Helicopter object) {
        mSceneHO.attachChild(object.getMainSprite());
    }

    public synchronized void addArmedMovingObject(ArmedMovingObject object) {
        mArmedMovingObjects.add(object);
        mSceneMO.attachChild(object.getMainSprite());
    }

    public synchronized void removeArmedMovingObject(ArmedMovingObject object, Iterator iterator) {
        mSceneMO.detachChild(object.getMainSprite());
        iterator.remove();
    }

    public synchronized void addHelicopterFlyingObject(FlyingObject object) {
        mFlyingObjects.add(object);
        mSceneFO.attachChild(object.getMainSprite());
    }

    public synchronized void addBotFlyingObject(FlyingObject object) {
        mBotFlyingObjects.add(object);
        mSceneFO.attachChild(object.getMainSprite());
    }

    public synchronized void removeFlyingObject(FlyingObject object, Iterator iterator) {
        mSceneFO.detachChild(object.getMainSprite());
        iterator.remove();
    }

    public void setHelicopterPointF(PointF pointF) {
        mHelicopter.setPoint(pointF);
    }

    public Helicopter getHelicopter() {
        return mHelicopter;
    }

    private void updateBackground(long period) {

        for (StaticObject staticObject : mStaticObjects) {
            if (staticObject.posY() - mRotateBackgroundDistance / 2 > mCamera.getCenterY() + Config.CAMERA_HEIGHT / 2) {
                staticObject.setPoint(staticObject.posY() - mRotateBackgroundDistance * 3);
            }
        }

        float distance = (float) period / 1000 * Config.SCENE_SPEED;
        mCamera.setCenter(mCamera.getCenterX(), mCamera.getCenterY() - distance);

        mHelicopter.setY(mHelicopter.posY() - distance);
//        mHelicopter.getMainSprite().setPosition(mHelicopter.getMainSprite().getX(),
//                mHelicopter.getMainSprite().getY() - distance);
    }

    class UIScene extends CameraScene {

        UIScene(Camera pCamera) {
            super(pCamera);
        }

    }


}
