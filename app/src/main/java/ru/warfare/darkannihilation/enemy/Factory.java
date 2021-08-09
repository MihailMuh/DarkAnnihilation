package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.FACTORY_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.FACTORY_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.constant.Constants.FACTORY_SPAWN_TIME;
import static ru.warfare.darkannihilation.constant.Constants.FACTORY_SPEED;
import static ru.warfare.darkannihilation.math.Randomize.randInt;

public class Factory extends Sprite {
    private long lastSpawn = System.currentTimeMillis();
    private boolean isSpawn;
    private float hp;

    private static final int minionY = ImageHub.factoryImg.getHeight() - 100;

    public Factory(Game game) {
        super(game, ImageHub.factoryImg);

        hide();

        recreateRect(x + 20, y + 80, right() - 20, bottom() - 20);
    }

    public void spawn() {
        if (System.currentTimeMillis() - lastSpawn > FACTORY_SPAWN_TIME) {
            lastSpawn = System.currentTimeMillis();
            game.enemies.add(new Minion(game, randInt(x, right), minionY));
            game.enemies.add(new Minion(game, randInt(x, right), minionY));
        }
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
        if (intersect(bullet)) {
            health -= bullet.damage;
            bullet.kill();
            if (hp > 6) {
                hp = ((health / (float) FACTORY_HEALTH) * FACTORY_HEALTH_BAR_LEN);
            } else {
                hp = 6;
            }
            if (health <= 0) {
                kill();
            }
        }
    }

    @Override
    public void kill() {
        Game.score += 75;
        createSkullExplosion();
        hide();
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 20, y + 80);
    }

    @Override
    public void hide() {
        lock = true;
        hp = FACTORY_HEALTH_BAR_LEN;
        isSpawn = false;
        y = -height;
        x = Game.halfScreenWidth - halfWidth;
        health = FACTORY_HEALTH;
    }

    @Override
    public void update() {
        if (y < 0) {
            y += FACTORY_SPEED;
        } else {
            if (!isSpawn) {
                isSpawn = true;
            }
            spawn();
        }
    }

    @Override
    public void render() {
        super.render();

        Game.canvas.drawRect(centerX() - 250, y + 60, centerX() + 250, y + 75, Game.scorePaint);
        Game.canvas.drawRect(centerX() - 247, y + 62, centerX() - 253 + hp, y + 73, Game.fpsPaint);
    }
}
