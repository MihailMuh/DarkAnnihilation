package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Sprite.randInt;

public class HardThread implements Runnable {
//    1 - bullets enemy
//    2 - saturn second gun
//    3 - sunrise
//    4 - bomb
//    5 - saturn first gun

    private static final Vector vector = new Vector();
    private Thread thread;
    private final Game game;
    private boolean work = false;

    public static volatile int job = 0;
    public static volatile int x = 0;
    public static volatile int y = 0;

    public HardThread(Game g) {
        game = g;
    }

    @Override
    public void run() {
        while (work) {
            switch (job)
            {
                case 1:
                    vector.makeVector(x, y, game.player.centerX(), game.player.centerY(), 13);
                    AudioPlayer.playShotgun();
                    Game.allSprites.add(new BulletEnemy(x, y , vector.getAngle(), vector.getSpeedX(), vector.getSpeedY()));
                    job = 0;
                    break;
                case 2:
                    BuckshotSaturn buckshotSaturn = new BuckshotSaturn(game, game.player.centerX(), game.player.y);
                    Game.bullets.add(buckshotSaturn);
                    Game.allSprites.add(buckshotSaturn);
                    job = 0;
                    break;
                case 3:
                    AudioPlayer.playDeagle();
                    Game.allSprites.add(new BulletEnemy(x, y, 0, 0, -10));
                    Game.allSprites.add(new BulletEnemy(x, y, 90, 10, 0));
                    Game.allSprites.add(new BulletEnemy(x, y, 180, 0, 10));
                    Game.allSprites.add(new BulletEnemy(x, y, -90, -10, 0));

                    Game.allSprites.add(new BulletEnemy(x, y, 45, 7, -7));
                    Game.allSprites.add(new BulletEnemy(x, y, 135, 7, 7));
                    Game.allSprites.add(new BulletEnemy(x, y, -45, -7, -7));
                    Game.allSprites.add(new BulletEnemy(x, y, -135, -7, 7));

                    Game.allSprites.add(new BulletEnemy(x, y, 67, 10, -4));
                    Game.allSprites.add(new BulletEnemy(x, y, 22, 4, -10));
                    Game.allSprites.add(new BulletEnemy(x, y, -67, -10, -4));
                    Game.allSprites.add(new BulletEnemy(x, y, -22, -4, -10));

                    Game.allSprites.add(new BulletEnemy(x, y, 157, 4, 10));
                    Game.allSprites.add(new BulletEnemy(x, y, 113, 10, 4));
                    Game.allSprites.add(new BulletEnemy(x, y, -157, -4, 10));
                    Game.allSprites.add(new BulletEnemy(x, y, -113, -10, 4));

                    job = 0;
                    break;
                case 4:
                    Game.allSprites.add(new Bomb(game.demoman.centerX(), game.demoman.centerY()));
                    job = 0;
                    break;
                case 5:
                    for (int i = 0; i < randInt(2, 6); i++) {
                        BulletSaturn bulletSaturn = new BulletSaturn(game.player.centerX(), game.player.y);
                        Game.bullets.add(bulletSaturn);
                        Game.allSprites.add(bulletSaturn);
                    }
                    AudioPlayer.playShoot();
                    job = 0;
                    break;
                default:
                    break;
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
