package ru.warfare.darkannihilation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private Game game;
    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
        );

        AudioPlayer.init(this);

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        ImageHub.init(this, (double) size.x / 1920, size.x, size.y);

        ClientServer.getStatistics();
        Clerk.init(this);
        checkOnFirstRun();

        game = new Game(this, size.x, size.y);
        setContentView(game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        game.onPause();
        game.saveScore();
        AudioPlayer.releaseAP();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
        );
        game.onResume();
    }

    private boolean isOnline() {
        return ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    private void checkOnFirstRun() {
        preferences = getSharedPreferences("ru.warfare.darkannihilation", MODE_PRIVATE);

        if (preferences.getBoolean("firstrun", true)) {
            if (isOnline()) {
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.dialog, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);

                mDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView.findViewById(R.id.input_text);

                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Apply", null);

                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.setOnShowListener(dialog -> {
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(view -> {
                        String nick = userInput.getText().toString();
                        if (nick.length() == 0) {
                            Toast toast = Toast.makeText(this, "Nickname must be notnull!", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            if (ClientServer.info_from_server.has(nick)) {
                                Toast toast = Toast.makeText(this, "This nickname already exists", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                preferences.edit().putBoolean("firstrun", false).apply();
                                Clerk.nickname = nick;
                                Clerk.saveNickname();
                                ClientServer.postBestScore(nick, 0);
                                Toast toast = Toast.makeText(this, "Congratulations! You have registered!", Toast.LENGTH_LONG);
                                toast.show();
                                dialog.dismiss();
                            }
                        }
                    });
                });
                alertDialog.show();
            } else {
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.warning, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);

                mDialogBuilder.setView(promptsView);

                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Exit", null)
                        .setNegativeButton("Later", null)
                        .setNeutralButton("I enabled internet and want to register", null);

                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.setOnShowListener(dialog -> {
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(view -> System.exit(0));

                    Button buttonCancel = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    buttonCancel.setOnClickListener(view -> dialog.dismiss());

                    Button buttonRegister = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                    buttonRegister.setOnClickListener(view -> {
                        checkOnFirstRun();
                        dialog.dismiss();
                    });
                });
                alertDialog.show();
            }
        } else {
            Clerk.getNickname();
            try {
                if (Clerk.nickname.equals("")) {
                    preferences.edit().putBoolean("firstrun", true).apply();
                    checkOnFirstRun();
                } else {
                    preferences.edit().putBoolean("firstrun", false).apply();
                }
            } catch (Exception e) {
                preferences.edit().putBoolean("firstrun", true).apply();
                checkOnFirstRun();
            }
        }
    }
}
