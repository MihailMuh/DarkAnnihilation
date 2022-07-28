package com.warfare.darkannihilation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.warfare.darkannihilation.systemd.MainGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
//        config.width = 300;
//        config.height = 900;
        config.fullscreen = true;
        config.useGL30 = true;
        config.audioDeviceSimultaneousSources = 32;
        config.foregroundFPS = 0;
        config.backgroundFPS = 0;

        new LwjglApplication(new MainGame(), config);
    }
}
