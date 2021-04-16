package ru.warfare.darkannihilation;

public class Character extends Sprite {
    public int endX;
    public int endY;
    public int shootTime;
    public int shotgunTime;
    public long lastShoot;
    public long now;
    public boolean dontmove = false;
    public String gun = "gun";
    public int ai = 1;
    public int maxHealth = 50;

    public Character(Game g, int w, int h) {
        super(g, w, h);
    }

    public void heal() {
        if (health < maxHealth - 20) {
            health += 20;
        } else {
            health = maxHealth;
        }
    }

    public void damage(int dmg) {
        if (ai == 0) {
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
                game.vibrator.vibrate(1550);
            } else {
                if (dmg != 0) {
                    game.vibrator.vibrate(70);
                }
            }
        }
    }

    public void PLAYER() {}
    public void shoot() {}
    public void checkIntersections(Sprite sprite) {}
}
