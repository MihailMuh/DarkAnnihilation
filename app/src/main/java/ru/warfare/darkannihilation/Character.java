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

    public Character(Game g, int w, int h) {
        super(g, w, h);
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
                game.vibrator.vibrate(70);
            }
        }
    }

    public void PLAYER() {}
    public void shoot() {}
    public void check_intersectionVader(Vader vader) {}
    public void check_intersectionTripleFighter(TripleFighter tripleFighter) {}
    public void check_intersectionPortal(Portal portal) {}
    public void check_intersectionMinion(Minion minion) {}
    public void check_intersectionDemoman(Demoman demoman) {}
    public void check_intersectionShotgunKit(ShotgunKit shotgunKit) {}
    public void check_intersectionHealthKit(HealthKit healthKit) {}
    public void check_intersectionRocket(Rocket rocket) {}
    public void checkIntersections(Sprite sprite) {}
}
