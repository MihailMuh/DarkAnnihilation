package ru.warfare.darkannihilation;

import android.util.Log;

public class HardWorker implements Runnable {
    private Thread thread;
    private boolean work = false;
    static int typeWork = 0;
    static int x = 0;
    static int y = 0;
    static int X = 0;
    static int Y = 0;
    static int halfWidth = 0;
    static int halfHeight = 0;
    static double angle = 0;
    private final Game game;

    public HardWorker(Game g) {
        game = g;
    }

    @Override
    public void run() {
        while (work) {
            if (typeWork == 1) {
                X = ((game.player.x + game.player.halfWidth) - (x + halfWidth)) / 50;
                Y = ((game.player.y + game.player.halfHeight) - (y + halfHeight)) / 50;
                angle = Math.toDegrees(Math.atan2(Y, X) + (Math.PI / 2));
                game.bulletEnemies.add(new BulletEnemy(game, x + halfWidth, y + halfHeight, HardWorker.angle, HardWorker.X, HardWorker.Y));
                game.numberBulletsEnemy += 1;
                typeWork = 0;
            }
        }
    }

    public void workOnPause() {
        try {
            thread.join();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "Thread join " + e);
        }
        work = false;
    }

    public void workOnResume() {
        thread = new Thread(this);
        thread.start();
        work = true;
    }
}
