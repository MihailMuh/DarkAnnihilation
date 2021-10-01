package ru.warfare.darkannihilation.enemy.boss;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBoss;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Randomize;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BOSS_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.BOSS_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_BOMBS;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_BOSS_SHOTS;
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
        int count = 0;
        for (int i = NUMBER_BOMBS; i < NUMBER_BOSS_SHOTS; i++) {
            if (Game.bulletsEnemy[i].lock) {
                count++;
                Game.bulletsEnemy[i].start(ri, y40, count);
            }
            if (count == 3) {
                break;
            }
        }
        AudioHub.playShoot();
    }

    @Override
    public void kill() {
        Game.score += 325;
        game.addEnemies(false);
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
