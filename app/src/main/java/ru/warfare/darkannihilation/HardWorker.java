package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Sprite.getDistance;

public class HardWorker implements Runnable {
    private Thread thread;
    private boolean work = false;
    public static volatile int job = 0;
    public static final Vector vector = new Vector();

    public static volatile int x = 0;
    public static volatile int y = 0;
    public static volatile int distance = 0;

    private final Game game;

    public HardWorker(Game g) {
        game = g;
    }

    @Override
    public void run() {
        while (work) {
            if (job == 1) {
                int plX = game.player.centerX();
                int plY = game.player.centerY();
                if (getDistance(x - plX, y - plY) < distance) {
                    vector.makeVector(x, y, plX, plY, 9);
                    AudioPlayer.playShoot();
                    Game.allSprites.add(new BulletEnemy(x, y, vector.getAngle(), vector.getSpeedX(), vector.getSpeedY()));
                }
                job = 0;
            }
        }
    }

    public void workOnPause() {
        work = false;
        try {
            thread.join();
        } catch (Exception e) {
            Service.print("Thread join " + e);
        }
    }

    public void workOnResume() {
        try {
            work = true;
            job = 0;
            thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
            Service.print(e.toString());
        }
    }
}