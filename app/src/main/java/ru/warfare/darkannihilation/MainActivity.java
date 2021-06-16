package ru.warfare.darkannihilation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public Game game;
    public SharedPreferences preferences;
    public static GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.darkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = findViewById(R.id.gameView);

        Service.init(this);
        runOnUiThread(new Thread(() -> {
            ImageHub.init(this);
            AudioHub.init(this);
            ClientServer.getStatistics();
            gif = findViewById(R.id.gifView);
        }));

        Clerk.init(this);
        checkOnFirstRun();
        game.init(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullscreen();
        game.onResume();
    }

    private boolean isOnline() {
        return ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    @SuppressLint("InflateParams")
    private void checkOnFirstRun() {
        preferences = getSharedPreferences("ru.warfare.darkannihilation", MODE_PRIVATE);

        if (preferences.getBoolean("firstrun", true)) {
            LayoutInflater li = LayoutInflater.from(this);
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);

            if (isOnline()) {
                View promptsView = li.inflate(R.layout.dialog, null);

                AlertDialog alertDialog = mDialogBuilder
                        .setView(promptsView)
                        .setCancelable(false)
                        .setPositiveButton("Apply", null)
                        .create();

                alertDialog.setOnShowListener(dialog -> {
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(view -> {
                        String nick = ((EditText) promptsView.findViewById(R.id.input_text)).getText().toString();
                        if (nick.length() == 0) {
                            Toast toast = Toast.makeText(this, "Nickname must be notnull!", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            if (ClientServer.info_from_server.has(nick)) {
                                Toast toast = Toast.makeText(this, "This nickname already exists", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                preferences.edit().putBoolean("firstrun", false).apply();

                                new Thread(() -> {
                                    String[] str = nick.split(" ");
                                    StringBuilder stringBuilder = new StringBuilder();
                                    ArrayList<String> filterNick = new ArrayList<>(0);
                                    for (String s : str) {
                                        if (!s.equals("")) {
                                            filterNick.add(s);
                                        }
                                    }

                                    int len = filterNick.size();
                                    if (len > 1) {
                                        for (int i = 0; i < len; i++) {
                                            stringBuilder.append(filterNick.get(i));
                                            if (i != len - 1) {
                                                stringBuilder.append(" ");
                                            }
                                        }
                                    } else {
                                        stringBuilder.append(filterNick.toString()).deleteCharAt(0).deleteCharAt(stringBuilder.length()-1);
                                    }

                                    Clerk.nickname = stringBuilder.toString();
                                    Clerk.saveNickname();
                                    ClientServer.postBestScore(Clerk.nickname, 0);
                                }).start();

                                Toast toast = Toast.makeText(this, "Congratulations! You have registered!", Toast.LENGTH_LONG);
                                toast.show();
                                dialog.dismiss();
                            }
                        }
                    });
                });
                alertDialog.show();
            } else {
                AlertDialog alertDialog = mDialogBuilder.setView(li.inflate(R.layout.warning, null))
                        .setCancelable(false)
                        .setPositiveButton("Exit", null)
                        .setNegativeButton("Later", null)
                        .setNeutralButton("I enabled internet and want to register", null)
                        .create();

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

    private void fullscreen() {
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
}
