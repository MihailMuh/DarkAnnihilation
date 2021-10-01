package ru.warfare.darkannihilation.base;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_BULLETS_ENEMY;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;

public abstract class BaseEnemy extends Sprite {
    public final Vector vector = new Vector();

    protected BaseEnemy(Game g, Bitmap bitmap, int dmg) {
        super(g, bitmap);

        damage = dmg;
        lock = true;
    }

    public abstract void shoot();

    public void bulletEnemy(int endX, int endY, int power) {
        int X = centerX();
        int Y = centerY();
        int[] values = vector.vector(X, Y, endX, endY, power);
        
        for (int i = 0; i < NUMBER_BULLETS_ENEMY; i++) {
            if (Game.bulletsEnemy[i].lock) {
                Game.bulletsEnemy[i].start(X, Y, values[2], values[0], values[1]);
                break;
            }
        }
    }

    public void bulletEnemy(int X, int Y, int[] values) {
        for (int i = 0; i < NUMBER_BULLETS_ENEMY; i++) {
            if (Game.bulletsEnemy[i].lock) {
                Game.bulletsEnemy[i].start(X, Y, values[2], values[0], values[1]);
                break;
            }
        }
    }
}
