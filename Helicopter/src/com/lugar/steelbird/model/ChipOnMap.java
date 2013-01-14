package com.lugar.steelbird.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChipOnMap implements Parcelable {

    private int mX;
    private int mY;
    private String mTitle;
    private int mID;

    public ChipOnMap() {
    }

    public ChipOnMap(Parcel in) {
        mX = in.readInt();
        mY = in.readInt();
        mTitle = in.readString();
        mID = in.readInt();
    }

    public ChipOnMap(int mX, int mY, String mTitle, int mID) {
        this.mX = mX;
        this.mY = mY;
        this.mTitle = mTitle;
        this.mID = mID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mX);
        parcel.writeInt(mY);
        parcel.writeString(mTitle);
        parcel.writeInt(mID);
    }

    public static final Parcelable.Creator<ChipOnMap> CREATOR = new Parcelable.Creator<ChipOnMap>() {

        @Override
        public ChipOnMap createFromParcel(Parcel source) {
            return new ChipOnMap(source);
        }

        @Override
        public ChipOnMap[] newArray(int size) {
            return new ChipOnMap[size];
        }
    };

    public int getX() {
        return mX;
    }

    public void setX(int mX) {
        this.mX = mX;
    }

    public int getY() {
        return mY;
    }

    public void setY(int mY) {
        this.mY = mY;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }
}
