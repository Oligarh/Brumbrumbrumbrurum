package com.lugar.steelbird.mathengine;

import android.graphics.PointF;
import android.util.Log;

import com.lugar.steelbird.*;
import com.lugar.steelbird.controllers.StatisticController;
import com.lugar.steelbird.mathengine.ammunitions.FlyingObject;
import com.lugar.steelbird.mathengine.bots.Soldier;
import com.lugar.steelbird.mathengine.bots.Tank;
import com.lugar.steelbird.mathengine.statics.Enemy;
import com.lugar.steelbird.mathengine.statics.StaticObject;
import com.lugar.steelbird.mathengine.statics.Tree;
import com.lugar.steelbird.model.Item;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.util.math.MathUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MathEngine implements Runnable {

    private StatisticController mStatisticController;

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
    private Enemy mEnemy;

    private GameActivity mGameActivity;

    private final List<ArmedMovingObject> mArmedMovingObjects = new ArrayList<ArmedMovingObject>();
    private final List<FlyingObject> mHelicopterFlyingObjects = new ArrayList<FlyingObject>();
    private final List<FlyingObject> mBotFlyingObjects = new ArrayList<FlyingObject>();
    private final List<StaticObject> mBackground = new ArrayList<StaticObject>();
    private final List<StaticObject> mStaticObjects = new ArrayList<StaticObject>();

    private final List<Item> mAllObjects;

    private int mLength;
    private String mLocationID;

    public MathEngine(GameActivity gameActivity, int resLevelID, String locationID) {

        mLocationID = locationID;

        LevelBuilder levelBuilder = new LevelBuilder(gameActivity.getResources(), resLevelID);
        mLength = - levelBuilder.getLength() * Config.CAMERA_HEIGHT;
        mAllObjects = levelBuilder.getSceneObjects();

        mStatisticController = new StatisticController();

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

        mCriticalDistance = mHelicopter.getSprite().getWidthScaled() * 0.3f;

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

        if (mEnemy != null && MathUtils.distance(mHelicopter.posX(), mHelicopter.posY(), mEnemy.posX(), mEnemy.posY()) <
                mCriticalDistance) {
            mGameActivity.showResult(mHelicopter.getPlayerFrag());
            int countOpened = Prefs.getIntProperty(mGameActivity, mLocationID);
            Prefs.setIntProperty(mGameActivity, mLocationID, countOpened + 1);
            Log.d("~~~~~ MathEngine: ", "Frags: " + mHelicopter.getPlayerFrag().getFrag());
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
            if (MathUtils.distance(flyingObject.posX(), flyingObject.posY(), mHelicopter.posX(), mHelicopter.posY())
                    < mCriticalDistance) {
                if (mHelicopter.getHealth() >= flyingObject.getDamage()) {
                    notifyGameControllersCarriedDamage(mHelicopter, flyingObject.getDamage());
                } else {
                    notifyGameControllersCarriedDamage(mHelicopter, mHelicopter.getHealth());
                }
                mHelicopter.damage(flyingObject.getDamage());

                if (!mHelicopter.isAlive()) {
                    notifyGameControllersAIKillPlayer(mHelicopter);
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
                if (botObject.isAlive() && flyingObject.getSprite().collidesWith(botObject.getSprite())) {
                    if (botObject.getHealth() >= flyingObject.getDamage()) {
                        notifyGameControllersDamageToOpponents(mHelicopter, flyingObject.getDamage());
                    } else {
                        notifyGameControllersDamageToOpponents(mHelicopter, botObject.getHealth());
                    }
                    botObject.damage(flyingObject.getDamage());

                    if (!botObject.isAlive()) {
                        notifyGameControllersPlayerKillAI(mHelicopter);
//                      TODO добавить замену спрайта бота на груду горящего метала
                        removeArmedMovingObject(botObject, botIterator);
                    }
                    removeFlyingObjectHelicopter(flyingObject, flyingIterator);
                    removed = true;
                    break;
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
            if (botObject.isAlive()) {
                botObject.tact(now, time);
                if (botObject.canShoot(now, mCamera.getYMin())) {
                    final List<FlyingObject> flyingObjects = botObject.shoot(now);
                    for (FlyingObject flyingObject : flyingObjects) {
                        addBotFlyingObject(flyingObject);
                    }
                }

                if (botObject.posY() > mCamera.getYMax() + botObject.getSprite().getHeightScaled() / 2) {
                    removeArmedMovingObject(botObject, botIterator);
                }
            }
        }
        // END tact bots

        // tact bots
        for (Iterator<StaticObject> staticIterator = mStaticObjects.iterator(); staticIterator.hasNext(); ) {
            StaticObject staticObject = staticIterator.next();
            if (staticObject.posY() > mCamera.getYMax() + staticObject.getSprite().getHeightScaled() / 2) {
                removeTree((Tree) staticObject, staticIterator);
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
                            mResourceManager, mHelicopter));
                    iterator.remove();
                } else if (sceneObject.getType().equals(Tags.PALM)) {
                    addTreeObject(new Tree(new PointF(sceneObject.getPointX() / 100 * Config.CAMERA_WIDTH,
                            sceneObject.getPointY() / 100 * mLength), mResourceManager.getPalm(),
                            mResourceManager.getVertexBufferObjectManager()));
                    iterator.remove();
                } else if (sceneObject.getType().equals(Tags.ENEMY)) {
                    mEnemy = new Enemy(new PointF(sceneObject.getPointX() / 100 * Config.CAMERA_WIDTH,
                            sceneObject.getPointY() / 100 * mLength), mResourceManager.getEnemy(),
                            mResourceManager.getVertexBufferObjectManager());
                    mEnemy.addShadow(mResourceManager.getEnemyShadow());
                    mBackgroundLayer.attachChild(mEnemy.getSpriteShadow());
                    mBackgroundLayer.attachChild(mEnemy.getSprite());
                    iterator.remove();
                }
            }
        }

        updateBackground(time);
    }

    public void addHeroObject(Helicopter object) {
        object.addShadow(mResourceManager.getHelicopterShadow());
        mHelicopterLayer.attachChild(object.getSpriteShadow());
        mHelicopterLayer.attachChild(object.getSprite());
    }

    public synchronized void addArmedMovingObject(ArmedMovingObject object) {
        mArmedMovingObjects.add(object);
        object.addShadow(mResourceManager.getTankShadow());
        mBotsLayer.attachChild(object.getSpriteShadow());
        mBotsLayer.attachChild(object.getSprite());
    }

    public synchronized void addSoldier(Soldier object) {
        mArmedMovingObjects.add(object);
        object.addShadow(mResourceManager.getSoldierShadow(object.getSprite().getTextureRegion()));
        mBotsLayer.attachChild(object.getSpriteShadow());
        mBotsLayer.attachChild(object.getSprite());
    }

    public synchronized void addTreeObject(Tree object) {
        mStaticObjects.add(object);
        object.addShadow(mResourceManager.getPalmShadow(object.getSprite().getTextureRegion()));
        mStaticLayer.attachChild(object.getSpriteShadow());
        mStaticLayer.attachChild(object.getSprite());
    }

    public synchronized void removeArmedMovingObject(final ArmedMovingObject object, Iterator iterator) {
        mGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                object.getSprite().clearEntityModifiers();
                object.getSprite().clearUpdateHandlers();
                object.getSpriteShadow().clearEntityModifiers();
                object.getSpriteShadow().clearUpdateHandlers();
                mBotsLayer.detachChild(object.getSpriteShadow());
                mBotsLayer.detachChild(object.getSprite());
            }
        });
        iterator.remove();
    }

    public synchronized void removeTree(final Tree object, Iterator iterator) {
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
        mFlyingObjectHelicopterLayer.attachChild(object.getSprite());
    }

    public synchronized void addBotFlyingObject(FlyingObject object) {
        mBotFlyingObjects.add(object);
        mFlyingObjectBotLayer.attachChild(object.getSprite());
    }

    public synchronized void removeFlyingObjectBot(final FlyingObject object, Iterator iterator) {
        mGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                object.getSprite().clearEntityModifiers();
                object.getSprite().clearUpdateHandlers();
                mFlyingObjectBotLayer.detachChild(object.getSprite());
            }
        });
        iterator.remove();
    }

    public synchronized void removeFlyingObjectHelicopter(final FlyingObject object, Iterator iterator) {
        mGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                object.getSprite().clearEntityModifiers();
                object.getSprite().clearUpdateHandlers();
                mFlyingObjectHelicopterLayer.detachChild(object.getSprite());
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
        if (mCamera.getYMin() > mLength) {
            for (StaticObject background : mBackground) {
                if (background.posY() - mRotateBackgroundDistance / 2 > mCamera.getCenterY() + Config.CAMERA_HEIGHT / 2) {
                    background.setY(background.posY() - mRotateBackgroundDistance * 3);
                }
            }

            float distance = (float) period / 1000 * Config.SCENE_SPEED;
            mCamera.setCenter(mCamera.getCenterX(), mCamera.getCenterY() - distance);

            mHelicopter.setY(mHelicopter.posY() - distance);
        }
    }

    private void notifyGameControllersDamageToOpponents(Helicopter helicopter, float damage) {
        mStatisticController.damageToOpponents(helicopter, damage);
    }

    private void notifyGameControllersCarriedDamage(Helicopter helicopter, float damage) {
        mStatisticController.carriedDamage(helicopter, damage);
    }

    private void notifyGameControllersAIKillPlayer(Helicopter helicopter) {
//      TODO write to prefs
//        mStatisticController.aiKillPlayer(helicopter);
    }

    private void notifyGameControllersPlayerKillAI(Helicopter helicopter) {
        mStatisticController.playerKillAI(helicopter);
    }

    private interface Tags {

        String ENEMY = "enemy";

        String TANK = "tank";
        String SOLDIER = "soldier";
        String CANNON = "cannon";
        String DOT = "dot";

        String PALM = "palm";
    }
}
