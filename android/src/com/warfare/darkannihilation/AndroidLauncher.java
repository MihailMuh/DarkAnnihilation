package com.warfare.darkannihilation;

import static android.hardware.SensorManager.SENSOR_DELAY_FASTEST;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.warfare.darkannihilation.systemd.MainGame;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//        config.numSamples = 2;
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        config.hideStatusBar = true;
        config.useImmersiveMode = true;
        config.touchSleepTime = 0;
        config.sensorDelay = SENSOR_DELAY_FASTEST;

        initialize(new MainGame(), config);
    }
}
