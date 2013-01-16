package com.lugar.steelbird.mathengine;

public class PlayerFrag {

    private int mFrag;
    private int mDead;
    private int mDamage;
    private int mCarried;
    private int mMoney;
    private int mExperience;
//    private List<Award> awards;

    public void addFrag() {
        mFrag++;
    }

    public void addDamage(float damage) {
        mDamage += damage;
    }

    public void addCarried(float carried) {
        mCarried += carried;
    }

    public void addExperience(float experience) {
        mExperience += experience;
    }

    public int getFrag() {
        return mFrag;
    }

    public int getDead() {
        return mDead;
    }

    public int getDamage() {
        return mDamage;
    }

    public int getCarried() {
        return mCarried;
    }

    public int getMoney() {
        return mMoney;
    }

    public int getExperience() {
        return mExperience;
    }
}
