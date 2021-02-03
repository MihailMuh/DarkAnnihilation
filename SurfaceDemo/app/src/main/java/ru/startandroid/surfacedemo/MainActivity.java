package ru.startandroid.surfacedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
<<<<<<< HEAD
import android.view.View;
=======
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    Game game;
<<<<<<< HEAD
=======
    public int screenWidth;
    public int screenHeight;
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
<<<<<<< HEAD
        this.getWindow().getDecorView().setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
        );
        setContentView(R.layout.activity_main);

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();

        game = findViewById(R.id.gameView);
        game.setScreenSizes(displaymetrics.widthPixels, displaymetrics.heightPixels);
=======
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;

        game = findViewById(R.id.gameView);
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.resume();
<<<<<<< HEAD
        getSupportActionBar().hide();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
        );
=======
>>>>>>> 14019d6d9fa5d2b258fffb9b7a082e1e65e32ff6
    }
}