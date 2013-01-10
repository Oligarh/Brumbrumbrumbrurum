package com.lugar.steelbird.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Bot implements Parcelable {

    private String mType;
    private ArrayList<Item> mItems;

    public Bot() {
    }

    private Bot(Parcel in) {
        mType = in.readString();
        mItems = in.readArrayList(Item.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mType);
        parcel.writeList(mItems);
    }

    public static final Parcelable.Creator<Bot> CREATOR = new Parcelable.Creator<Bot>() {

        @Override
        public Bot createFromParcel(Parcel source) {
            return new Bot(source);
        }

        @Override
        public Bot[] newArray(int size) {
            return new Bot[size];
        }
    };

    public void addItem(Item item) {
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mItems.add(item);
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<Item> items) {
        mItems = items;
    }
}
