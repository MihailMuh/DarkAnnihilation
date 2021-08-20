package ru.warfare.darkannihilation.systemd;

import static ru.warfare.darkannihilation.systemd.service.Service.makeToast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseActivity;
import ru.warfare.darkannihilation.systemd.service.Clerk;
import ru.warfare.darkannihilation.systemd.service.ClientServer;
import ru.warfare.darkannihilation.systemd.service.Service;
import ru.warfare.darkannihilation.systemd.service.Vibrator;
import ru.warfare.darkannihilation.systemd.service.Windows;

public final class MainActivity extends BaseActivity {
    public Game game;
    private GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gif = findViewById(R.id.gifView);
        gif.setBackgroundResource(R.drawable.background);

        HardThread.doInBackGround(ClientServer::getStatistics);

        game = findViewById(R.id.gameView);

        HardThread.doInBackGround(() -> {
            Service.init(this);

            Windows.init();
            ImageHub.init();
            AudioHub.init();
            Vibrator.init();
            game.init();

            runOnUiThread(this::checkOnFirstRun);
        });
    }

    public void newWinGif(GifDrawable gifDrawable) {
        gif = findViewById(R.id.gifView);
        gif.setImageDrawable(gifDrawable);
        gif.setVisibility(GifImageView.VISIBLE);
    }

    public void deleteWinGif() {
        gif.setVisibility(GifImageView.GONE);
        gif.setImageDrawable(null);
        gif = null;
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
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                        String nick = ((EditText) promptsView.findViewById(R.id.input_text)).getText().toString();
                        if (nick.length() == 0) {
                            makeToast("Nickname must be notnull!", true);
                        } else {
                            if (ClientServer.info_from_server.has(nick)) {
                                makeToast("This nickname already exists", true);
                            } else {
                                preferences.edit().putBoolean("firstrun", false).apply();

                                HardThread.doInBackGround(() -> {
                                    String[] str = nick.split(" ");
                                    StringBuilder stringBuilder = new StringBuilder();
                                    ArrayList<String> filterNick = new ArrayList<>(0);
                                    for (String s : str) {
                                        if (!s.equals("")) {
                                            filterNick.add(s);
                                        }
                                    }

                                    int len = filterNick.size();
                                    int len_1 = len - 1;
                                    if (len > 1) {
                                        for (int i = 0; i < len; i++) {
                                            stringBuilder.append(filterNick.get(i));
                                            if (i != len_1) {
                                                stringBuilder.append(" ");
                                            }
                                        }
                                    } else {
                                        stringBuilder.append(filterNick.toString()).deleteCharAt(0).deleteCharAt(stringBuilder.length() - 1);
                                    }

                                    Clerk.nickname = stringBuilder.toString();
                                    Clerk.saveNickname();
                                    ClientServer.postBestScore(Clerk.nickname, 0);
                                });

                                makeToast("Congratulations! You have got registered!", true);
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
}
