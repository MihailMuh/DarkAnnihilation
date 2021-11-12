package com.warfare.darkannihilation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.warfare.darkannihilation.systemd.DarkGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL30 = true;

        new LwjglApplication(new DarkGame(), config);
    }
}
