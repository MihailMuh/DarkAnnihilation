package com.warfare.darkannihilation.player;

import static com.badlogic.gdx.math.MathUtils.roundPositive;
import static com.warfare.darkannihilation.constants.Constants.MILLENNIUM_FALCON_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.MILLENNIUM_FALCON_SHOOT_TIME;
import static com.warfare.darkannihilation.constants.Names.FULL_HEART;
import static com.warfare.darkannihilation.constants.Names.HALF_HEART;
import static com.warfare.darkannihilation.constants.Names.NULL_HEART;
import static com.warfare.darkannihilation.systemd.service.Watch.time;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.movement.MovementSprite;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Player extends MovementSprite {
    private final Array<Heart> hearts = new Array<>(true, 20, Heart.class);
    private final Sound sound;
    private final PoolWrap<Bullet> bulletPool;
    private final PoolWrap<Heart> armorPool = new PoolWrap<Heart>(hearts) {
        @Override
        protected ArmorHeart newObject() {
            return new ArmorHeart();
        }
    };

    private float lastShot;
    private float endX, endY;
    private int heartX = 25;
    private int heartY = SCREEN_HEIGHT - 90;
    private final int mxHealthMinus5;

    public Player(AtlasRegion texture, Sound sound, PoolWrap<Bullet> bulletPool, PoolWrap<Explosion> explosionPool) {
        super(explosionPool, texture, MILLENNIUM_FALCON_HEALTH, 10000, 0);
        this.bulletPool = bulletPool;
        this.sound = sound;
        mxHealthMinus5 = maxHealth - 5;
        health = maxHealth;

        int len = maxHealth / 10;
        for (int i = 0; i < len; i++) {
            hearts.add(new Heart(heartX, heartY));

            heartX += 90;
            checkLevel(true);
        }

        setIndents(20, 25, 20, 20);

//        Processor.post(() -> {
//            Service.sleep(5000);
//            while (true) {
//                heal(15);
//                Service.sleep(2000);
//            }
//        });
        reset();
    }

    private void changeHearts() {
        final int len = health / 10;
        int bar;

        for (bar = 0; bar < len; bar++) hearts.get(bar).setType(FULL_HEART);

        if (health % 10 == 5) hearts.get(bar++).setType(HALF_HEART);

        while (bar < hearts.size) {
            Heart heart = hearts.get(bar);
            heart.setType(NULL_HEART);

            if (!heart.visible) {
                hearts.removeIndex(bar);
                armorPool.free(heart);

                heartX -= 90;
                checkLevel(false);
            } else bar++;
        }
    }

    private void heal(int value) {
        int len = roundPositive(value / 10f);

        if (value % 10 == 5 && health % 10 == 5) len--;

        addArmorHeart(len);
        health += value;
        changeHearts();
    }

    private void addArmorHeart(int times) {
        int hlth = health;

        for (int i = 1; i <= times; i++) {
            hlth += i * 10;

            if (hlth >= mxHealthMinus5) {
                ((ArmorHeart) armorPool.obtain()).start(heartX, heartY);
                heartX += 90;

                checkLevel(true);
            }
        }
    }

    private void checkLevel(boolean up) {
        if (up) {
            if (heartX > 385) {
                heartX = 25;
                heartY -= 70;
            }
        } else {
            if (heartX < 25) {
                heartX = 385;
                heartY += 70;
            }
        }
    }

    public void shoot() {
        if (time - lastShot >= MILLENNIUM_FALCON_SHOOT_TIME) {
            lastShot = time;

            float X = centerX();
            float Y = top() + 5;
            bulletPool.obtain().start(X - 7, Y);
            bulletPool.obtain().start(X, Y);
            bulletPool.obtain().start(X + 7, Y);

            sound.play(0.17f);
        }
    }

    @Override
    public void reset() {
        x = HALF_SCREEN_WIDTH;
        y = HALF_SCREEN_HEIGHT;

        endX = (x -= halfWidth);
        endY = (y -= halfHeight);
    }

    public void setCoordinates(float X, float Y) {
        endX = X - halfWidth;
        endY = Y - halfHeight;
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        speedX = (endX - x) / 3f;
        speedY = (endY - y) / 3f;
    }

    @Override
    public boolean damage(int dmg) {
        health -= dmg;
        changeHearts();

        if (health <= 0) kill();

        return false;
    }

    @Override
    public void kill() {
        explodeHuge();
    }

    public void renderHearts() {
        for (Heart heart : hearts) heart.render();
    }
}
