package com.lugar.steelbird;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void play(View v) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void onBackPressed(View v) {
        onBackPressed();
    }
}
