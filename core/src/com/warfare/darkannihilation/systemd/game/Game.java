package com.warfare.darkannihilation.systemd.game;

import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Assets.FALCON_ATLAS;
import static com.warfare.darkannihilation.constants.Assets.FIRST_LEVEL_ATLAS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_EXPLOSION;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Names.BOMB;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.abstraction.sprite.movement.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bomb;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.bullet.BulletEnemy;
import com.warfare.darkannihilation.enemy.Attention;
import com.warfare.darkannihilation.enemy.Demoman;
import com.warfare.darkannihilation.enemy.Rocket;
import com.warfare.darkannihilation.enemy.TripleFighter;
import com.warfare.darkannihilation.enemy.Vader;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.screens.DynamicScreen;
import com.warfare.darkannihilation.systemd.gameover.GameOverScreen;
import com.warfare.darkannihilation.support.HealthKit;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.gameover.GameOver;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.GameTask;
import com.warfare.darkannihilation.utils.PoolWrap;

import java.util.Iterator;

public class Game extends Scene {
    private final GameTask gameTask = new GameTask(this::spawn, 250);
    private Frontend frontend;

    private Player player;
    private Demoman demoman;
    private HealthKit healthKit;
    private Attention attention;

    private final Array<Explosion> explosions = new Array<>(NUMBER_EXPLOSION);
    private final Array<Bullet> bullets = new Array<>(NUMBER_MILLENNIUM_FALCON_BULLETS);
    private final Array<BaseBullet> bulletsEnemy = new Array<>(15);
    private final Array<Opponent> empire = new Array<>(NUMBER_VADER);

    private PoolWrap<Explosion> explosionPool;
    private PoolWrap<Bullet> bulletPool;
    private PoolWrap<BaseBullet> bombPool;
    private PoolWrap<BaseBullet> bulletEnemyPool;

    private boolean firstRun = true;
    private float moveAll;
    int score;

    public Game(MainGameManager mainGameManager) {
        super(mainGameManager);
        mainGameManager.assetManager.loadAtlas(FIRST_LEVEL_ATLAS);
        mainGameManager.assetManager.loadAtlas(FALCON_ATLAS);
        mainGameManager.soundHub.loadGameSounds();
    }

    @Override
    public void create() {
        mainGameManager.imageHub.getGameImages();
        mainGameManager.soundHub.getGameSounds();

        explosionPool = new PoolWrap<Explosion>(explosions) {
            @Override
            protected Explosion newObject() {
                return new Explosion(mainGameManager);
            }
        };
        bulletPool = new PoolWrap<Bullet>(bullets) {
            @Override
            protected Bullet newObject() {
                return new Bullet(explosionPool, mainGameManager.imageHub.bulletImg);
            }
        };
        bombPool = new PoolWrap<BaseBullet>(bulletsEnemy) {
            @Override
            protected Bomb newObject() {
                return new Bomb(explosionPool, mainGameManager.imageHub.bombImg);
            }
        };
        bulletEnemyPool = new PoolWrap<BaseBullet>(bulletsEnemy) {
            @Override
            protected BulletEnemy newObject() {
                return new BulletEnemy(explosionPool, mainGameManager.imageHub.bulletEnemyImg);
            }
        };

        demoman = new Demoman(explosionPool, bombPool, mainGameManager.imageHub.demomanImg);
        healthKit = new HealthKit(mainGameManager.imageHub.healthKitImg);
        Rocket rocket = new Rocket(explosionPool, mainGameManager.imageHub.rocketImg);
        attention = new Attention(mainGameManager.imageHub.attentionImg, mainGameManager.soundHub.attentionSound, rocket);
        player = new Player(mainGameManager.imageHub, mainGameManager.soundHub, bulletPool, explosionPool);
        screen = new DynamicScreen(mainGameManager.imageHub.starScreenGIF);

        for (int i = 0; i < NUMBER_VADER; i++) {
            if (MathUtils.random() <= 0.12)
                newTriple();
            else empire.add(new Vader(explosionPool, mainGameManager.imageHub.vadersImages));
        }
        newTriple();
        empire.add(demoman, healthKit, attention, rocket);

        frontend = new Frontend(this, mainGameManager.fontHub.canisMinor, player, screen, explosions, bullets, empire, bulletsEnemy);

        clickListener = new GameClickListener(player, mainGameManager);
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    @Override
    public void resume() {
        if (!firstRun) {
            gameTask.start();
        } else {
            firstRun = false;
            mainGameManager.startScene(new Countdown(mainGameManager, screen, player), false);

            mainGameManager.soundHub.firstLevelMusic.play();
        }
    }

    @Override
    public void pause() {
        gameTask.stop();
    }

    @Override
    public void update() {
        moveAll = player.speedX / 2.8f;
        screen.x -= moveAll;

        player.update();
        player.shoot();

        updateEmpire();
        updateBulletsEnemy();

        for (Bullet bullet : bullets) {
            bullet.x -= moveAll;
            bullet.update();
        }

        updateExplosions();
    }

    private void updateEmpire() {
        for (Opponent opponent : empire) {
            if (opponent.visible) {
                opponent.x -= moveAll;
                opponent.update();
                if (opponent.killedByPlayer(player)) continue;

                for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
                    Bullet bullet = iterator.next();
                    if (bullet.visible) {
                        if (opponent.killedByBullet(bullet)) {
                            score += opponent.killScore;

                            iterator.remove();
                            bulletPool.free(bullet);
                            break;
                        }
                        continue;
                    }
                    iterator.remove();
                    bulletPool.free(bullet);
                }
            }
        }
    }

    private void updateBulletsEnemy() {
        for (Iterator<BaseBullet> iterator = bulletsEnemy.iterator(); iterator.hasNext(); ) {
            BaseBullet bullet = iterator.next();
            if (bullet.visible) {
                bullet.x -= moveAll;
                bullet.update();
                player.killedByBullet(bullet);
                continue;
            }
            iterator.remove();
            if (bullet.name == BOMB) {
                bombPool.free(bullet);
            } else {
                bulletEnemyPool.free(bullet);
            }
        }
    }

    private void updateExplosions() {
        for (Iterator<Explosion> iterator = explosions.iterator(); iterator.hasNext(); ) {
            Explosion explosion = iterator.next();
            if (explosion.visible) {
                explosion.x -= moveAll;
                continue;
            }
            iterator.remove();
            explosionPool.free(explosion);
        }
    }

    private void spawn() {
        if (!demoman.visible && score > 70 && randomBoolean(0.0315f)) demoman.reset();

        if (!healthKit.visible && randomBoolean(0.015f)) healthKit.reset();

        if (!attention.visible && score > 50 && randomBoolean(0.06f)) attention.reset();

        if (player.isDead()) {
            frontend.setScreen(new GameOverScreen(mainGameManager.imageHub.gameOverScreen, mainGameManager.fontHub));
            mainGameManager.startScene(new GameOver(mainGameManager, empire, bullets, bulletsEnemy, explosions, explosionPool), false);
        }
    }

    private void newTriple() {
        empire.add(new TripleFighter(explosionPool, bulletEnemyPool, mainGameManager.imageHub.tripleFighterImg,
                mainGameManager.soundHub.shotgunSound, player));
    }

    @Override
    public void render() {
        frontend.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        mainGameManager.imageHub.disposeGameImages();
        mainGameManager.soundHub.disposeGameSounds();
    }
}
