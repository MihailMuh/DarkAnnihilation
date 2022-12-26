package ru.warfare.darkannihilation.enemy;

import static ru.warfare.darkannihilation.constant.Constants.FACTORY_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.FACTORY_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.constant.Constants.FACTORY_SPAWN_TIME;
import static ru.warfare.darkannihilation.constant.Constants.FACTORY_SPEED;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Randomize;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.thread.Process;
import ru.warfare.darkannihilation.thread.SickGameTask;

public class Factory extends Sprite {
    private final SickGameTask gameTask = new SickGameTask(this::spawn, FACTORY_SPAWN_TIME);
    private final Process process = new Process();
    private float hp;
    private boolean startSpawn;
    private boolean BOOM;

    private static final int minionY = ImageHub.factoryImg.getHeight() - 100;

    public Factory(Game game) {
        super(game, ImageHub.factoryImg);

        hide();

        recreateRect(x + 20, y + 80, right() - 20, bottom() - 20);
    }

    private void spawn() {
        int count = 0;
        for (int i = game.NUMBER_VADERS; i < game.NUMBER_MINIONS; i++) {
            if (game.enemy[i].lock && Randomize.randBoolean()) {
                ((Minion) game.enemy[i]).start(randInt(x, right), minionY, process);
                count++;
            }
            if (count == 3) {
                break;
            }
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
        if (!BOOM) {
            BOOM = true;
            HardThread.doInBackGround(() -> {
                Game.score += 75;
                createSkullExplosion();
                hide();
                process.terminate();
                gameTask.stop();
            });
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 20, y + 80);
    }

    @Override
    public void hide() {
        HardThread.closeBlackHole();
        lock = true;
        hp = FACTORY_HEALTH_BAR_LEN;
        y = -height;
        x = HALF_SCREEN_WIDTH - halfWidth;
        health = FACTORY_HEALTH;
        startSpawn = false;
        BOOM = false;
    }

    @Override
    public void update() {
        if (y < 0) {
            y += FACTORY_SPEED;
        } else {
            if (!startSpawn) {
                startSpawn = true;
                gameTask.start();
                process.start();
            }
        }
    }

    @Override
    public void render() {
        super.render();

        Game.canvas.drawRect(centerX() - 250, y + 60, centerX() + 250, y + 75, Game.scorePaint);
        Game.canvas.drawRect(centerX() - 247, y + 62, centerX() - 253 + hp, y + 73, Game.fpsPaint);
    }
}
