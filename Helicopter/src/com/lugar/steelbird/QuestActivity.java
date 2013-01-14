package com.lugar.steelbird;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lugar.steelbird.model.ChipOnMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestActivity extends Activity {

    private int widthDisplay;
    private int heightDisplay;

    private RelativeLayout relativeLayout;
    private LinearLayout layoutQuests;
    private LinearLayout layoutSubLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quests);

        Display display = getWindowManager().getDefaultDisplay();
        widthDisplay = display.getWidth();
        heightDisplay = display.getHeight();

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutMap);
        layoutQuests = (LinearLayout) findViewById(R.id.layoutLocationID);
        layoutSubLocation = (LinearLayout) findViewById(R.id.layoutSubLocation);

        List<ChipOnMap> chips = readLocations();

        for (ChipOnMap chipOnMap : chips) {
            Bitmap chipBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chip);
            Bitmap chipSelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chip_selected);

            StateListDrawable drawList = new StateListDrawable();
            drawList.addState(new int[]{android.R.attr.state_pressed}, new BitmapDrawable(getResizeBitmap(chipSelectedBitmap, heightDisplay / 12, heightDisplay / 12)));
            drawList.addState(new int[]{android.R.attr.state_enabled}, new BitmapDrawable(getResizeBitmap(chipBitmap, heightDisplay / 12, heightDisplay / 12)));

            View item = getLayoutInflater().inflate(R.layout.chip, relativeLayout, false);
            final ImageView imageView = (ImageView) item.findViewById(R.id.imageChip);
            imageView.setImageDrawable(drawList);
            imageView.setScaleType(ImageView.ScaleType.CENTER);

            TextView textView = (TextView) item.findViewById(R.id.textChip);
            textView.setText(chipOnMap.getTitle());
            textView.setTextSize(heightDisplay / 30);

            final String[] subLocations = getSubLocations(chipOnMap.getID());
            item.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        imageView.setPressed(true);
                    }
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        imageView.setPressed(false);
                        openSubLocations(subLocations);
                    }
                    return true;
                }
            });

            RelativeLayout.LayoutParams chipParams = (RelativeLayout.LayoutParams) item.getLayoutParams();
            chipParams.leftMargin = chipOnMap.getX() * widthDisplay / 100;
            chipParams.topMargin = chipOnMap.getY() * heightDisplay / 100;
            item.setLayoutParams(chipParams);
            relativeLayout.addView(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (relativeLayout.isShown()) {
            super.onBackPressed();
        } else {
            layoutSubLocation.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    private void openSubLocations(String[] subLocations) {
        relativeLayout.setVisibility(View.GONE);
        layoutSubLocation.setVisibility(View.VISIBLE);
        LinearLayout inflate = null;
        for (int i = 0; i < subLocations.length; i++) {
            if (i % 5 == 0) {
                inflate = (LinearLayout) getLayoutInflater().inflate(R.layout.item_location, layoutQuests, false);
                final View item = getLayoutInflater().inflate(R.layout.button_location, inflate, false);
                addButtonToLayer(item, subLocations[i], i, true, subLocations.length);
                addItemToInflate(inflate, item, true);
            } else {
                final View item = getLayoutInflater().inflate(R.layout.button_location, inflate, false);
                addButtonToLayer(item, subLocations[i], i, true, subLocations.length);
                addItemToInflate(inflate, item, false);
            }
        }
    }

    private String[] getSubLocations(int id) {
        switch (id) {
            case 0:
                return getResources().getStringArray(R.array.Tutorial);
        }
        return null;
    }

    private View addButtonToLayer(View item, final String jsonFile, final int i, boolean open, final int finished) {
        final Button button = (Button) item.findViewById(R.id.buttonLocation);

        if (open) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button.setEnabled(finished >= i);
                    if (finished >= i) {
                        button.setText(String.valueOf(i + 1));
                    } else {
                        button.setBackgroundResource(R.drawable.chip_1_selected);
                    }
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(QuestActivity.this, GameActivity.class);
                    intent.putExtra("file", jsonFile);
                    startActivity(intent);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button.setEnabled(false);
                    button.setBackgroundResource(R.drawable.chip_1_selected);
                }
            });
        }
        return item;
    }

    private void addItemToInflate(final LinearLayout inflate, final View item, final boolean newRow) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (newRow) {
                    inflate.addView(item);
                    layoutQuests.addView(inflate);
                } else {
                    inflate.addView(item);
                }
            }
        });
    }

    private List<ChipOnMap> readLocations() {
        List<ChipOnMap> chips = new ArrayList<ChipOnMap>();
        try {
            InputStream input = getResources().openRawResource(R.raw.locations1);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String text = new String(buffer);
            JSONArray jsonArray = new JSONArray(text);
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject itemObj = jsonArray.getJSONObject(i);
                final ChipOnMap chipOnMap = new ChipOnMap();
                chipOnMap.setID(itemObj.getInt(Tags.ID));
                chipOnMap.setTitle(getString(itemObj.getString(Tags.TITLE)));
                chipOnMap.setX(itemObj.getInt(Tags.X));
                chipOnMap.setY(itemObj.getInt(Tags.Y));
                chips.add(chipOnMap);
            }
        } catch (Exception je) {
            Log.d("Error w/file: ", je.getMessage());
        }
        return chips;
    }

    private String getString(String str) {
        if (TextUtils.isEmpty(str) || str.equals("null")) {
            return "";
        } else return str;
    }

    private Bitmap getResizeBitmap(Bitmap bm, int newWidth, int newHeight) {
        Resources r = getResources();
        int width = bm.getWidth();
        int height = bm.getHeight();
        float newWidthPX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newWidth, r.getDisplayMetrics());
        float newHeightPX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newHeight, r.getDisplayMetrics());
        float scaleWidth = newWidthPX / width;
        float scaleHeight = newHeightPX / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    private interface Tags {
        String ID = "id";
        String TITLE = "title";
        String X = "x";
        String Y = "y";
    }
}
