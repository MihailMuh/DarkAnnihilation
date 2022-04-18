package com.warfare.darkannihilation.player;

import static com.badlogic.gdx.math.MathUtils.roundPositive;
import static com.warfare.darkannihilation.constants.Constants.MILLENNIUM_FALCON_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.MILLENNIUM_FALCON_SHOOT_TIME;
import static com.warfare.darkannihilation.constants.Names.BULLET_ENEMY;
import static com.warfare.darkannihilation.constants.Names.FULL_HEART;
import static com.warfare.darkannihilation.constants.Names.HALF_HEART;
import static com.warfare.darkannihilation.constants.Names.NULL_HEART;
import static com.warfare.darkannihilation.constants.Names.PLAYER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Watch.time;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.sprite.MovementSprite;
import com.warfare.darkannihilation.pools.BulletPool;
import com.warfare.darkannihilation.systemd.DifficultyAnalyzer;
import com.warfare.darkannihilation.systemd.service.Processor;

public class Player extends MovementSprite {
    private final Array<Heart> hearts = new Array<>(true, 20, Heart.class);
    private final ArmorPool armorPool = new ArmorPool(hearts);
    private final DifficultyAnalyzer difficultyAnalyzer;

    private float lastShot;
    private float endX, endY;
    private int heartX = 25;
    private int heartY = SCREEN_HEIGHT - 90;
    private final int mxHealthMinus5;

    public Player(DifficultyAnalyzer difficultyAnalyzer) {
        super(getImages().millenniumFalcon, MILLENNIUM_FALCON_HEALTH, 10000, 0);
        this.difficultyAnalyzer = difficultyAnalyzer;

        name = PLAYER;
        mxHealthMinus5 = maxHealth - 5;
        health = maxHealth;

        int len = maxHealth / 10;
        for (int i = 0; i < len; i++) {
            hearts.add(new Heart(heartX, heartY));

            heartX += 90;
            checkLevel(true);
        }

        shrinkBorders(25, 30, 25, 25);

        reset();
    }

    private void changeHearts() {
        final Array<Heart> hearts = this.hearts;
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

    public void heal(int value) {
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
                armorPool.obtain(heartX, heartY);
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

            Processor.postToLooper(() -> {
                final float X = centerX();
                final float Y = top() + 5;
                final BulletPool bulletPool = getPools().bulletPool;

                bulletPool.obtain(X - 7, Y);
                bulletPool.obtain(X, Y);
                bulletPool.obtain(X + 7, Y);

                getSounds().laserSound.play();
            });
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

    public void damage(MovementSprite sprite) {
        Processor.post(() -> {
            damage(sprite.damage);
            changeHearts();

            if (sprite.name > BULLET_ENEMY) getSounds().metalSound.play();

            if (sprite.damage >= 20) Gdx.input.vibrate(120);
            else Gdx.input.vibrate(60);

            difficultyAnalyzer.addStatistics(health);
        });
    }

    @Override
    public void kill() {
        explodeHuge();
        Gdx.input.vibrate(150);
    }

    public void renderHearts() {
        for (Heart heart : hearts) heart.render();
    }

    @Override
    public void render() {
        if (visible) super.render();
    }
}
