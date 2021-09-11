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

import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseActivity;
import ru.warfare.darkannihilation.systemd.service.Clerk;
import ru.warfare.darkannihilation.systemd.service.ClientServer;
import ru.warfare.darkannihilation.systemd.service.Fonts;
import ru.warfare.darkannihilation.systemd.service.Service;
import ru.warfare.darkannihilation.systemd.service.Vibrator;
import ru.warfare.darkannihilation.systemd.service.Windows;

public final class MainActivity extends BaseActivity {
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
//        pauseBanner = findViewById(R.id.pauseAdMob);
//        pauseBanner.setAdUnitId(ADMOB_ID);
//        pauseBanner = null;
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
                HardThread.doInPool(ClientServer::getStatistics);

                View view = li.inflate(R.layout.dialog, null);

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setView(view)
                        .setCancelable(false)
                        .setPositiveButton("Apply", null)
                        .create();

                alertDialog.setOnShowListener(dialog ->
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(xView -> {
                            String[] nick = (((EditText) view.findViewById(R.id.input_text)).getText().toString() + " ").split(" ");
                            StringBuilder stringBuilder = new StringBuilder();

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
                                        HardThread.doInPool(() -> {
                                            Clerk.saveNickname();
                                            ClientServer.postBestScore(Clerk.nickname, 0);
                                        });

                                        preferences.edit().putBoolean("firstRun", false).apply();
                                        makeToast("Congratulations! You have got registered!", true);
                                        alertDialog.dismiss();
                                    }
                                }
                            }
                        }));
                alertDialog.show();
            } else {
                new AlertDialog.Builder(this)
                        .setView(li.inflate(R.layout.warning, null))
                        .setCancelable(false)
                        .setNegativeButton("Exit", (dialogInterface, i) -> Service.systemExit())
                        .setPositiveButton("Later", null)
                        .setNeutralButton("I enabled internet and want to register", (dialogInterface, i) -> checkOnFirstRun())
                        .create()
                        .show();
            }
        }
    }
}
