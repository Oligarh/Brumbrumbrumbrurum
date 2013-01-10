package com.lugar.steelbird.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    private int mPointX;
    private int mPointY;
    private int mNextPointX;
    private int mNextPointY;

    public Item() {
    }

    private Item(Parcel in) {
        mPointX = in.readInt();
        mPointY = in.readInt();
        mNextPointX = in.readInt();
        mNextPointY = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mPointX);
        parcel.writeInt(mPointY);
        parcel.writeInt(mNextPointX);
        parcel.writeInt(mNextPointY);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getPointX() {
        return mPointX;
    }

    public void setPointX(int pointX) {
        mPointX = pointX;
    }

    public int getNextPointX() {
        return mNextPointX;
    }

    public void setNextPointX(int nextPointX) {
        mNextPointX = nextPointX;
    }

    public int getPointY() {
        return mPointY;
    }

    public void setPointY(int pointY) {
        mPointY = pointY;
    }

    public int getNextPointY() {
        return mNextPointY;
    }

    public void setNextPointY(int nextPointY) {
        mNextPointY = nextPointY;
    }
}
