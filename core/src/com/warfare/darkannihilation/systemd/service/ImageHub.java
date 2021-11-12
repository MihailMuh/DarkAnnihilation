package com.warfare.darkannihilation.systemd.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.StringBuilder;
import com.warfare.darkannihilation.GifDecoder;

public final class ImageHub {
    private static final AssetManager assetManager = new AssetManager();
    private static final StringBuilder stringBuilder = new StringBuilder();

    public static Animation<TextureRegion> starScreen;

    public static void load() {
        //Texture[] textures;
        //int i;

        //for (i = 0; i < 12; i++) {
        //    stringBuilder.length = 0;
        //    stringBuilder.append("star_screen/").append(i).append(".jpg");
        //    assetManager.load(stringBuilder.toString(), Texture.class);
        //}

        //while (assetManager.getProgress() != 1) {
        //    assetManager.update();
        //    print(assetManager.getProgress());
        //}

        //textures = new Texture[12];
        //for (i = 0; i < 12; i++) {
        //    stringBuilder.length = 0;
        //    stringBuilder.append("star_screen/").append(i).append(".jpg");
        //    textures[i] = assetManager.get(stringBuilder.toString());
        //}
        //starScreen = new Animation<>(0.04f, textures);

        starScreen = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("thunder.gif").read());
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
