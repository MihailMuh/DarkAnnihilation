package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BOMB_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BOMB_SPEED;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;

public class Bomb extends BaseBullet {
    public Bomb(Game game, int X, int Y) {
        super(game, ImageHub.bombImg, X, Y, BOMB_DAMAGE);
        name = BULLET_ENEMY;

        AudioHub.playFallingBomb();
    }

    @Override
    public Object[] getBox(int a, int b, Bitmap bitmap) {
        return new Object[] {ImageHub.rotateImage(image, 180)};
    }

    @Override
    public void update() {
        y += BOMB_SPEED;

        if (y > Game.screenHeight) {
            kill();
        }
    }
}