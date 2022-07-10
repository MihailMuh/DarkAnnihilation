package com.warfare.darkannihilation.scenes.firstlevel;

import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Constants.BOSS_PERIOD_IN_SECS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_EXPLOSION;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Names.ATTENTION;
import static com.warfare.darkannihilation.constants.Names.BOMB;
import static com.warfare.darkannihilation.constants.Names.DEATH_STAR;
import static com.warfare.darkannihilation.constants.Names.DEMOMAN;
import static com.warfare.darkannihilation.constants.Names.FACTORY;
import static com.warfare.darkannihilation.constants.Names.HEALTH_KIT;
import static com.warfare.darkannihilation.constants.Names.MINION;
import static com.warfare.darkannihilation.constants.Names.TRIPLE;
import static com.warfare.darkannihilation.constants.Names.VADER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Processor.postToLooper;
import static com.warfare.darkannihilation.systemd.service.Watch.frameCount;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.enemy.Attention;
import com.warfare.darkannihilation.enemy.Demoman;
import com.warfare.darkannihilation.enemy.Factory;
import com.warfare.darkannihilation.enemy.Rocket;
import com.warfare.darkannihilation.enemy.boss.DeathStar;
import com.warfare.darkannihilation.hub.PoolHub;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.scenes.countdown.Countdown;
import com.warfare.darkannihilation.scenes.gameover.GameOver;
import com.warfare.darkannihilation.scenes.gameover.GameOverScreen;
import com.warfare.darkannihilation.scenes.versus.VersusScene;
import com.warfare.darkannihilation.screens.MovingScreen;
import com.warfare.darkannihilation.support.HealthKit;
import com.warfare.darkannihilation.systemd.DifficultyAnalyzer;
import com.warfare.darkannihilation.systemd.EnemyController;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.GameTask;

import java.util.Iterator;

public class FirstLevel extends Scene {
    private final GameTask gameTask = new GameTask(this::systemTask, 250);
    // BOSS_PERIOD_IN_SECS * 100 == (BOSS_PERIOD_IN_SECS / 10) * 1000
    private final GameTask spawnBossTask = new GameTask(this::spawnBoss, BOSS_PERIOD_IN_SECS * 100);

    private final Array<Explosion> explosions = new Array<>(false, NUMBER_EXPLOSION);
    private final Array<Bullet> bullets = new Array<>(false, NUMBER_MILLENNIUM_FALCON_BULLETS);
    private final Array<BaseBullet> bulletsEnemy = new Array<>(false, 30);
    private final Array<Opponent> empire = new Array<>(false, 40);

    private final EnemyController enemyController = new EnemyController(empire);
    private DifficultyAnalyzer difficultyAnalyzer;
    private final PoolHub poolHub = getPools();

    private Frontend frontend;
    private Player player;
    private Demoman demoman;
    private HealthKit healthKit;
    private Attention attention;
    private Rocket rocket;
    public Factory factory;

    private boolean firstRun = true;
    private boolean startAnalytics = false;
    private float lastBoss;
    private int numBosses;
    int score;

    public FirstLevel(MainGameManager mainGameManager) {
        super(mainGameManager);
        getImages().loadFirstLevelImages();
        getSounds().loadGameSounds();
    }

    @Override
    public void create() {
        getImages().getFirstLevelImages();
        getSounds().getGameSounds();
        poolHub.initPools(explosions, bulletsEnemy, bullets);

        factory = new Factory();
        difficultyAnalyzer = new DifficultyAnalyzer(enemyController, factory);
        player = new Player(difficultyAnalyzer);
        screen = new MovingScreen(getImages().starScreenGIF);
        frontend = new Frontend(this, player, screen, explosions, bullets, empire, bulletsEnemy);

        poolHub.initWithPlayer(empire, player);
        Processor.postTask(this::initEnemies);
    }

    private void initEnemies() {
        enemyController.newVader(NUMBER_VADER);
        enemyController.newTriple();

        demoman = new Demoman();
        healthKit = new HealthKit();
        rocket = new Rocket();
        attention = new Attention(rocket);
        empire.addAll(demoman, healthKit, attention, rocket, factory);

        clickListener = new GameClickListener(player, mainGameManager);
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    @Override
    public void resume() {
        super.resume();
        if (!firstRun) {
            gameTask.start();
            spawnBossTask.start();

            if (!startAnalytics) {
                startAnalytics = true;
                difficultyAnalyzer.startCollecting();
                lastBoss = time;
            }
        } else {
            firstRun = false;
            mainGameManager.startScene(new Countdown(mainGameManager, screen, player), false);

            getSounds().firstLevelMusic.play();
        }
    }

    @Override
    public void pause() {
        gameTask.stop();
        spawnBossTask.stop();
    }

    @Override
    public void update() {
        float moveAll = player.speedX / 2.8f;
        boolean needFindIntersections = frameCount % 2 == 0;

        screen.x -= moveAll;

        player.update();
        player.shoot();

        updateEmpire(moveAll, needFindIntersections);
        updateBulletsEnemy(moveAll, needFindIntersections);

        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
            Bullet bullet = iterator.next();
            if (bullet.visible) {
                bullet.x -= moveAll;
                bullet.update();
            } else {
                iterator.remove();
                postToLooper(() -> poolHub.bulletPool.free(bullet));
            }
        }

        updateExplosions(moveAll);
    }

    private void updateEmpire(float moveAll, boolean needFindIntersections) {
        for (Iterator<Opponent> iterator = empire.iterator(); iterator.hasNext(); ) {
            Opponent opponent = iterator.next();
            if (opponent.visible) {
                opponent.x -= moveAll;
                opponent.update();
                if (needFindIntersections) checkIntersectionsWithAnybody(opponent);
            } else {
                switch (opponent.name) {
                    case VADER:
                        iterator.remove();
                        postToLooper(() -> poolHub.vaderPool.free(opponent));
                        continue;
                    case TRIPLE:
                        iterator.remove();
                        postToLooper(() -> poolHub.triplePool.free(opponent));
                        continue;
                    case MINION:
                        iterator.remove();
                        postToLooper(() -> poolHub.minionPool.free(opponent));
                        continue;
                    case DEATH_STAR:
                        iterator.remove();
                        Processor.postTask(() -> {
                            lastBoss = time;
                            numBosses--;
                            difficultyAnalyzer.isBossTime(numBosses != 0);

                            enemyController.newVader((int) (NUMBER_VADER * 1.5));
                            enemyController.newTriple();
                            enemyController.newTriple();
                        });
                }
            }
        }
    }

    private void updateBulletsEnemy(float moveAll, boolean needFindIntersections) {
        Player player = this.player;

        for (Iterator<BaseBullet> iterator = bulletsEnemy.iterator(); iterator.hasNext(); ) {
            BaseBullet bullet = iterator.next();
            if (bullet.visible) {
                bullet.x -= moveAll;
                bullet.update();
                if (needFindIntersections) {
                    bullet.killedByPlayer(player);
                }
            } else {
                iterator.remove();
                postToLooper(() -> {
                    if (bullet.name == BOMB) {
                        poolHub.bombPool.free(bullet);
                    } else {
                        poolHub.bulletEnemyPool.free(bullet);
                    }
                });
            }
        }
    }

    private void updateExplosions(float moveAll) {
        for (Iterator<Explosion> iterator = explosions.iterator(); iterator.hasNext(); ) {
            Explosion explosion = iterator.next();
            if (explosion.visible) {
                explosion.x -= moveAll;
            } else {
                iterator.remove();
                postToLooper(() -> poolHub.explosionPool.free(explosion));
            }
        }
    }

    private void checkIntersectionsWithAnybody(Opponent opponent) {
        if (opponent.name == ATTENTION) return;
        if (opponent.name == HEALTH_KIT) {
            if (opponent.intersect(player)) {
                Processor.postTask(() -> player.heal(15));
                opponent.kill();
            }
            return;
        }

        if (opponent.killedByPlayer(player) || (rocket.visible && opponent != rocket && opponent.killedBy(rocket))) {
            return;
        }

        for (Bullet bullet : bullets) {
            if (bullet.visible && opponent.killedBy(bullet)) {
                score += opponent.killScore;
                return;
            }
        }
        if (opponent.name != DEMOMAN && opponent.name != FACTORY) {
            for (BaseBullet bullet : bulletsEnemy) {
                if (bullet.visible && bullet.name == BOMB && opponent.killedBy(bullet)) {
                    return;
                }
            }
        }
    }

    private void systemTask() {
        if (!demoman.visible && score > 70 && randomBoolean(0.0315f)) demoman.reset();
        if (!healthKit.visible && randomBoolean(0.02f)) healthKit.reset();
        if (!attention.visible && score > 50 && randomBoolean(0.06f)) attention.reset();
        if (!factory.visible && score > 170 && randomBoolean(0.0135f) && numBosses == 0)
            factory.reset();

        difficultyAnalyzer.checkTime();

        if (player.isDead()) {
            Processor.runForDifferentOS(() -> {
                frontend.setScreen(new GameOverScreen());
                mainGameManager.startScene(new GameOver(mainGameManager, empire, bullets, bulletsEnemy, explosions), false);
            });
        }
    }

    private void spawnBoss() {
        if (time - lastBoss > BOSS_PERIOD_IN_SECS && numBosses == 0) {
            lastBoss = time;

            enemyController.killEmpire();

            DeathStar deathStar = new DeathStar();
            stopBackground();
            mainGameManager.startScene(new VersusScene(mainGameManager, player.name, deathStar.name), false);

            difficultyAnalyzer.isBossTime(true);
            numBosses++;
            empire.add(deathStar);
        }
    }

    @Override
    public void render() {
        frontend.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        getImages().disposeFirstLevelImages();
        getSounds().disposeGameSounds();
        poolHub.disposePools();
    }
}
