package com.lugar.steelbird.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    private String mType;
    private float mPointX;
    private float mPointY;
    private float mNextPointX;
    private float mNextPointY;

    public Item() {
    }

    private Item(Parcel in) {
        mType = in.readString();
        mPointX = in.readFloat();
        mPointY = in.readFloat();
        mNextPointX = in.readFloat();
        mNextPointY = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mType);
        parcel.writeFloat(mPointX);
        parcel.writeFloat(mPointY);
        parcel.writeFloat(mNextPointX);
        parcel.writeFloat(mNextPointY);
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

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public float getPointX() {
        return mPointX;
    }

    public void setPointX(float mPointX) {
        this.mPointX = mPointX;
    }

    public float getPointY() {
        return mPointY;
    }

    public void setPointY(float mPointY) {
        this.mPointY = mPointY;
    }

    public float getNextPointX() {
        return mNextPointX;
    }

    public void setNextPointX(float mNextPointX) {
        this.mNextPointX = mNextPointX;
    }

    public float getNextPointY() {
        return mNextPointY;
    }

    public void setNextPointY(float mNextPointY) {
        this.mNextPointY = mNextPointY;
    }
}
