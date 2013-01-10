package com.lugar.steelbird;

import android.text.TextUtils;
import android.util.Log;

import com.lugar.steelbird.model.Bot;
import com.lugar.steelbird.model.Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestPars {

    public TestPars() {

        try {
//          TODO from file
            final JSONObject jsonObject = null;

            final ArrayList<Bot> bots = new ArrayList<Bot>();

            final JSONArray botObjects = jsonObject.getJSONArray(Tags.BOTS);
            for (int i = 0; i < botObjects.length(); i++) {
                final JSONObject botObject = botObjects.getJSONObject(i);
                final Bot bot = new Bot();
                bot.setType(getString(botObject.getString(Tags.TYPE)));

                final JSONArray itemObjects = botObject.getJSONArray(Tags.ITEMS);
                for (int j = 0; j < itemObjects.length(); j++) {
                    final JSONObject itemObj = itemObjects.getJSONObject(j);
                    final Item item = new Item();
                    item.setPointX(itemObj.getInt(Tags.POINT_X));
                    item.setPointY(itemObj.getInt(Tags.POINT_Y));
                    item.setNextPointX(itemObj.getInt(Tags.NEXT_POINT_X));
                    item.setNextPointX(itemObj.getInt(Tags.NEXT_POINT_Y));
                    bot.addItem(item);
                }
                bots.add(bot);
            }
        } catch (Exception e) {
            Log.e("TestPars: ", e.getLocalizedMessage());
        }

    }

    private String getString(String str) {
        if (TextUtils.isEmpty(str) || str.equals("null")) {
            return "";
        } else return str;
    }

    private interface Tags {

        String BOTS = "bots";

        String TYPE = "type";
        String ITEMS = "items";

        String POINT_X = "point_x";
        String POINT_Y = "point_y";
        String NEXT_POINT_X = "next_point_x";
        String NEXT_POINT_Y = "next_point_y";
    }
}
