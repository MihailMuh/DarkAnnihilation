package ru.warfare.darkannihilation.enemy.boss;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBoss;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletBoss;
import ru.warfare.darkannihilation.enemy.TripleFighter;
import ru.warfare.darkannihilation.math.Randomize;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BOSS_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.BOSS_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_VADER;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class DeathStar extends BaseBoss {
    public DeathStar(Game g) {
        super(g, ImageHub.bossImage, BOSS_HEALTH, 10, BOSS_SHOOT_TIME);

        recreateRect(x + 20, y + 20, right() - 20, bottom() - 20);
    }

    @Override
    public void shoot() {
        int ri = right() - 115;
        int y40 = y + 40;
        for (int i = 1; i < 4; i++) {
            game.intersectOnlyPlayer.add(new BulletBoss(game, ri, y40, i));
        }
        AudioHub.playShoot();
    }

    @Override
    public void kill() {
        Game.score += 325;

        int len = NUMBER_VADER / 4;
        for (int i = 0; i < len; i++) {
            if (Randomize.randFloat() <= 0.1) {
                game.enemies.add(new TripleFighter(game));
            } else {
                game.generateVader();
            }
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 20, y + 20);
    }

    @Override
    public void update() {
        if (y >= 35) {
            x += speedX;
            if (x < -width) {
                if (Randomize.randBoolean()) {
                    speedX = -speedX;
                } else {
                    x = SCREEN_WIDTH;
                }
            }
            if (x > SCREEN_WIDTH) {
                if (Randomize.randBoolean()) {
                    speedX = -speedX;
                } else {
                    x = -width;
                }
            }
            if (!startShoot) {
                startShoot = true;
                gameTask.start();
            }

        } else {
            super.update();

            y += speedY;
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, null);

        super.render();
    }
}
