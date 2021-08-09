package ru.warfare.darkannihilation.enemy.boss;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBoss;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletBossVaders;
import ru.warfare.darkannihilation.enemy.Vader;
import ru.warfare.darkannihilation.enemy.XWing;
import ru.warfare.darkannihilation.math.Randomize;
import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BOSS_VADERS_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.BOSS_VADERS_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_VADER;

public class BossVaders extends BaseBoss {
    private final Vector vector = new Vector();

    private boolean field = false;
    private boolean left = Randomize.randBoolean();

    public BossVaders(Game game) {
        super(game, ImageHub.bossVadersImg, BOSS_VADERS_HEALTH, 5);

        recreateRect(x + 35, y + 20, right() - 35, bottom() - 20);
    }

    @Override
    public void shoot() {
        if (System.currentTimeMillis() - lastShoot > BOSS_VADERS_SHOOT_TIME) {
            lastShoot = System.currentTimeMillis();
            HardThread.doInBackGround(() -> {
                int X = centerX();
                int Y = centerY();
                int[] values = vector.easyVector(X, Y, game.player.centerX(),
                        game.player.centerY(), 10);
                game.enemies.add(new BulletBossVaders(game, X, Y, values[0], values[1]));
                AudioHub.playBossShoot();
            });
        }
    }

    @Override
    public void onBuff() {
        speedX *= 2;
        speedY *= 2;
    }

    @Override
    public void onStopBuff() {
        speedX /= 2;
        speedY /= 2;
    }

    @Override
    public void kill() {
        Game.score += 400;

        int len = NUMBER_VADER / 4;
        for (int i = 0; i < len; i++) {
            if (Randomize.randFloat() <= 0.3) {
                game.enemies.add(new XWing(game));
            } else {
                game.enemies.add(new Vader(game, true));
            }
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 35, y + 20);
    }

    @Override
    public void update() {
        super.update();

        if (y > 0 & !field) {
            field = true;
        }
        if (y > -halfHeight) {
            shoot();
        }
        if (field) {
            if (x <= 0) {
                left = true;
            }
            if (x >= screenWidthWidth) {
                left = false;
            }
            if ((y >= screenHeightHeight) | (y <= 0)) {
                speedY = -speedY;
            }

            if (left) {
                x += speedX;
            } else {
                x -= speedX;
            }
        }
        y += speedY + 0.5;
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);

        super.render();
    }
}
