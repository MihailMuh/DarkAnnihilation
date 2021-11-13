package com.warfare.darkannihilation.systemd.service;

import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.warfare.darkannihilation.GifDecoder;

public final class ImageHub {
    private static final AssetManager assetManager = new AssetManager();

    public static Animation<TextureRegion> starScreen;

    public static Texture millenniumFalcon;

    public static void load() {
        int count = 0;

        assetManager.load("players/ship.png", Texture.class);

        while (assetManager.getProgress() != 1) {
            assetManager.update();
            print(assetManager.getProgress());
            if (count < 1) {
                starScreen = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, "thunder.gif");
                count++;
            }
        }

        millenniumFalcon = assetManager.get("players/ship.png");
        millenniumFalcon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public static void dispose() {
        assetManager.dispose();
        dispose(starScreen);
    }

    private static void dispose(Animation<TextureRegion> animation) {
        if (animation != null) {
            animation.getKeyFrame(0).getTexture().dispose();
        }
    }
}
