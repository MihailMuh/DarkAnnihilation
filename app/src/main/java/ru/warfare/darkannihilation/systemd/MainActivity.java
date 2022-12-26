package ru.warfare.darkannihilation.systemd;

import static ru.warfare.darkannihilation.systemd.service.Service.makeToast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Collections;

import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseActivity;
import ru.warfare.darkannihilation.systemd.service.Clerk;
import ru.warfare.darkannihilation.systemd.service.ClientServer;
import ru.warfare.darkannihilation.systemd.service.Fonts;
import ru.warfare.darkannihilation.systemd.service.Service;
import ru.warfare.darkannihilation.systemd.service.Vibrator;
import ru.warfare.darkannihilation.systemd.service.Windows;
import ru.warfare.darkannihilation.thread.HardThread;

public class MainActivity extends BaseActivity {
    public Game game;
    private AdView pauseBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = findViewById(R.id.gameView);

        HardThread.doInBackGround(() -> {
            Service.init(this);

            Windows.init();
            ImageHub.init();

            HardThread.doInPool(ClientServer::getStatistics);
            AudioHub.init();
            Vibrator.init();
            Fonts.init();
            MobileAds.initialize(this, initializationStatus -> {
            });
            MobileAds.setRequestConfiguration(new RequestConfiguration.Builder()
                    .setTestDeviceIds(Collections.singletonList("5371A173E68885BC058877681D34AB6B")).build());

            game.init();

            runOnUiThread(this::checkOnFirstRun);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pauseBanner != null) {
            pauseBanner.pause();
        }
        game.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.onResume();
        if (pauseBanner != null) {
            pauseBanner.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        game.onPause();
        closeAdMob();
    }

    public void initAdMob() {
        pauseBanner = findViewById(R.id.pauseAdMob);
        pauseBanner.loadAd(new AdRequest.Builder().build());

        pauseBanner.setVisibility(View.VISIBLE);
    }

    public void closeAdMob() {
        if (pauseBanner != null) {
            pauseBanner.setVisibility(View.GONE);
            pauseBanner.destroy();
            pauseBanner = null;
        }
    }

    @SuppressLint("InflateParams")
    private void checkOnFirstRun() {
        preferences = getSharedPreferences("ru.warfare.darkannihilation", MODE_PRIVATE);

        if (preferences.getBoolean("firstRun", true)) {
            LayoutInflater li = LayoutInflater.from(this);

            if (isOnline()) {
                View alertView = li.inflate(R.layout.dialog_nick, null);
                StringBuilder stringBuilder = new StringBuilder();
                EditText editText = alertView.findViewById(R.id.input_nick);
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setView(alertView)
                        .setCancelable(false)
                        .create();

                alertView.findViewById(R.id.button_apply).setOnClickListener(view2 -> {
                    String[] nick = (editText.getText().toString() + " ").split(" ");

                    if (nick.length == 0) {
                        makeToast("Nickname must be notnull!", true);
                    } else {
                        if (nick.length > 20) {
                            makeToast("Your nickname is too long!", true);
                        } else {
                            for (String s : nick) {
                                if (s.length() != 0) {
                                    stringBuilder.append(s).append(" ");
                                }
                            }

                            Clerk.nickname = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();

                            if (ClientServer.info_from_server.has(Clerk.nickname)) {
                                makeToast("This nickname already exists", true);
                            } else {
                                alertDialog.dismiss();

                                HardThread.doInBackGround(() -> {
                                    Clerk.saveNickname();
                                    ClientServer.postBestScore(Clerk.nickname, 0);
                                });

                                preferences.edit().putBoolean("firstRun", false).apply();
                                makeToast("Congratulations! You have got registered!", true);
                            }
                        }
                    }
                });

                alertDialog.show();
            } else {
                View alertView = li.inflate(R.layout.warning_dialog, null);
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setView(alertView)
                        .setCancelable(false)
                        .create();

                alertView.findViewById(R.id.button_later).setOnClickListener(view2 -> alertDialog.dismiss());
                alertView.findViewById(R.id.button_exit).setOnClickListener(view2 -> Service.systemExit());
                alertView.findViewById(R.id.button_enable_internet).setOnClickListener(view1 -> {
                    alertDialog.dismiss();
                    checkOnFirstRun();
                });

                alertDialog.show();
            }
        }
    }
}
