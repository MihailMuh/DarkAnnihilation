package ru.warfare.darkannihilation;

public class BaseCharacter extends Sprite {
    public int endX;
    public int endY;
    public int shootTime;
    public int shotgunTime;
    public long lastShoot;
    public long now;
    public boolean dontmove = false;
    public String gun = "gun";
    public int maxHealth = 50;
    public Heart[] hearts = new Heart[5];
    public boolean god = false;

    public BaseCharacter(Game g, int w, int h) {
        super(g, w, h);

        if (Game.level == 1 | !game.shotgunKit.picked) {
            gun = "gun";
        }
        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;
        endX = x;
        endY = y;
        lock = true;
        health = maxHealth;
        int c = 370;
        for (int i = 0; i < 5; i++) {
            hearts[i] = new Heart(c);
            c -= 90;
        }
    }

    public BaseCharacter(Game g, int w, int h, int maxHealth) {
        super(g, w, h);

        if (Game.level == 1 | !game.shotgunKit.picked) {
            gun = "gun";
        }
        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;
        endX = x;
        endY = y;
        lock = true;
        this.maxHealth = maxHealth;
        health = maxHealth;
    }

    public BaseCharacter(int w, int h) {
        super(w, h);

        x = Game.halfScreenWidth;
        y = Game.halfScreenHeight;
    }

    public void shoot() {}
    public void checkIntersections(Sprite sprite) {}

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

    public void damage(int dmg) {
        if (dmg != 0 & !god) {
            health -= dmg;
            if (health <= 0) {
                game.generateGameover();
                createSkullExplosion();
                AudioHub.playMegaBoom();
                Service.vibrate(1450);
            } else {
                Service.vibrate(70);
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
