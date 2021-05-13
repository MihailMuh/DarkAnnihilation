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
    public boolean ai = true;
    public final int maxHealth = 50;
    public final Heart[] hearts = new Heart[5];
    public static boolean god = false;

    public BaseCharacter(Game g, int w, int h) {
        super(g, w, h);
    }

    public void shoot() {}
    public void checkIntersections(Sprite sprite) {}

    public void baseSetting() {
        god = false;
        ai = false;
        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
        lock = true;
        health = maxHealth;
        int c = 370;
        for (int i = 0; i < 5; i++) {
            hearts[i] = new Heart(game, c, 10);
            c -= 90;
        }
    }

    public void PLAYER() {
        baseSetting();
    }

    public void heal() {
        if (health < maxHealth - 20) {
            health += 20;
        } else {
            health = maxHealth;
        }
    }

    public void damage(int dmg) {
        if (!ai & dmg != 0 & !god) {
            health -= dmg;
            if (health <= 0) {
                game.generateGameover();
                for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
                    if (game.allExplosions[i].lock) {
                        game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                        break;
                    }
                }
                AudioPlayer.playMegaBoom();
                Service.vibrator.vibrate(1550);
            } else {
                Service.vibrator.vibrate(70);
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
