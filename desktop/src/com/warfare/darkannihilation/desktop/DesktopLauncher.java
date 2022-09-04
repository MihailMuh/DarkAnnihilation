package com.warfare.darkannihilation.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.warfare.darkannihilation.systemd.MainGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setAudioConfig(32, 512, 9);
        new Lwjgl3Application(new MainGame(), config);
    }
}
