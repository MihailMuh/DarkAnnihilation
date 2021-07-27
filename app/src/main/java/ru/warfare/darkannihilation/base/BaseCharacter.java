package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import java.util.ArrayList;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.Vibrator;
import ru.warfare.darkannihilation.character.Heart;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.MILLENNIUM_FALCON_HEALTH;
import static ru.warfare.darkannihilation.constant.NamesConst.GUN;

public class BaseCharacter extends Sprite {
    public int endX;
    public int endY;
    public long lastShoot = System.currentTimeMillis();
    public long now;
    public boolean dontmove = false;
    public byte gun = GUN;
    public int maxHealth = MILLENNIUM_FALCON_HEALTH;
    public ArrayList<Heart> hearts = new ArrayList<>(0);
    public int[] types;
    public boolean god;
    private int bar;
    public int heartX = 25;
    public int heartY = 10;

    public BaseCharacter(Game g, Bitmap bitmap) {
        super(g, bitmap);

        init();

        for (int i = 0; i < 5; i++) {
            hearts.add(new Heart(g, heartX, heartY, false));
            heartX += 90;
        }
        newLevel();

        changeHearts();
    }

    public BaseCharacter(Game g, Bitmap bitmap, int maxHealth) {
        super(g, bitmap);

        this.maxHealth = maxHealth;

        init();
    }

    public void newStatus() {
        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;
        endX = x;
        endY = y;
        lock = true;
        god = false;
    }

    private void init() {
        newStatus();
        health = maxHealth;
    }

    public void checkIntersections(Sprite sprite) {
        if (intersect(sprite)) {
            damage(sprite.damage);
            sprite.intersectionPlayer();
        }
    }

    public void setCoords(int X, int Y) {
        endX = X - halfWidth;
        endY = Y - halfHeight;
    }

    public void newLevel() {
        heartX = 25;
        heartY += 10 + ImageHub.imageHeartHalf.getHeight();
    }

    public void killArmorHeart(Heart heart) {
        if (heartX != 25) {
            heartX -= 90;
        } else {
            heartX = 385;
            heartY -= 10 + ImageHub.imageHeartHalf.getHeight();
        }
        maxHealth = health;
        hearts.remove(heart);
    }

    public void addArmorHeart() {
        hearts.add(new Heart(game, heartX, heartY, true));
        heartX += 90;
        if (heartX > 385) {
            newLevel();
        }
    }

    public void heal() {
        if (health < 200) {
            baseHeal();
        }
        changeHearts();
    }

    public void baseHeal() {
        health += 15;
        int armor = health - maxHealth;
        if (armor > 0) {
            addArmorHeart();
            if (armor > 10) {
                addArmorHeart();
            }
            maxHealth = health;
        }
    }

    private void damage(int dmg) {
        if (dmg != 0 & !god) {
            health -= dmg;
            changeHearts();
            if (health <= 0) {
                HardThread.doInBackGround(() -> {
                    game.generateGameover();
                    Vibrator.vibrate(1300);
                    createSkullExplosion();
                });
            } else {
                Vibrator.vibrateInBackGround(60);
            }
        }
    }

    public void changeHearts() {
        types = new int[hearts.size()];
        int len = health / 10;

        for (bar = 0; bar < len; bar++) {
            types[bar] = 2;
        }
        if (health % 10 == 5) {
            types[bar] = 1;
            bar++;
        }
        while (bar < hearts.size()) {
            types[bar] = 0;
            bar++;
        }
    }

    public void renderHearts() {
        for (bar = 0; bar < hearts.size(); bar++) {
            hearts.get(bar).render(types[bar]);
        }
    }

    @Override
    public void render() {
        super.render();
        renderHearts();
    }
}
