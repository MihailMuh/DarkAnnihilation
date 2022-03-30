package com.warfare.darkannihilation.systemd.firstlevel;

import static com.badlogic.gdx.math.MathUtils.randomBoolean;
import static com.warfare.darkannihilation.constants.Constants.MIN_NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_EXPLOSION;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_MILLENNIUM_FALCON_BULLETS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Names.BOMB;
import static com.warfare.darkannihilation.constants.Names.TRIPLE;
import static com.warfare.darkannihilation.constants.Names.VADER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Processor.postToLooper;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.enemy.Attention;
import com.warfare.darkannihilation.enemy.Demoman;
import com.warfare.darkannihilation.enemy.Rocket;
import com.warfare.darkannihilation.enemy.TripleFighter;
import com.warfare.darkannihilation.enemy.Vader;
import com.warfare.darkannihilation.hub.PoolHub;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.pools.OpponentPool;
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
    private OpponentPool vaderPool, triplePool;

    private boolean firstRun = true;
    private boolean single = false;
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

        vaderPool = new OpponentPool(empire, (int) (NUMBER_VADER * 2.5)) {
            @Override
            protected Vader newObject() {
                return new Vader();
            }
        };
        triplePool = new OpponentPool(empire, NUMBER_VADER) {
            @Override
            protected TripleFighter newObject() {
                return new TripleFighter(player);
            }
        };

        player = new Player(difficultyAnalyzer);
        screen = new DynamicScreen(getImages().starScreenGIF);
        frontend = new Frontend(this, player, screen, explosions, bullets, empire, bulletsEnemy);

        Processor.post(this::initEnemies);
    }

    private void initEnemies() {
        newVader(NUMBER_VADER);
        newTriple();

        demoman = new Demoman();
        healthKit = new HealthKit();
        Rocket rocket = new Rocket();
        attention = new Attention(rocket);
        empire.add(demoman, healthKit, attention, rocket);

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
        Player player = this.player;

        for (Iterator<Opponent> iterator = empire.iterator(); iterator.hasNext(); ) {
            Opponent opponent = iterator.next();
            if (opponent.visible) {
                opponent.x -= moveAll;
                opponent.update();
                if (opponent.killedBy(player)) {
                    continue;
                }

                for (Bullet bullet : bullets) {
                    if (bullet.visible && opponent.killedBy(bullet)) {
                        score += opponent.killScore;
                        break;
                    }
                }
            } else {
                if (opponent.name == VADER) {
                    iterator.remove();
                    postToLooper(() -> vaderPool.free(opponent));
                } else {
                    if (opponent.name == TRIPLE) {
                        iterator.remove();
                        postToLooper(() -> triplePool.free(opponent));
                    }
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
                bullet.killedBy(player);
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

    private void systemTask() {
        if (!demoman.visible && score > 70 && randomBoolean(0.0315f)) demoman.reset();

        if (!healthKit.visible && randomBoolean(0.015f)) healthKit.reset();

        if (!attention.visible && score > 50 && randomBoolean(0.06f)) attention.reset();

        if (player.isDead()) {
            frontend.setScreen(new GameOverScreen());
            mainGameManager.startScene(new GameOver(mainGameManager, empire, bullets, bulletsEnemy, explosions), false);
        }

        if (empire.size - 4 < MIN_NUMBER_VADER) {
            newVader(1);
        }

        difficultyAnalyzer.checkTime();
    }

    public void newTriple() {
        triplePool.obtain();
    }

    public void newVader(int count) {
        for (int i = 0; i < count; i++) {
            vaderPool.obtain();
        }
    }

    public void killTriple() {
        for (int i = empire.size - 1; i >= 0; i--) {
            Opponent opponent = empire.get(i);

            if (opponent.name == TRIPLE && !opponent.shouldKill) {
                opponent.shouldKill = true;
                break;
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
        getImages().disposeFirstLevelImages();
        getSounds().disposeGameSounds();
        poolHub.disposePools();
    }
}
