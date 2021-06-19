package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Sprite.getDistance;
import static ru.warfare.darkannihilation.Sprite.randInt;

public class HardThread implements Runnable {
//    1 - bullets enemy
//    2 - sunrise
//    3 - bomb
//    4 - saturn first gun
//    5 - stop buffer
//    6 - start buffer
//    7 - atomic bomb
//    8 - xwing
//    9 - dynamite

    private static final Vector vector = new Vector();
    private Thread thread;
    private final Game game;
    private volatile boolean work = false;

    public static volatile int job = 0;
    public static volatile int x = 0;
    public static volatile int y = 0;
    private volatile int r;

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
                    AudioHub.playShotgun();
                    Game.allSprites.add(new BulletEnemy(x, y , vector.getAngle(), vector.getSpeedX(), vector.getSpeedY()));
                    job = 0;
                    break;
                case 2:
                    AudioHub.playDeagle();
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
                case 3:
                    Game.allSprites.add(new Bomb(game.demoman.centerX(), game.demoman.centerY()));
                    job = 0;
                    break;
                case 4:
                    for (int i = 0; i < randInt(2, 6); i++) {
                        BulletSaturn bulletSaturn = new BulletSaturn(game.player.centerX(), game.player.y);
                        Game.bullets.add(bulletSaturn);
                        Game.allSprites.add(bulletSaturn);
                    }
                    AudioHub.playShoot();
                    job = 0;
                    break;
                case 5:
                    for (int i = 0; i < Game.allSprites.size(); i++) {
                        Sprite sprite = Game.allSprites.get(i);
                        if ((!sprite.isPassive && !sprite.isBullet)) {
                            Game.allSprites.get(i).sB();
                        }
                    }
                    job = 0;
                    break;
                case 6:
                    for (int i = 0; i < Game.allSprites.size(); i++) {
                        Sprite sprite = Game.allSprites.get(i);
                        if ((!sprite.isPassive && !sprite.isBullet)) {
                            Game.allSprites.get(i).buff();
                        }
                    }
                    job = 0;
                    break;
                case 7:
                    for (int i = 0; i < Game.allSprites.size(); i++) {
                        Sprite sprite = Game.allSprites.get(i);
                        if (sprite != game.atomicBomb) {
                            if (!sprite.lock) {
                                if ((!sprite.isPassive && !sprite.isBullet) | (sprite.status.equals("bulletEnemy"))) {
                                    sprite.intersection();
                                }
                            }
                        }
                    }
                    job = 0;
                    break;
                case 8:
                    int X = game.player.centerX();
                    int Y = game.player.centerY();
                    if (getDistance(x - X, y - Y) < r) {
                        vector.makeVector(x, y, X, Y, 9);
                        AudioHub.playShoot();
                        Game.allSprites.add(new BulletEnemy(x, y, vector.getAngle(), vector.getSpeedX(), vector.getSpeedY()));
                    }
                    job = 0;
                    break;
                case 9:
                    AudioHub.playDynamite();
                    BulletDynamite bulletDynamite = new BulletDynamite(game.player.centerX(), game.player.y);
                    Game.bullets.add(bulletDynamite);
                    Game.allSprites.add(bulletDynamite);
                    job = 0;
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
        if (Game.character.equals("saturn")) {
            r = 600;
        } else {
            r = 350;
        }
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
