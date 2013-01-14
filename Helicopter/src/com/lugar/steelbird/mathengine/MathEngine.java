package com.lugar.steelbird.mathengine;

import android.graphics.PointF;
import android.util.Log;

import com.lugar.steelbird.Config;
import com.lugar.steelbird.GameActivity;
import com.lugar.steelbird.LevelBuilder;
import com.lugar.steelbird.ResourceManager;
import com.lugar.steelbird.UIHandler;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;
import com.lugar.steelbird.mathengine.bots.Soldier;
import com.lugar.steelbird.mathengine.bots.Tank;
import com.lugar.steelbird.mathengine.statics.StaticObject;
import com.lugar.steelbird.model.Item;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MathEngine implements Runnable {

    private static final int LAYER_BACKGROUND = 0;
    private static final int LAYER_BOTS = LAYER_BACKGROUND + 1;
    private static final int LAYER_STATIC = LAYER_BOTS + 1;
    private static final int LAYER_HELICOPTER = LAYER_STATIC + 1;

    private Entity mBackgroundLayer;
    private Entity mBotsLayer;
    private Entity mStaticLayer;
    private Entity mFlyingObjectBotLayer;
    private Entity mFlyingObjectHelicopterLayer;
    private Entity mHelicopterLayer;

    private static final int UPDATE_PERIOD = 40;

    private float mCriticalDistance;
    private float mRotateBackgroundDistance;

    private Camera mCamera;
    private Scene mScene;
    private ResourceManager mResourceManager;

    private boolean mAlive;
    private Thread mGameLoop;
    private long mLastUpdateScene;
    private boolean mPaused = false;

    private Helicopter mHelicopter;

    private GameActivity mGameActivity;

    private final List<ArmedMovingObject> mArmedMovingObjects = new ArrayList<ArmedMovingObject>();
    private final List<FlyingObject> mHelicopterFlyingObjects = new ArrayList<FlyingObject>();
    private final List<FlyingObject> mBotFlyingObjects = new ArrayList<FlyingObject>();
    private final List<StaticObject> mBackground = new ArrayList<StaticObject>();
    private final List<StaticObject> mStaticObjects = new ArrayList<StaticObject>();

    private final List<Item> mAllObjects;

    private int mLength;

    public MathEngine(GameActivity gameActivity, int resLevelID) {

        LevelBuilder levelBuilder = new LevelBuilder(gameActivity.getResources(), resLevelID);
        mLength = - levelBuilder.getLength() * Config.CAMERA_HEIGHT;
        mAllObjects = levelBuilder.getSceneObjects();

        Log.d("~~~~~ MathEngine, LevelBuilder getLength = " + mLength, "");

        mGameActivity = gameActivity;
        mResourceManager = gameActivity.getResourceManager();

        mCamera = gameActivity.getCamera();

        mScene = gameActivity.getScene();

        mRotateBackgroundDistance = mResourceManager.getBackGround().getHeight() * Config.SCALE;

        mBackground.add(new StaticObject(new PointF(
                mCamera.getCenterX(),
                mCamera.getCenterY() - mRotateBackgroundDistance),
                mResourceManager.getBackGround(),
                gameActivity.getVertexBufferObjectManager()));

        mBackground.add(new StaticObject(new PointF(
                mCamera.getCenterX(),
                mCamera.getCenterY()),
                mResourceManager.getBackGround(),
                gameActivity.getVertexBufferObjectManager()));

        mBackground.add(new StaticObject(new PointF(
                mCamera.getCenterX(),
                mCamera.getCenterY() + mRotateBackgroundDistance),
                mResourceManager.getBackGround(),
                gameActivity.getVertexBufferObjectManager()));

        mBackgroundLayer = new Entity();
        mBotsLayer = new Entity();
        mStaticLayer = new Entity();
        mFlyingObjectBotLayer = new Entity();
        mFlyingObjectHelicopterLayer = new Entity();
        mHelicopterLayer = new Entity();

        mScene.attachChild(mBackgroundLayer);
        mScene.attachChild(mFlyingObjectBotLayer);
        mScene.attachChild(mBotsLayer);
        mScene.attachChild(mStaticLayer);
        mScene.attachChild(mFlyingObjectHelicopterLayer);
        mScene.attachChild(mHelicopterLayer);

        for (StaticObject background : mBackground) {
            mBackgroundLayer.attachChild(background.getSprite());
        }

        mHelicopter = new Helicopter(new PointF(Config.CAMERA_WIDTH / 2, Config.CAMERA_HEIGHT * 0.8f),
                mResourceManager);

        mScene.setChildScene(new UIHandler(mCamera, mHelicopter, mResourceManager.getJoystick(),
                mResourceManager.getVertexBufferObjectManager(), gameActivity.getFpsCounter(),
                mResourceManager.getFont()));

        mCriticalDistance = mHelicopter.getMainSprite().getWidthScaled() * 0.3f;

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

    public void tact(long time) {

        if (mCamera.getYMin() < mLength) {
//          TODO VICTORY!!!
            Log.d("~~~~~ MathEngine tact: ", "VICTORY!");
            mPaused = true;
        }

        final long now = System.currentTimeMillis();

        // tact Helicopter
        mHelicopter.tact(now, time);
        if (mHelicopter.canShoot(now, mCamera.getYMin())) {
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
                    Log.d("~~~~~ MathEngine tact (Helicopter health = " + mHelicopter.getHealth() + ")", "GAME OVER!");
                }
                removeFlyingObjectBot(flyingObject, flyingIterator);
            } else if (flyingObject.posX() < mCamera.getXMin() || flyingObject.posY() < mCamera.getYMin() ||
                    flyingObject.posX() > mCamera.getXMax() || flyingObject.posY() > mCamera.getYMax()) {
                removeFlyingObjectBot(flyingObject, flyingIterator);
            }
        }
        // END tact bombs of bots

        // tact bombs of Helicopter
        for (Iterator<FlyingObject> flyingIterator = mHelicopterFlyingObjects.iterator(); flyingIterator.hasNext(); ) {
            FlyingObject flyingObject = flyingIterator.next();
            flyingObject.tact(now, time);
            boolean removed = false;
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
                    removeFlyingObjectHelicopter(flyingObject, flyingIterator);
                    removed = true;
                }
            }

            if (!removed && (flyingObject.posX() < mCamera.getXMin() || flyingObject.posY() < mCamera.getYMin() ||
                    flyingObject.posX() > mCamera.getXMax() || flyingObject.posY() > mCamera.getYMax())) {
                removeFlyingObjectHelicopter(flyingObject, flyingIterator);
            }
        }
        // END tact bombs of Helicopter

        // tact bots
        for (Iterator<ArmedMovingObject> botIterator = mArmedMovingObjects.iterator(); botIterator.hasNext(); ) {
            ArmedMovingObject botObject = botIterator.next();
            botObject.tact(now, time);

            if (botObject.canShoot(now, mCamera.getYMin())) {
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

        // tact bots
        for (Iterator<StaticObject> staticIterator = mStaticObjects.iterator(); staticIterator.hasNext(); ) {
            StaticObject staticObject = staticIterator.next();
            if (staticObject.posY() > mCamera.getYMax() + staticObject.getSprite().getHeightScaled() / 2) {
                removeStaticObject(staticObject, staticIterator);
            }
        }
        // END tact bots

        for (Iterator<Item> iterator = mAllObjects.iterator(); iterator.hasNext(); ) {
            Item sceneObject = iterator.next();
            if (sceneObject.getPointY() / 100 * mLength > (mCamera.getCenterY() - Config.CAMERA_HEIGHT)) {
                if (sceneObject.getType().equals(Tags.TANK)) {
                    addArmedMovingObject(new Tank(
                            new PointF(sceneObject.getPointX() / 100 * Config.CAMERA_WIDTH,
                                    sceneObject.getPointY() / 100 * mLength),
                            new PointF(sceneObject.getNextPointX() / 100 * Config.CAMERA_WIDTH,
                                    sceneObject.getNextPointY() / 100 * mLength),
                            mResourceManager, mHelicopter));
                    iterator.remove();
                } else if (sceneObject.getType().equals(Tags.SOLDIER)) {
                    addSoldier(new Soldier(
                            new PointF(sceneObject.getPointX() / 100 * Config.CAMERA_WIDTH,
                                    sceneObject.getPointY() / 100 * mLength),
                            new PointF(sceneObject.getNextPointX() / 100 * Config.CAMERA_WIDTH,
                                    sceneObject.getNextPointY() / 100 * mLength),
                            mResourceManager, mHelicopter));
                    iterator.remove();
                } else if (sceneObject.getType().equals(Tags.TREE_1)) {
                    addStaticObject(new StaticObject(new PointF(sceneObject.getPointX() / 100 * Config.CAMERA_WIDTH,
                            sceneObject.getPointY() / 100 * mLength), mResourceManager.getPalm(),
                            mResourceManager.getVertexBufferObjectManager()));
                    iterator.remove();
                }
            }
        }

        updateBackground(time);
    }

    public void addHeroObject(Helicopter object) {
        object.addShadow(mResourceManager.getHelicopterShadow());
        mHelicopterLayer.attachChild(object.getSpriteShadow());
        mHelicopterLayer.attachChild(object.getMainSprite());
    }

    public synchronized void addArmedMovingObject(ArmedMovingObject object) {
        mArmedMovingObjects.add(object);
        object.addShadow(mResourceManager.getTankShadow());
        mBotsLayer.attachChild(object.getSpriteShadow());
        mBotsLayer.attachChild(object.getMainSprite());
    }

    public synchronized void addSoldier(Soldier object) {
        mArmedMovingObjects.add(object);
        object.addShadow(mResourceManager.getSoldier());
        mBotsLayer.attachChild(object.getSpriteShadow());
        mBotsLayer.attachChild(object.getMainSprite());
    }

    public synchronized void addStaticObject(StaticObject object) {
        mStaticObjects.add(object);
        object.addShadow(mResourceManager.getPalmShadow());
        mStaticLayer.attachChild(object.getSpriteShadow());
        mStaticLayer.attachChild(object.getSprite());
    }

    public synchronized void removeArmedMovingObject(final ArmedMovingObject object, Iterator iterator) {
        mGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                object.getMainSprite().clearEntityModifiers();
                object.getMainSprite().clearUpdateHandlers();
                object.getSpriteShadow().clearEntityModifiers();
                object.getSpriteShadow().clearUpdateHandlers();
                mBotsLayer.detachChild(object.getSpriteShadow());
                mBotsLayer.detachChild(object.getMainSprite());
            }
        });
        iterator.remove();
    }

    public synchronized void removeStaticObject(final StaticObject object, Iterator iterator) {
        mGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                object.getSprite().clearEntityModifiers();
                object.getSprite().clearUpdateHandlers();
                object.getSpriteShadow().clearEntityModifiers();
                object.getSpriteShadow().clearUpdateHandlers();
                mStaticLayer.detachChild(object.getSpriteShadow());
                mStaticLayer.detachChild(object.getSprite());
            }
        });
        iterator.remove();
    }

    public synchronized void addHelicopterFlyingObject(FlyingObject object) {
        mHelicopterFlyingObjects.add(object);
        mFlyingObjectHelicopterLayer.attachChild(object.getMainSprite());
    }

    public synchronized void addBotFlyingObject(FlyingObject object) {
        mBotFlyingObjects.add(object);
        mFlyingObjectBotLayer.attachChild(object.getMainSprite());
    }

    public synchronized void removeFlyingObjectBot(final FlyingObject object, Iterator iterator) {
        mGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                object.getMainSprite().clearEntityModifiers();
                object.getMainSprite().clearUpdateHandlers();
                mFlyingObjectBotLayer.detachChild(object.getMainSprite());
            }
        });
        iterator.remove();
    }

    public synchronized void removeFlyingObjectHelicopter(final FlyingObject object, Iterator iterator) {
        mGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                object.getMainSprite().clearEntityModifiers();
                object.getMainSprite().clearUpdateHandlers();
                mFlyingObjectHelicopterLayer.detachChild(object.getMainSprite());
            }
        });
        iterator.remove();
    }

    public void setHelicopterPointF(PointF pointF) {
        mHelicopter.setPoint(pointF);
    }

    public Helicopter getHelicopter() {
        return mHelicopter;
    }

    private void updateBackground(long period) {

        for (StaticObject background : mBackground) {
            if (background.posY() - mRotateBackgroundDistance / 2 > mCamera.getCenterY() + Config.CAMERA_HEIGHT / 2) {
                background.setPoint(background.posY() - mRotateBackgroundDistance * 3);
            }
        }

        float distance = (float) period / 1000 * Config.SCENE_SPEED;
        mCamera.setCenter(mCamera.getCenterX(), mCamera.getCenterY() - distance);

        mHelicopter.setY(mHelicopter.posY() - distance);
    }

    private interface Tags {

        String TANK = "tank";
        String SOLDIER = "soldier";
        String CANNON = "cannon";
        String DOT = "dot";

        String TREE_1 = "tree_1";
    }
}
