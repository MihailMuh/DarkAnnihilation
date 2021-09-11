package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import java.util.ArrayList;

import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.systemd.service.Vibrator;
import ru.warfare.darkannihilation.character.Heart;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.NamesConst.GUN;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static ru.warfare.darkannihilation.systemd.service.Windows.calculate;

public abstract class BaseCharacter extends Sprite {
    private static final short cup = 250;
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
    private static final int _25 = calculate(25);
    private static final int _10 = calculate(10);
    private static final int _90 = calculate(90);
    private static final int _385 = calculate(385);

    public int heartX = _25;
    public int heartY = _10;

    public BaseCharacter(Game g, Bitmap bitmap, int maxHealth) {
        super(g, bitmap);

        this.maxHealth = maxHealth;
        health = maxHealth;

        int len = maxHealth / 10;
        int lvl = 0;
        for (int i = 0; i < len; i++) {
            hearts.add(new Heart(g, heartX, heartY, false));
            heartX += _90;
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
        heartX = _25;
        heartY += _10 + ImageHub.imageHeartHalf.getHeight();
    }

    public void killArmorHeart(Heart heart) {
        if (heartX != _25) {
            heartX -= _90;
        } else {
            heartX = _385;
            heartY -= _10 + ImageHub.imageHeartHalf.getHeight();
        }
        maxHealth = health;
        hearts.remove(heart);
    }

    protected void addArmorHeart() {
        hearts.add(new Heart(game, heartX, heartY, true));
        heartX += _90;
        if (heartX > _385) {
            newLevel();
        }
    }

    public void heal() {
        baseHeal();
        while (health > cup) {
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
        int len = health / 10;
        int countHearts = hearts.size();
        types = new int[countHearts];

        for (bar = 0; bar < len; bar++) {
            types[bar] = 2;
        }
        if (health % 10 == 5) {
            types[bar] = 1;
            bar++;
        }
        while (bar < countHearts) {
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
