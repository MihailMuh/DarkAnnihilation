package com.warfare.darkannihilation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.warfare.darkannihilation.systemd.MainGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
        config.width = 1560;
        config.height = 720;
        config.useGL30 = true;
        config.audioDeviceSimultaneousSources = 32;

        new LwjglApplication(new MainGame(), config);
    }
}
