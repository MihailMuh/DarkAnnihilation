package ru.warfare.darkannihilation;

public class Minion extends Sprite {
    public int X;
    public int Y;
    private double angle;
    private static final int shootMinionTime = 1_000;
    private long lastShoot;
    private long now;

    public Minion(Game g, int FX) {
        super(g, ImageHub.minionImg.getWidth(), ImageHub.minionImg.getHeight());
        health = 2;

        x = randInt(FX, FX + ImageHub.factoryImg.getWidth());
        y = ImageHub.factoryImg.getHeight() - 100;
        speedX = randInt(-8, 8);
        speedY = randInt(2, 5);
        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shootMinionTime) {
            lastShoot = now;

            X = ((game.player.x + game.player.halfWidth) - (x + halfWidth)) / 50;
            Y = ((game.player.y + game.player.halfHeight) - (y + halfHeight)) / 50;
            angle = Math.toDegrees(Math.atan2(Y, X) + (Math.PI / 2));

            game.bulletEnemies.add(new BulletEnemy(game, x + halfWidth, y + halfHeight, angle, X, Y));
            game.numberBulletsEnemy += 1;
        }
    }

    @Override
    public void check_intersectionBullet(Bullet bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            game.bullets.remove(bullet);
            game.numberBullets -= 1;
            AudioPlayer.playBoom();
            game.minions.remove(this);
            game.numberMinions -= 1;
            for (int i = 0; i < numberDefaultExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
        }
    }

    public void check_intersectionBullet(Buckshot bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            AudioPlayer.playBoom();
            game.minions.remove(this);
            game.numberMinions -= 1;
            for (int i = 0; i < numberDefaultExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
        }
    }

    @Override
    public void check_intersectionPlayer() {
        if (x + 15 < game.player.x + 20 & game.player.x + 20 < x + width - 15 &
                y + 15 < game.player.y + 25 & game.player.y + 25 < y + height - 15 |
                game.player.x + 20 < x + 15 & x + 15 < game.player.x + game.player.width - 20 &
                        game.player.y + 25 < y + 15 & y + 15 < game.player.y + game.player.height - 20) {
            AudioPlayer.playMetal();
            game.minions.remove(this);
            game.numberMinions -= 1;
            game.player.damage(5);
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
        }
    }

    @Override
    public void update() {
        check_intersectionPlayer();

        x += speedX;
        y += speedY;

        shoot();

        if (x < -width | x > game.screenWidth | y > game.screenHeight) {
            game.minions.remove(this);
            game.numberMinions -= 1;
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.minionImg, x, y, null);
    }
}