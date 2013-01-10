package com.lugar.steelbird.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SceneObject implements Parcelable {

    private String mType;
    private ArrayList<Item> mItems;

    public SceneObject() {
    }

    private SceneObject(Parcel in) {
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

    public static final Parcelable.Creator<SceneObject> CREATOR = new Parcelable.Creator<SceneObject>() {

        @Override
        public SceneObject createFromParcel(Parcel source) {
            return new SceneObject(source);
        }

        @Override
        public SceneObject[] newArray(int size) {
            return new SceneObject[size];
        }
    };

    public void addItem(Item item) {
        if (mItems == null) {
            mItems = new ArrayList<Item>();
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
