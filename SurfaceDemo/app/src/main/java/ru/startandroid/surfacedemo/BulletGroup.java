package ru.startandroid.surfacedemo;

import android.graphics.Canvas;
import android.util.Log;

public class BulletGroup {
    public Player player;
    public Bullet[] bullets = new Bullet[0];
    public int numberBullet = 0;

    public BulletGroup(Player p) {
        player = p;
    }
    public BulletGroup() {}

    public void append(Bullet b) {
        Bullet[] newBullets = new Bullet[bullets.length + 1];
        for (int i = 0; i < numberBullet; i++) {
            newBullets[i] = bullets[i];
        }
        newBullets[newBullets.length - 1] = b;
        bullets = newBullets.clone();
        numberBullet += 1;
    }

    public void checkCollisions(Vader[] vaders, int i) {
        for (int j = 0; j < numberBullet; j++) {
            if (bullets[j] != null) {
                vaders[i].check_intersection(bullets[j].x, bullets[j].y, bullets[j].width, bullets[j].height);
            }
        }
    }

    public void update(Canvas canvas) {
        for (int i = 0; i < numberBullet; i++) {
            if (bullets[i] != null) {
                bullets[i].x -= player.speedX / 3;
                bullets[i].update(canvas);
                if (bullets[i].y < -10) {
                    bullets[i].bulletImage.recycle();
                    bullets[i] = null;
                }
            }
        }
//        Log.i("array", "" + bullets.length);
    }
}