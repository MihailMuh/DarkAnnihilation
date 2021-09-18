package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.arts.ImageHub;

import static ru.warfare.darkannihilation.constant.Colors.THUNDER_FIRST;
import static ru.warfare.darkannihilation.constant.Colors.THUNDER_SECOND;
import static ru.warfare.darkannihilation.constant.Constants.LIGHTNING_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.LIGHTNING_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_LIGHTNING_IMAGES;
import static ru.warfare.darkannihilation.constant.NamesConst.SUPER;
import static ru.warfare.darkannihilation.systemd.Game.now;

public class BulletThunder extends BaseBullet {
    private int frame = 0;
    private long lastShoot = now;

    public BulletThunder(Game game, int X, int Y) {
        super(game, ImageHub.thunderImage[0], X, Y, LIGHTNING_DAMAGE);

        y = Y - height;
        power = SUPER;
    }

    @Override
    public void update() {
        if (now - lastShoot > LIGHTNING_SHOOT_TIME) {
            lastShoot = now;
            frame++;

            if (frame == NUMBER_LIGHTNING_IMAGES) {
                game.bullets.remove(this);
            }
        }
    }

    @Override
    public void kill() {
    }

    @Override
    public void render() {
        switch (frame) {
            case 2:
                Game.canvas.drawColor(THUNDER_FIRST);
                break;
            case 3:
                Game.canvas.drawColor(THUNDER_SECOND);
                break;
            default:
                Game.canvas.drawBitmap(ImageHub.thunderImage[frame], x, y, null);
                break;
        }
    }
}
