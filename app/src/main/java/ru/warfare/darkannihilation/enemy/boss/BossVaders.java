package ru.warfare.darkannihilation.enemy.boss;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBoss;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Randomize;
import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BOSS_VADERS_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.BOSS_VADERS_SHOOT_TIME;

public class BossVaders extends BaseBoss {
    private final Vector vector = new Vector();

    private boolean field = false;
    private boolean left = Randomize.randBoolean();
    private final int start;

    public BossVaders(Game game) {
        super(game, ImageHub.bossVadersImg, BOSS_VADERS_HEALTH, 5, BOSS_VADERS_SHOOT_TIME);

        start = game.PORTAL_ID + 1;

        recreateRect(x + 35, y + 20, right() - 35, bottom() - 20);
    }

    @Override
    public void shoot() {
        for (int i = start; i < game.NUMBER_MINIONS; i++) {
            Sprite sprite = game.enemy[i];
            if (sprite.lock) {
                int X = centerX();
                int Y = centerY();
                int[] values = vector.easyVector(X, Y, game.player.centerX(),
                        game.player.centerY(), 10);

                sprite.x = X;
                sprite.y = Y;
                sprite.speedX = values[0];
                sprite.speedY = values[1];
                sprite.lock = false;
                AudioHub.playBossShoot();
                break;
            }
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
        game.addEnemies(true);
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 35, y + 20);
    }

    @Override
    public void update() {
        super.update();

        if (y > 0 && !field) {
            field = true;
            gameTask.start();
        }
        if (field) {
            if (x <= 0) {
                left = true;
            }
            if (x >= screenWidthWidth) {
                left = false;
            }
            if ((y >= screenHeightHeight) || (y <= 0)) {
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
