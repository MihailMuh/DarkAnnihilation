package ru.warfare.darkannihilation;

import java.util.ArrayList;

import static ru.warfare.darkannihilation.Constants.MILLENNIUM_FALCON_HEALTH;

public class BaseCharacter extends Sprite {
    public int endX;
    public int endY;
    public long lastShoot = System.currentTimeMillis();
    public long now;
    public boolean dontmove = false;
    public String gun = "gun";
    public int maxHealth = MILLENNIUM_FALCON_HEALTH;
    public ArrayList<Heart> hearts = new ArrayList<>(0);
    public boolean god = false;
    private int len;
    private int bar;
    public int heartX = 25;
    public int heartY = 10;

    public BaseCharacter(Game g, int w, int h) {
        super(g, w, h);

        init();

        for (int i = 0; i < 5; i++) {
            hearts.add(new Heart(g, heartX, heartY, false));
            heartX += 90;
        }
        newLevel();
    }

    public BaseCharacter(Game g, int w, int h, int maxHealth) {
        super(g, w, h);

        this.maxHealth = maxHealth;

        init();
    }

    public BaseCharacter(int w, int h) {
        super(w, h);

        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;
    }

    public void newStatus() {
        god = false;
        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;
        endX = x;
        endY = y;
        lock = true;
    }

    private void init() {
        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;
        endX = x;
        endY = y;
        lock = true;
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
        hearts.remove(heart);
        if (heartX != 25) {
            heartX -= 90;
        } else {
            heartX = 385;
            heartY -= 10 + ImageHub.imageHeartHalf.getHeight();
        }
        maxHealth = health;
    }

    public void addArmorHeart() {
        hearts.add(new Heart(game, heartX, heartY, true));
        heartX += 90;
        if (heartX > 385) {
            newLevel();
        }
        maxHealth = health;
    }

    public void heal() {
        health += 10;
        if (health - maxHealth > 0) {
            addArmorHeart();
        }
    }

    private void damage(int dmg) {
        if (dmg != 0 & !god) {
            health -= dmg;
            if (health <= 0) {
                game.generateGameover();
                Service.vibrate(1300);
                createSkullExplosion();
            } else {
                Service.vibrate(60);
            }
        }
    }

    public void renderHearts() {
        len = health / 10;
        for (bar = 0; bar < len; bar++) {
            hearts.get(bar).render("full");
        }
        if (health % 10 == 5) {
            hearts.get(bar).render("half");
            bar++;
        }
        while (bar < hearts.size()) {
            hearts.get(bar).render("non");
            bar++;
        }
    }
}
