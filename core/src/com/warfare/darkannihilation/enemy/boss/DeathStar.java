package com.warfare.darkannihilation.enemy.boss;

import static com.warfare.darkannihilation.constants.Constants.BOSS_HEALTH;
import static com.warfare.darkannihilation.constants.Constants.BOSS_SHOOT_TIME;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.sprite.Shooter;
import com.warfare.darkannihilation.systemd.firstlevel.FirstLevel;
import com.warfare.darkannihilation.systemd.service.Processor;

public class DeathStar extends Shooter {
    private final FirstLevel firstLevel;
    private final int vaders, triples;

    public DeathStar(FirstLevel firstLevel, int vaders, int triples) {
        super(getImages().deathStarImg, BOSS_HEALTH, 0, 325, BOSS_SHOOT_TIME);
        this.firstLevel = firstLevel;
        this.vaders = vaders;
        this.triples = triples;

        speedY = 1;
        speedX = 10;
        x = MathUtils.random(SCREEN_WIDTH);
        y = topY + 800;

        shrinkBorders(20, 20, 20, 20);
    }

    @Override
    public void update() {
//        if (y >= 35) {
//            x += speedX;
//            if (x < -width) {
//                if (Randomize.randBoolean()) {
//                    speedX = -speedX;
//                } else {
//                    x = SCREEN_WIDTH;
//                }
//            }
//            if (x > SCREEN_WIDTH) {
//                if (Randomize.randBoolean()) {
//                    speedX = -speedX;
//                } else {
//                    x = -width;
//                }
//            }
//            if (!startShoot) {
//                startShoot = true;
//                gameTask.start();
//            }
//
//        } else {
//            super.update();
//
//            y += speedY;
//        }
    }

    @Override
    protected void shot() {
        Processor.postToLooper(() -> {
            float X = x + width - 115;
            float Y = y + 40;

            getPools().bulletEnemyPool.obtain(X, Y, 0.7071f, 0.7071f, 45);
            getPools().bulletEnemyPool.obtain(X, Y, 0, 1, 0);
            getPools().bulletEnemyPool.obtain(X, Y, -0.7071f, 0.7071f, 135);
            getSounds().bigLaserSound.play();
        });
    }

    @Override
    public void killFromPlayer() {
    }

    @Override
    public void kill() {
        explodeHuge();
        firstLevel.newVader(vaders);
        for (int i = 0; i < triples; i++) {
            firstLevel.newTriple();
        }

    }
}
