package com.warfare.darkannihilation.scenes.firstlevel;

import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.Settings.DEATH_STAR_PERIOD_IN_SECS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_EXPLOSION;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Names.ATTENTION;
import static com.warfare.darkannihilation.constants.Names.BOMB;
import static com.warfare.darkannihilation.constants.Names.BULLET_ENEMY;
import static com.warfare.darkannihilation.constants.Names.DEATH_STAR;
import static com.warfare.darkannihilation.constants.Names.DEMOMAN;
import static com.warfare.darkannihilation.constants.Names.FACTORY;
import static com.warfare.darkannihilation.constants.Names.HEALTH_KIT;
import static com.warfare.darkannihilation.constants.Names.MINION;
import static com.warfare.darkannihilation.constants.Names.STAR_LASER;
import static com.warfare.darkannihilation.constants.Names.SUNRISE_BOMB;
import static com.warfare.darkannihilation.constants.Names.TRIPLE;
import static com.warfare.darkannihilation.constants.Names.VADER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Processor.postToLooper;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.enemy.Attention;
import com.warfare.darkannihilation.enemy.Demoman;
import com.warfare.darkannihilation.enemy.Factory;
import com.warfare.darkannihilation.enemy.Rocket;
import com.warfare.darkannihilation.enemy.deathstar.DeathStar;
import com.warfare.darkannihilation.hub.PoolHub;
import com.warfare.darkannihilation.hub.Resources;
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
    private final GameTask spawnBossTask = new GameTask(this::spawnBoss, DEATH_STAR_PERIOD_IN_SECS * 100);

    private final Array<Explosion> explosions = new Array<>(false, NUMBER_EXPLOSION);
    private final Array<Bullet> bullets = new Array<>(false, NUMBER_MILLENNIUM_FALCON_BULLETS);
    private final Array<BaseBullet> bulletsEnemy = new Array<>(false, 30);
    private final Array<Opponent> empire = new Array<>(false, 40);

    private final EnemyController enemyController = new EnemyController(empire);
    private final PoolHub poolHub = getPools();

    private DifficultyAnalyzer difficultyAnalyzer;
    private Frontend frontend;
    private Player player;
    private Demoman demoman;
    private HealthKit healthKit;
    private Attention attention;
    private Rocket rocket;
    private Factory factory;

    private boolean startAnalytics = false, countdownTime = true;
    private float lastBoss;
    private int numBosses;
    private int score;

    private final StringBuilder stringBuilder = new StringBuilder(30);
    String scoreForFrontend = "";

    public FirstLevel(MainGameManager mainGameManager) {
        super(mainGameManager);
        getImages().loadFirstLevelImages();
        getSounds().loadGameSounds();
    }

    @Override
    public void create() {
        getImages().getFirstLevelImages();
        getSounds().getGameSounds();
        poolHub.initPools(explosions, bulletsEnemy, bullets, empire);

        factory = new Factory();
        difficultyAnalyzer = new DifficultyAnalyzer(enemyController, factory);
        player = new Player(difficultyAnalyzer);
        screen = new MovingScreen(getImages().starScreenGIF);
        frontend = new Frontend(this, screen, explosions, bullets, empire, bulletsEnemy);

        Resources.setPlayer(player);
        initEnemies();

        mainGameManager.startSceneOver(this, new Countdown(mainGameManager, () -> {
            countdownTime = false;
            updateOnPause = false;
        }));
        getSounds().firstLevelMusic.play();
    }

    private void initEnemies() {
        enemyController.newVader(NUMBER_VADER);
        enemyController.newTriple(1);

        demoman = new Demoman();
        healthKit = new HealthKit();
        rocket = new Rocket();
        attention = new Attention(rocket);

        empire.addAll(demoman, healthKit, attention, rocket, factory);
        enemyController.setHealthKit(healthKit);

        clickListener = new GameClickListener(mainGameManager);
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    @Override
    public void resume() {
        super.resume();
        gameTask.start();
        spawnBossTask.start();

        if (!startAnalytics) {
            startAnalytics = true;
            difficultyAnalyzer.startCollecting();
            lastBoss = time;
        }
    }

    @Override
    public void pause() {
        super.pause();
        gameTask.stop();
        spawnBossTask.stop();
    }

    @Override
    public void updateInThread() {
        for (int i = 0; i < empire.size; i++) {
            Opponent opponent = empire.get(i);

            if (opponent != null && opponent.visible) {
                opponent.updateInThread();
                checkIntersectionsWithAnybody(opponent);
            }
        }
        for (int i = 0; i < bulletsEnemy.size; i++) {
            BaseBullet bullet = bulletsEnemy.get(i);

            if (bullet != null && bullet.visible) {
                bullet.updateInThread();
                bullet.killedByPlayer(player);
            }
        }
        for (int i = 0; i < bullets.size; i++) {
            Bullet bullet = bullets.get(i);

            if (bullet != null && bullet.visible) {
                bullet.updateInThread();
            }
        }
        for (int i = 0; i < explosions.size; i++) {
            Explosion explosion = explosions.get(i);

            if (explosion != null && explosion.visible) {
                explosion.updateInThread();
            }
        }

        screen.update();
        if (!countdownTime) player.shoot();

        scoreForFrontend = stringBuilder.append(getLocales().currentScore).append(score).toString();
        stringBuilder.setLength(0);
    }

    @Override
    public void update() {
        float moveAll = player.speedX / -2.8f;

        screen.translateX(moveAll);
        player.update();

        if (!countdownTime) {
            updateEmpire(moveAll);
            updateBulletsEnemy(moveAll);
            updateBullets(moveAll);
            updateExplosions(moveAll);
        }
    }

    private void updateEmpire(float moveAll) {
        for (Iterator<Opponent> iterator = empire.iterator(); iterator.hasNext(); ) {
            Opponent opponent = iterator.next();
            if (opponent.visible) {
                opponent.translateX(moveAll);
                opponent.update();
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
                            enemyController.newTriple(2);
                        });
                }
            }
        }
    }

    private void updateBulletsEnemy(float moveAll) {
        for (Iterator<BaseBullet> iterator = bulletsEnemy.iterator(); iterator.hasNext(); ) {
            BaseBullet bullet = iterator.next();
            if (bullet.visible) {
                bullet.translateX(moveAll);
                bullet.update();
            } else {
                iterator.remove();
                postToLooper(() -> {
                    switch (bullet.name) {
                        case BOMB:
                            poolHub.bombPool.free(bullet);
                            return;
                        case BULLET_ENEMY:
                            poolHub.bulletEnemyPool.free(bullet);
                            return;
                        case SUNRISE_BOMB:
                            poolHub.sunriseBulletPool.free(bullet);
                            return;
                        case STAR_LASER:
                            poolHub.starLaserPool.free(bullet);
                    }
                });
            }
        }
    }

    private void updateBullets(float moveAll) {
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
            Bullet bullet = iterator.next();
            if (bullet.visible) {
                bullet.translateX(moveAll);
                bullet.update();
            } else {
                iterator.remove();
                postToLooper(() -> poolHub.bulletPool.free(bullet));
            }
        }
    }

    private void updateExplosions(float moveAll) {
        for (Iterator<Explosion> iterator = explosions.iterator(); iterator.hasNext(); ) {
            Explosion explosion = iterator.next();
            if (explosion.visible) {
                explosion.translateX(moveAll);
                explosion.update();
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
                Processor.postTask(() -> player.heal(20));
                opponent.kill();
            }
            return;
        }

        if (opponent.killedByPlayer(player) || (rocket.visible && opponent != rocket && opponent.killedBy(rocket))) {
            return;
        }

        for (int i = 0; i < bullets.size; i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null && bullet.visible && opponent.killedBy(bullet)) {
                score += opponent.killScore;
                return;
            }
        }

        if (opponent.name != DEMOMAN && opponent.name != FACTORY) {
            for (int i = 0; i < bulletsEnemy.size; i++) {
                BaseBullet bullet = bulletsEnemy.get(i);
                if (bullet != null && bullet.visible && bullet.name == BOMB && opponent.killedBy(bullet)) {
                    return;
                }
            }
        }
    }

    private void systemTask() {
        if (!demoman.visible && score > 70 && randomBoolean(0.0315f)) demoman.reset();
        if (!healthKit.visible && randomBoolean(0.018f)) healthKit.reset();
        if (!attention.visible && score > 50 && randomBoolean(0.06f)) attention.reset();
        if (!factory.visible && score > 170 && randomBoolean(0.013f) && numBosses == 0) {
            factory.reset();
        }

        difficultyAnalyzer.checkTime();

        if (player.isDead()) {
            Gdx.app.postRunnable(() -> {
                frontend.setScreen(new GameOverScreen());
                mainGameManager.startScene(new GameOver(mainGameManager, empire, bullets, bulletsEnemy, explosions), false);
            });
        }
    }

    private void spawnBoss() {
        if (time - lastBoss > DEATH_STAR_PERIOD_IN_SECS && numBosses == 0) {
            lastBoss = time;

            DeathStar deathStar = new DeathStar(enemyController);
            mainGameManager.startScene(new VersusScene(mainGameManager, player.name, deathStar.name), false);

            enemyController.killEmpire();

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
        poolHub.dispose();

        gameTask.shutdown();
        spawnBossTask.shutdown();
    }
}
