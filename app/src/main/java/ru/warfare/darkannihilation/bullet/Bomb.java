package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BOMB_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BOMB_SPEED;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

public class Bomb extends BaseBullet {
    public Bomb(Game game) {
        super(game, ImageHub.bombImg, BOMB_DAMAGE);
        name = BULLET_ENEMY;
    }

    @Override
    public void start(int X, int Y) {
        x = X - halfWidth;
        y = Y;
        lock = false;
    }

    @Override
    public Object[] getBox(Bitmap bitmap) {
        return new Object[] {ImageHub.rotateBitmap(image, 180)};
    }

    @Override
    public void hide() {
        lock = true;
    }

    @Override
    public void kill() {
        super.kill();
        hide();
    }

    @Override
    public void intersectionPlayer() {
        kill();
    }

    @Override
    public void update() {
        y += BOMB_SPEED;

        if (y > SCREEN_HEIGHT) {
            hide();
        }
    }
}
