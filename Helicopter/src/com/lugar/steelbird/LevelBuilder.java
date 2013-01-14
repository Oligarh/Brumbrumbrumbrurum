package com.lugar.steelbird;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.lugar.steelbird.model.Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LevelBuilder {

    private int mLength;
    private List<Item> mObjects;

    public LevelBuilder(Resources res, int resLevelID) {

        mObjects = new ArrayList<Item>();

        try {
            InputStream input = res.openRawResource(resLevelID);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String text = new String(buffer);
            JSONObject jsonObject = new JSONObject(text);
            mLength = jsonObject.getInt(Tags.LENGTH);

            JSONArray jsonArray = jsonObject.getJSONArray(Tags.ITEMS);
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject itemObj = jsonArray.getJSONObject(i);
                final Item item = new Item();
                item.setType(getString(itemObj.getString(Tags.TYPE)));
                item.setPointX((float) itemObj.getDouble(Tags.POINT_X));
                item.setPointY((float) itemObj.getDouble(Tags.POINT_Y));
                item.setNextPointX((float) itemObj.getDouble(Tags.NEXT_POINT_X));
                item.setNextPointY((float) itemObj.getDouble(Tags.NEXT_POINT_Y));
                mObjects.add(item);
            }
        } catch (Exception je) {
            Log.d("Error w/file: ", je.getMessage());
        }
    }

    private String getString(String str) {
        if (TextUtils.isEmpty(str) || str.equals("null")) {
            return "";
        } else return str;
    }

    public int getLength() {
        return mLength;
    }

    public List<Item> getSceneObjects() {
        return mObjects;
    }

    private interface Tags {

        String LENGTH = "length";

        String ITEMS = "items";
        String TYPE = "type";
        String POINT_X = "point_x";
        String POINT_Y = "point_y";
        String NEXT_POINT_X = "next_point_x";
        String NEXT_POINT_Y = "next_point_y";

    }
}
