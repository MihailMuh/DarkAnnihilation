package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.thread.GameTask;
import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.math.Randomize;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BOSS_HEALTH_BAR_LEN;
import static ru.warfare.darkannihilation.constant.Modes.BOSS_PREVIEW;

public abstract class BaseBoss extends Sprite {
    protected final GameTask gameTask;
    protected boolean startShoot;
    private float hp = BOSS_HEALTH_BAR_LEN;
    private boolean BOOM = false;
    private final float maxHealth;

    public BaseBoss(Game game, Bitmap bitmap, int maxHealth, int speedX, short shootTime) {
        super(game, bitmap);

        speedY = 1;
        this.speedX = speedX;

        calculateBarriers();

        x = Randomize.randInt(0, screenWidthWidth);
        y = -800;

        this.maxHealth = maxHealth;
        health = maxHealth;

        gameTask = new GameTask(this::shoot, shootTime);
    }

    public abstract void shoot();

    private void killAfterFight() {
        if (!BOOM) {
            BOOM = true;

            HardThread.doInPool(() -> {
                AudioHub.pauseBossMusic();

                gameTask.stop();
                game.gameTask.stop();

                createSkullExplosion();
                game.killBoss();

                game.newPortal();

                kill();

                game.startEmpire();

                game.changerGuns.show();
                game.pauseButton.show();
            });
        }
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
        if (intersect(bullet)) {
            health -= bullet.damage;
            bullet.kill();
            if (hp > 4) {
                hp = (health / maxHealth) * BOSS_HEALTH_BAR_LEN;
            } else {
                hp = 4;
            }

            if (health <= 0) {
                killAfterFight();
            }
        }
    }

    @Override
    public void update() {
        if (y == -600) {
            AudioHub.restartBossMusic();
            AudioHub.pauseBackgroundMusic();
            ImageHub.loadPortalImages();
            HardThread.pauseTasks();
            Game.gameStatus = BOSS_PREVIEW;
        }
    }

    @Override
    public void render() {
        Game.canvas.drawRect(centerX() - 70, y - 10, centerX() + 70, y + 5, Game.scorePaint);
        Game.canvas.drawRect(centerX() - 68, y - 8, centerX() - 72 + hp, y + 3, Game.fpsPaint);
    }
}
