package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.MILLENNIUM_FALCON_HEALTH;

public class BaseCharacter extends Sprite {
    public int endX;
    public int endY;
    public long lastShoot;
    public long now;
    public boolean dontmove = false;
    public String gun = "shotgun";
    public int maxHealth = MILLENNIUM_FALCON_HEALTH;
    public Heart[] hearts = new Heart[5];
    public boolean god = false;

    public BaseCharacter(Game g, int w, int h) {
        super(g, w, h);

        init();

        int c = 385;
        for (int i = 0; i < 5; i++) {
            hearts[i] = new Heart(c);
            c -= 90;
        }
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

    private void init() {
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

    public void heal() {
        if (health < maxHealth - 20) {
            health += 20;
        } else {
            health = maxHealth;
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
        switch (health) {
            case 50:
                hearts[0].render("full");
                hearts[1].render("full");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                break;
            case 45:
                hearts[0].render("half");
                hearts[1].render("full");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                break;
            case 40:
                hearts[0].render("non");
                hearts[1].render("full");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                break;
            case 35:
                hearts[0].render("non");
                hearts[1].render("half");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                break;
            case 30:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                break;
            case 25:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("half");
                hearts[3].render("full");
                hearts[4].render("full");
                break;
            case 20:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("full");
                hearts[4].render("full");
                break;
            case 15:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("half");
                hearts[4].render("full");
                break;
            case 10:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("full");
                break;
            case 5:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("half");
                break;
            default:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("non");
                break;
        }
    }
}
