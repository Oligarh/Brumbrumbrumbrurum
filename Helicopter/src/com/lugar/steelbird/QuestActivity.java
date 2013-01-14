package com.lugar.steelbird;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lugar.steelbird.model.ChipOnMap;
import com.lugar.steelbird.model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestActivity extends Activity {

    private int widthDisplay;
    private int heightDisplay;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quests);

        Display display = getWindowManager().getDefaultDisplay();
        widthDisplay = display.getWidth();
        heightDisplay = display.getHeight();

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutMap);

        List<ChipOnMap> chips = readLocations();

//        List<ChipOnMap> chips = new ArrayList<ChipOnMap>();
//        chips.add(new ChipOnMap(2, 10, "Золото Аляски"));
//        chips.add(new ChipOnMap(20, 20, "Тренировочная база"));
//        chips.add(new ChipOnMap(20, 70, "Бермудский треугольник"));
//        chips.add(new ChipOnMap(40, 25, "Средиземноморье"));
//        chips.add(new ChipOnMap(70, 85, "Тайна Антарктиды"));

        for (ChipOnMap chipOnMap : chips) {
            Bitmap chipBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chip_1);
            Bitmap chipSelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chip_1_selected);

            StateListDrawable drawList = new StateListDrawable();
            drawList.addState(new int[]{android.R.attr.state_pressed}, new BitmapDrawable(getResizeBitmap(chipSelectedBitmap, heightDisplay / 12, heightDisplay / 12)));
            drawList.addState(new int[]{android.R.attr.state_enabled}, new BitmapDrawable(getResizeBitmap(chipBitmap, heightDisplay / 12, heightDisplay / 12)));

            View item = getLayoutInflater().inflate(R.layout.chip, relativeLayout, false);
            final ImageView imageView = (ImageView) item.findViewById(R.id.imageChip);

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        imageView.setPressed(true);
                    }
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        imageView.setPressed(false);
                    }
                    return true;
                }
            });

            imageView.setImageDrawable(drawList);
            imageView.setScaleType(ImageView.ScaleType.CENTER);

            TextView textView = (TextView) item.findViewById(R.id.textChip);

            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        imageView.setPressed(true);
                    }
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        imageView.setPressed(false);
                    }
                    return true;
                }
            });

            textView.setText(chipOnMap.getTitle());
            textView.setTextSize(heightDisplay / 30);

            RelativeLayout.LayoutParams chipParams = (RelativeLayout.LayoutParams) item.getLayoutParams();
            chipParams.leftMargin = chipOnMap.getX() * widthDisplay / 100;
            chipParams.topMargin = chipOnMap.getY() * heightDisplay / 100;
            item.setLayoutParams(chipParams);
            relativeLayout.addView(item);
        }

    }

    private List<ChipOnMap> readLocations() {
        List<ChipOnMap> chips = new ArrayList<ChipOnMap>();
        try {
            InputStream input = getResources().openRawResource(R.raw.locations);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String text = new String(buffer);
            JSONArray jsonArray = new JSONArray(text);
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject itemObj = jsonArray.getJSONObject(i);
                final ChipOnMap chipOnMap = new ChipOnMap();
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
        String TITLE = "title";
        String X = "x";
        String Y = "y";
    }
}
