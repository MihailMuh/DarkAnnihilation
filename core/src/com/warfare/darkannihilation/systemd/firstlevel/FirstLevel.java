package com.warfare.darkannihilation.systemd.firstlevel;

import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Constants.MIN_NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_EXPLOSION;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Names.BOMB;
import static com.warfare.darkannihilation.constants.Names.DEMOMAN;
import static com.warfare.darkannihilation.constants.Names.FACTORY;
import static com.warfare.darkannihilation.constants.Names.MINION;
import static com.warfare.darkannihilation.constants.Names.TRIPLE;
import static com.warfare.darkannihilation.constants.Names.VADER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Processor.postToLooper;
import static com.warfare.darkannihilation.systemd.service.Watch.frameCount;

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
import com.warfare.darkannihilation.hub.PoolHub;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.screens.DynamicScreen;
import com.warfare.darkannihilation.support.HealthKit;
import com.warfare.darkannihilation.systemd.DifficultyAnalyzer;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.gameover.GameOver;
import com.warfare.darkannihilation.systemd.gameover.GameOverScreen;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.utils.GameTask;

import java.util.Iterator;

public class FirstLevel extends Scene {
    private final GameTask gameTask = new GameTask(this::systemTask, 250);
    private final DifficultyAnalyzer difficultyAnalyzer = new DifficultyAnalyzer(this);
    private final PoolHub poolHub = getPools();

    private final Array<Explosion> explosions = new Array<>(false, NUMBER_EXPLOSION);
    private final Array<Bullet> bullets = new Array<>(false, NUMBER_MILLENNIUM_FALCON_BULLETS);
    private final Array<BaseBullet> bulletsEnemy = new Array<>(false, 30);
    private final Array<Opponent> empire = new Array<>(false, 40);

    private Frontend frontend;
    private Player player;
    private Demoman demoman;
    private HealthKit healthKit;
    private Attention attention;
    private Rocket rocket;
    public Factory factory;

    private boolean firstRun = true;
    private boolean startAnalytics = false;
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

        player = new Player(difficultyAnalyzer);
        screen = new DynamicScreen(getImages().starScreenGIF);
        frontend = new Frontend(this, player, screen, explosions, bullets, empire, bulletsEnemy);

        poolHub.initWithPlayer(empire, player);
        Processor.post(this::initEnemies);
    }

    private void initEnemies() {
        newVader(NUMBER_VADER);
        newTriple();

        demoman = new Demoman();
        healthKit = new HealthKit();
        rocket = new Rocket();
        attention = new Attention(rocket);
        factory = new Factory();
        empire.addAll(demoman, healthKit, attention, rocket, factory);

        clickListener = new GameClickListener(player, mainGameManager);
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    @Override
    public void resume() {
        if (!firstRun) {
            gameTask.start();

            if (!startAnalytics) {
                startAnalytics = true;
                difficultyAnalyzer.startCollecting();
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
    }

    @Override
    public void update() {
        float moveAll = player.speedX / 2.8f;
        screen.x -= moveAll;

        player.update();
        player.shoot();

        updateEmpire(moveAll);
        updateBulletsEnemy(moveAll);

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

    private void updateEmpire(float moveAll) {
        for (Iterator<Opponent> iterator = empire.iterator(); iterator.hasNext(); ) {
            Opponent opponent = iterator.next();
            if (opponent.visible) {
                opponent.x -= moveAll;
                opponent.update();
                checkIntersectionsWithAnybody(opponent);
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
                }
            }
        }
    }

    private void updateBulletsEnemy(float moveAll) {
        Player player = this.player;

        for (Iterator<BaseBullet> iterator = bulletsEnemy.iterator(); iterator.hasNext(); ) {
            BaseBullet bullet = iterator.next();
            if (bullet.visible) {
                bullet.x -= moveAll;
                bullet.update();
                if (frameCount % 2 == 0) {
                    bullet.killedBy(player);
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
        if (frameCount % 2 != 0) {
            return;
        }

        if (opponent.killedBy(player) || (rocket.visible && opponent != rocket && opponent.killedBy(rocket))) {
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
        if (!healthKit.visible && randomBoolean(0.0155f)) healthKit.reset();
        if (!attention.visible && score > 50 && randomBoolean(0.06f)) attention.reset();
        if (!factory.visible && score > 170 && randomBoolean(0.0135f)) factory.reset();

        if (player.isDead()) {
            frontend.setScreen(new GameOverScreen());
            mainGameManager.startScene(new GameOver(mainGameManager, empire, bullets, bulletsEnemy, explosions), false);
        }

        difficultyAnalyzer.checkTime();
    }

    public void newTriple() {
        poolHub.triplePool.obtain();
    }

    public void newVader(int count) {
        for (int i = 0; i < count; i++) {
            poolHub.vaderPool.obtain();
        }
    }

    public void killTriple() {
        Array<Opponent> empire = this.empire;

        for (int i = empire.size - 1; i >= 0; i--) {
            Opponent opponent = empire.get(i);

            if (opponent.name == TRIPLE && !opponent.shouldKill) {
                opponent.shouldKill = true;
                return;
            }
        }
    }

    public void killVader() {
        Array<Opponent> empire = this.empire;
        long numVaders = getNumberVaders(empire);

        if (numVaders <= MIN_NUMBER_VADER) {
            newVader((int) (MIN_NUMBER_VADER - numVaders));
            killTriple();
        } else {
            for (int i = 0; i < empire.size; i++) {
                Opponent opponent = empire.get(i);
                if (opponent.name == VADER && !opponent.shouldKill) {
                    opponent.shouldKill = true;
                    return;
                }
            }
        }
    }

    private int getNumberVaders(Array<Opponent> empire) {
        int numVaders = 0;

        for (int i = 0; i < empire.size; i++) {
            Opponent opponent = empire.get(i);
            if (opponent.name == VADER && !opponent.shouldKill) {
                numVaders++;
            }
        }
        return numVaders;
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
