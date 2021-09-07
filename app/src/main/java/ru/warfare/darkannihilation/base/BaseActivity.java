package ru.warfare.darkannihilation.base;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import ru.warfare.darkannihilation.R;

public abstract class BaseActivity extends AppCompatActivity {
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.darkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fullscreen();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
        fullscreen();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onResume() {
        super.onStart();
        fullscreen();
    }

    public void fullscreen() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
        );
    }

    public boolean isOnline() {
        return ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
