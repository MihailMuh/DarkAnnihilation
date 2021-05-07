package ru.warfare.darkannihilation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import static ru.warfare.darkannihilation.ImageHub.isFilter;
import static ru.warfare.darkannihilation.ImageHub.portalImages;
import static ru.warfare.darkannihilation.ImageHub.screensSizeX;
import static ru.warfare.darkannihilation.ImageHub.thunderScreen;
import static ru.warfare.darkannihilation.ImageHub.winScreenImg;

public class ImageLoader implements Runnable {
    private Thread thread;

    private final Game game;
    private final Context context;

    private final int portalSize;

    public ImageLoader(Game g) {
        game = g;
        context = g.context;
        portalSize = (int) (300 * game.resizeK);

        try {
            thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "" + e);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i < 20) {
                portalImages[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                        context.getResources().getIdentifier("portal0" + i, "drawable", context.getPackageName())),
                        portalSize, portalSize, isFilter);

                thunderScreen[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                        context.getResources().getIdentifier("thunder" + i, "drawable", context.getPackageName())),
                        screensSizeX, game.screenHeight, isFilter);
            }
            winScreenImg[i] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                    context.getResources().getIdentifier("win_" + (i + 1), "drawable", context.getPackageName())),
                    game.screenWidth, game.screenHeight, isFilter);
        }

        try {
            thread.join();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "Thread join " + e);
        }
    }
}
