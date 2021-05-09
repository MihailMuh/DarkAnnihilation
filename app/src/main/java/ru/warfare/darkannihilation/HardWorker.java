package ru.warfare.darkannihilation;

import android.content.Context;
import android.util.Log;

public class HardWorker implements Runnable {
//    1 - bullets enemy
//    2 - saturn
//    3 - sunrise
//    4 - bomb
    private Thread thread;
    private volatile boolean work = false;
    static volatile int job = 0;
    public static final Vector vector = new Vector();

    static int x = 0;
    static int y = 0;
    private final Game game;

    public HardWorker(Game g) {
        game = g;
    }

    @Override
    public void run() {
        while (work) {
            switch (job)
            {
                case 1:
                    vector.makeVector(x, y, game.player.x + game.player.halfWidth,
                            game.player.y + game.player.halfHeight, 13);
                    AudioPlayer.playShotgun();
                    game.allSprites.add(new BulletEnemy(game, x, y , vector.getAngle(), vector.getSpeedX(), vector.getSpeedY()));
                    job = 0;
                    break;
                case 2:
                    BuckshotSaturn buckshotSaturn = new BuckshotSaturn(game, game.player.x + game.player.halfWidth, game.player.y);
                    game.bullets.add(buckshotSaturn);
                    game.allSprites.add(buckshotSaturn);
                    job = 0;
                    break;
                case 3:
                    AudioPlayer.playDeagle();
                    game.allSprites.add(new BulletEnemy(game, x, y, 0, 0, -10));
                    game.allSprites.add(new BulletEnemy(game, x, y, 90, 10, 0));
                    game.allSprites.add(new BulletEnemy(game, x, y, 180, 0, 10));
                    game.allSprites.add(new BulletEnemy(game, x, y, -90, -10, 0));

                    game.allSprites.add(new BulletEnemy(game, x, y, 45, 7, -7));
                    game.allSprites.add(new BulletEnemy(game, x, y, 135, 7, 7));
                    game.allSprites.add(new BulletEnemy(game, x, y, -45, -7, -7));
                    game.allSprites.add(new BulletEnemy(game, x, y, -135, -7, 7));

                    game.allSprites.add(new BulletEnemy(game, x, y, 67, 10, -4));
                    game.allSprites.add(new BulletEnemy(game, x, y, 22, 4, -10));
                    game.allSprites.add(new BulletEnemy(game, x, y, -67, -10, -4));
                    game.allSprites.add(new BulletEnemy(game, x, y, -22, -4, -10));

                    game.allSprites.add(new BulletEnemy(game, x, y, 157, 4, 10));
                    game.allSprites.add(new BulletEnemy(game, x, y, 113, 10, 4));
                    game.allSprites.add(new BulletEnemy(game, x, y, -157, -4, 10));
                    game.allSprites.add(new BulletEnemy(game, x, y, -113, -10, 4));

                    job = 0;
                    break;
                case 4:
                    game.allSprites.add(new Bomb(game, game.demoman.x + game.demoman.halfWidth, game.demoman.y + game.demoman.halfHeight));
                    job = 0;
                    break;
                case 0:
                    break;
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
            job = 0;
            thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "" + e);
        }
    }
}
