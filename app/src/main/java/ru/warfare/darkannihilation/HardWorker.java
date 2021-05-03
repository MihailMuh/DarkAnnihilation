package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HardWorker implements Runnable {
    private Thread thread;
    private volatile boolean work = false;
    static volatile boolean makeAngle = false;
    static volatile boolean makeBomb = false;
    static volatile boolean makeShotgun = false;
    static volatile boolean makeImages = false;
    public static final Vector vector = new Vector();

    static int x = 0;
    static int y = 0;
    static int id;
    static volatile int count = 0;
    private final Game game;

    public HardWorker(Game g) {
        game = g;
    }

    @Override
    public void run() {
        while (work) {
            if (makeBomb) {
                game.allSprites.add(new Bomb(game, game.demoman.x + game.demoman.halfWidth, game.demoman.y + game.demoman.halfHeight));
                makeBomb = false;
            }
            if (makeShotgun) {
                BuckshotGunner buckshotGunner = new BuckshotGunner(game, game.player.x + game.player.halfWidth, game.player.y);
                game.bullets.add(buckshotGunner);
                game.allSprites.add(buckshotGunner);
                makeShotgun = false;
            }
            if (makeImages) {
                if (count < 150) {
                    if (count < ImageHub.winScreenImg.length) {
                        id = game.context.getResources().getIdentifier("win_" + (count + 1), "drawable", game.context.getPackageName());
                        ImageHub.winScreenImg[count] = BitmapFactory.decodeResource(game.context.getResources(), id);
                        ImageHub.winScreenImg[count] = Bitmap.createScaledBitmap(ImageHub.winScreenImg[count], game.screenWidth, game.screenHeight, ImageHub.isFilter);
                    }
                    if (count < ImageHub.portalImages.length) {
                        id = game.context.getResources().getIdentifier("portal0" + count, "drawable", game.context.getPackageName());
                        ImageHub.portalImages[count] = BitmapFactory.decodeResource(game.context.getResources(), id);
                        ImageHub.portalImages[count] = Bitmap.createScaledBitmap(ImageHub.portalImages[count],
                                (int) (300 * game.resizeK), (int) (300 * game.resizeK), ImageHub.isFilter);
                        game.portal = new Portal(game);
                    }
                    if (count < ImageHub.thunderScreen.length) {
                        id = game.context.getResources().getIdentifier("thunder" + count, "drawable", game.context.getPackageName());
                        ImageHub.thunderScreen[count] = BitmapFactory.decodeResource(game.context.getResources(), id);
                        ImageHub.thunderScreen[count] = Bitmap.createScaledBitmap(ImageHub.thunderScreen[count], (int) (game.screenWidth * 1.4), game.screenHeight, ImageHub.isFilter);
                    }
                    count++;
                } else {
                    count = 0;
                    makeImages = false;
                }
            }
            if (makeAngle) {
                vector.makeVector(x, y, game.player.x + game.player.halfWidth,
                        game.player.y + game.player.halfHeight, 13);
                game.allSprites.add(new BulletEnemy(game, x, y , vector.getAngle(), vector.getSpeedX(), vector.getSpeedY()));
                makeAngle = false;
            }
        }
    }

    public void workOnPause() {
        work = false;
        try {
            thread.join();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "Thread join " + e);
        }
    }

    public void workOnResume() {
        try {
            work = true;
            makeAngle = false;
            makeBomb = false;
            makeShotgun = false;
            thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "" + e);
        }
    }
}
