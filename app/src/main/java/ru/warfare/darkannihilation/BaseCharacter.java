package ru.warfare.darkannihilation;

import java.util.ArrayList;

import static ru.warfare.darkannihilation.Constants.MILLENNIUM_FALCON_HEALTH;

public class BaseCharacter extends Sprite {
    public int endX;
    public int endY;
    public long lastShoot;
    public long now;
    public boolean dontmove = false;
    public String gun = "shotgun";
    public int maxHealth = MILLENNIUM_FALCON_HEALTH;
    public ArrayList<Heart> hearts = new ArrayList<>(0);
    public boolean god = false;
    private int len;
    private int bar;
    public int heartX;
    public int heartY;

    public BaseCharacter(Game g, int w, int h) {
        super(g, w, h);

        init();

        heartX = 25;
        heartY = 10;
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
        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;
        endX = x;
        endY = y;
        lock = true;
    }

    private void init() {
        gun = "shotgun";
        if (Game.level == 1 | !game.shotgunKit.picked) {
            gun = "gun";
        }
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
    }

    public void heal() {
        health += 20;
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

//        switch (health) {
//            case 50:
//                hearts[0].render("full");
//                hearts[1].render("full");
//                hearts[2].render("full");
//                hearts[3].render("full");
//                hearts[4].render("full");
//                break;
//            case 45:
//                hearts[0].render("half");
//                hearts[1].render("full");
//                hearts[2].render("full");
//                hearts[3].render("full");
//                hearts[4].render("full");
//                break;
//            case 40:
//                hearts[0].render("non");
//                hearts[1].render("full");
//                hearts[2].render("full");
//                hearts[3].render("full");
//                hearts[4].render("full");
//                break;
//            case 35:
//                hearts[0].render("non");
//                hearts[1].render("half");
//                hearts[2].render("full");
//                hearts[3].render("full");
//                hearts[4].render("full");
//                break;
//            case 30:
//                hearts[0].render("non");
//                hearts[1].render("non");
//                hearts[2].render("full");
//                hearts[3].render("full");
//                hearts[4].render("full");
//                break;
//            case 25:
//                hearts[0].render("non");
//                hearts[1].render("non");
//                hearts[2].render("half");
//                hearts[3].render("full");
//                hearts[4].render("full");
//                break;
//            case 20:
//                hearts[0].render("non");
//                hearts[1].render("non");
//                hearts[2].render("non");
//                hearts[3].render("full");
//                hearts[4].render("full");
//                break;
//            case 15:
//                hearts[0].render("non");
//                hearts[1].render("non");
//                hearts[2].render("non");
//                hearts[3].render("half");
//                hearts[4].render("full");
//                break;
//            case 10:
//                hearts[0].render("non");
//                hearts[1].render("non");
//                hearts[2].render("non");
//                hearts[3].render("non");
//                hearts[4].render("full");
//                break;
//            case 5:
//                hearts[0].render("non");
//                hearts[1].render("non");
//                hearts[2].render("non");
//                hearts[3].render("non");
//                hearts[4].render("half");
//                break;
//            default:
//                hearts[0].render("non");
//                hearts[1].render("non");
//                hearts[2].render("non");
//                hearts[3].render("non");
//                hearts[4].render("non");
//                break;
//        }
    }
}
