package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import java.util.ArrayList;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.systemd.service.Vibrator;
import ru.warfare.darkannihilation.character.Heart;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.NamesConst.GUN;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

public abstract class BaseCharacter extends Sprite {
    private boolean boom = false;
    public boolean lock;
    public int endX;
    public int endY;
    public long lastShoot = System.currentTimeMillis();
    public long now;
    public boolean dontmove = false;
    public byte gun = GUN;
    public int maxHealth;
    public ArrayList<Heart> hearts = new ArrayList<>(0);
    public int[] types;
    public boolean god;
    private int bar;
    public int heartX = 25;
    public int heartY = 10;

    public BaseCharacter(Game g, Bitmap bitmap, int maxHealth) {
        super(g, bitmap);

        this.maxHealth = maxHealth;
        health = maxHealth;

        int len = maxHealth / 10;
        int lvl = 0;
        for (int i = 0; i < len; i++) {
            hearts.add(new Heart(g, heartX, heartY, false));
            heartX += 90;
            lvl++;

            if (lvl == 5) {
                newLevel();
                lvl = 0;
            }
        }

        newStatus();
        changeHearts();
    }

    public void newStatus() {
        x = HALF_SCREEN_WIDTH - halfWidth;
        y = HALF_SCREEN_HEIGHT - halfHeight;
        endX = x;
        endY = y;
        lock = true;
        god = false;
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

    protected void newLevel() {
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

    protected void addArmorHeart() {
        hearts.add(new Heart(game, heartX, heartY, true));
        heartX += 90;
        if (heartX > 385) {
            newLevel();
        }
    }

    public void heal() {
        baseHeal();
        while (health > 200) {
            health -= 5;
            changeHearts();
        }
        changeHearts();
    }

    protected void baseHeal() {
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
        if (!god && dmg != 0) {
            health -= dmg;
            changeHearts();
            HardThread.doInBackGround(() -> {
                if (health <= 0) {
                    if (!boom) {
                        boom = true;
                        kill();
                    }
                } else {
                    Vibrator.vibrate(60);
                }
            });
        }
    }

    protected void changeHearts() {
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

    private void renderHearts() {
        for (bar = 0; bar < hearts.size(); bar++) {
            hearts.get(bar).render(types[bar]);
        }
    }

    @Override
    public void render() {
        super.render();
        renderHearts();
    }

    @Override
    public void buff() {
    }

    @Override
    public void onStopBuff() {
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
    }

    @Override
    public void start() {
    }

    @Override
    public void kill() {
        game.generateGameOver();
        Vibrator.vibrate(1300);
        createSkullExplosion();
    }
}
