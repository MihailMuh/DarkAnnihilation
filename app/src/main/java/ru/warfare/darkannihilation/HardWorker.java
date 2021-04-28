package ru.warfare.darkannihilation;

import android.util.Log;

public class HardWorker implements Runnable {
    private Thread thread;
    private volatile boolean work = false;
    static volatile int makeAngle = 0;
    static volatile int makeBomb = 0;
    static int x = 0;
    static int y = 0;
    private final Game game;
    private final Vector vector = new Vector();

    public HardWorker(Game g) {
        game = g;
    }

    @Override
    public void run() {
        while (work) {
            if (makeAngle == 1) {
                vector.makeVector(x, y, game.player.x + game.player.halfWidth,
                        game.player.y + game.player.halfHeight, 13);
                game.allSprites.add(new BulletEnemy(game, x, y , vector.getAngle(), vector.getSpeedX(), vector.getSpeedY()));
                makeAngle = 0;
            }
            if (makeBomb == 1) {
                game.allSprites.add(new Bomb(game, game.demoman.x + game.demoman.halfWidth, game.demoman.y + game.demoman.halfHeight));
                makeBomb = 0;
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
            makeAngle = 0;
            makeBomb = 0;
            thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "" + e);
        }
    }
}
