package com.warfare.darkannihilation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.warfare.darkannihilation.systemd.MainGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.backgroundFPS = 0;
        config.foregroundFPS = 0;

        new LwjglApplication(new MainGame(), config);
    }
}
