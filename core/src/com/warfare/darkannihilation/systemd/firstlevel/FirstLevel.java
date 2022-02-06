package com.warfare.darkannihilation.systemd.firstlevel;

import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Constants.MIN_NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_EXPLOSION;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Names.BOMB;
import static com.warfare.darkannihilation.constants.Names.TRIPLE;
import static com.warfare.darkannihilation.constants.Names.VADER;

import com.badlogic.gdx.Gdx;
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
import com.warfare.darkannihilation.systemd.DifficultyAnalyzer;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.screens.DynamicScreen;
import com.warfare.darkannihilation.support.HealthKit;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.gameover.GameOver;
import com.warfare.darkannihilation.systemd.gameover.GameOverScreen;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.GameTask;
import com.warfare.darkannihilation.utils.PoolWrap;

import java.util.Iterator;

public class FirstLevel extends Scene {
    private final GameTask gameTask = new GameTask(this::spawn, 250, false);
    private final DifficultyAnalyzer difficultyAnalyzer = new DifficultyAnalyzer(this);
    private Frontend frontend;

    private Player player;
    private Demoman demoman;
    private HealthKit healthKit;
    private Attention attention;

    private final Array<Explosion> explosions = new Array<>(NUMBER_EXPLOSION);
    private final Array<Bullet> bullets = new Array<>(NUMBER_MILLENNIUM_FALCON_BULLETS);
    private final Array<BaseBullet> bulletsEnemy = new Array<>(30);
    private final Array<Opponent> empire = new Array<>(30);

    private PoolWrap<Explosion> explosionPool;
    private PoolWrap<Bullet> bulletPool;
    private PoolWrap<BaseBullet> bombPool;
    private PoolWrap<BaseBullet> bulletEnemyPool;
    private PoolWrap<Opponent> vaderPool;
    private PoolWrap<Opponent> triplePool;

    private boolean firstRun = true;
    private boolean single = false;
    private float moveAll;
    int score;

    public FirstLevel(MainGameManager mainGameManager) {
        super(mainGameManager);
        imageHub.loadFirstLevelImages();
        soundHub.loadGameSounds();
    }

    @Override
    public void create() {
        imageHub.getFirstLevelImages();
        soundHub.getGameSounds();

        explosionPool = new PoolWrap<Explosion>(explosions) {
            @Override
            protected Explosion newObject() {
                return new Explosion(imageHub);
            }
        };
        bulletPool = new PoolWrap<Bullet>(bullets) {
            @Override
            protected Bullet newObject() {
                return new Bullet(explosionPool, imageHub.bulletImg);
            }
        };
        bombPool = new PoolWrap<BaseBullet>(bulletsEnemy) {
            @Override
            protected Bomb newObject() {
                return new Bomb(explosionPool, imageHub.bombImg);
            }
        };
        bulletEnemyPool = new PoolWrap<BaseBullet>(bulletsEnemy) {
            @Override
            protected BulletEnemy newObject() {
                return new BulletEnemy(explosionPool, imageHub.bulletEnemyImg);
            }
        };
        vaderPool = new PoolWrap<Opponent>(empire) {
            @Override
            protected Opponent newObject() {
                return new Vader(explosionPool, imageHub.vadersImages);
            }
        };
        triplePool = new PoolWrap<Opponent>(empire) {
            @Override
            protected Opponent newObject() {
                return new TripleFighter(explosionPool, bulletEnemyPool, imageHub.tripleFighterImg, soundHub.shotgunSound, player);
            }
        };

        demoman = new Demoman(explosionPool, bombPool, imageHub.demomanImg);
        healthKit = new HealthKit(imageHub.healthKitImg);
        Rocket rocket = new Rocket(explosionPool, imageHub.rocketImg);
        attention = new Attention(imageHub.attentionImg, soundHub.attentionSound, rocket);
        player = new Player(difficultyAnalyzer, imageHub, soundHub, bulletPool, explosionPool);
        screen = new DynamicScreen(imageHub.starScreenGIF);

        for (int i = 0; i < NUMBER_VADER; i++) {
            vaderPool.obtain().visible = true;
        }
        triplePool.obtain().visible = true;

        empire.add(demoman, healthKit, attention, rocket);

        frontend = new Frontend(this, fontHub.canisMinor, player, screen, explosions, bullets, empire, bulletsEnemy);

        clickListener = new GameClickListener(player, mainGameManager);
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    @Override
    public void resume() {
        if (!firstRun) {
            gameTask.start();

            if (!single) {
                single = true;
                difficultyAnalyzer.startCollecting();
            }
        } else {
            firstRun = false;
            mainGameManager.startScene(new Countdown(mainGameManager, screen, player), false);

            soundHub.firstLevelMusic.play();
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

        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
            Bullet bullet = iterator.next();
            if (bullet.visible) {
                bullet.x -= moveAll;
                bullet.update();
            } else {
                iterator.remove();
                Processor.post(() -> bulletPool.free(bullet));
            }
        }

        updateExplosions();
    }

    private void updateEmpire() {
        for (Iterator<Opponent> iterator = empire.iterator(); iterator.hasNext(); ) {
            Opponent opponent = iterator.next();
            if (opponent.visible) {
                opponent.x -= moveAll;
                opponent.update();
                if (opponent.killedBy(player)) continue;

                for (Bullet bullet : bullets) {
                    if (bullet.visible && opponent.killedBy(bullet)) {
                        score += opponent.killScore;
                        break;
                    }
                }
            } else {
                if (opponent.name == VADER) {
                    iterator.remove();
                    vaderPool.free(opponent);
                } else {
                    if (opponent.name == TRIPLE) {
                        iterator.remove();
                        triplePool.free(opponent);
                    }
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
                bullet.killedBy(player);
            } else {
                iterator.remove();
                Processor.post(() -> {
                    if (bullet.name == BOMB) {
                        bombPool.free(bullet);
                    } else {
                        bulletEnemyPool.free(bullet);
                    }
                });
            }
        }
    }

    private void updateExplosions() {
        for (Iterator<Explosion> iterator = explosions.iterator(); iterator.hasNext(); ) {
            Explosion explosion = iterator.next();
            if (explosion.visible) {
                explosion.x -= moveAll;
            } else {
                iterator.remove();
                explosionPool.free(explosion);
            }
        }
    }

    private void spawn() {
        if (!demoman.visible && score > 70 && randomBoolean(0.0315f)) demoman.reset();

        if (!healthKit.visible && randomBoolean(0.015f)) healthKit.reset();

        if (!attention.visible && score > 50 && randomBoolean(0.06f)) attention.reset();

        if (player.isDead()) {
            frontend.setScreen(new GameOverScreen(imageHub.gameOverScreen, fontHub));
            mainGameManager.startScene(new GameOver(mainGameManager, empire, bullets, bulletsEnemy, explosions, explosionPool), false);
        }

        difficultyAnalyzer.checkTime();

        if (empire.size - 4 < MIN_NUMBER_VADER) {
            newVader(1);
        }
    }

    public void newTriple() {
        Gdx.app.postRunnable(() -> triplePool.obtain().start());
    }

    public void newVader(int count) {
        for (int i = 0; i < count; i++) {
            Gdx.app.postRunnable(() -> vaderPool.obtain().start());
        }
    }

    public void killTriple() {
        if (NUMBER_VADER - triplePool.getFree() > 0) {
            for (int i = empire.size - 1; i >= 0; i--) {
                Opponent opponent = empire.get(i);

                if (opponent.name == TRIPLE && !opponent.shouldKill) {
                    opponent.shouldKill = true;
                    break;
                }
            }
        }
    }

    public void killVader() {
        for (int i = 0; i < empire.size; i++) {
            Opponent opponent = empire.get(i);

            if (!opponent.shouldKill && opponent.name == VADER) {
                opponent.shouldKill = true;
                break;
            }
        }
    }

    @Override
    public void render() {
        frontend.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        imageHub.disposeFirstLevelImages();
        soundHub.disposeGameSounds();
    }
}
