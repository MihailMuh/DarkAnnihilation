package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.MainGame.explosions;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.bullet.Bullet;

public class Frontend implements Disposable {
    static final OrthographicCamera camera = new OrthographicCamera();
    public static final SpriteBatch spriteBatch = new SpriteBatch(300);

    private final MainGame game;

    Frontend(MainGame mainGame) {
        game = mainGame;
    }

    public void render() {
        spriteBatch.begin();

        game.scene.render();
//        game.screen.render();
//        game.player.render();
//
//        for (Warrior enemy : game.empire) {
//            enemy.render();
//        }
//        for (Bullet bullet : game.bullets) {
//            bullet.render();
//        }
//        for (Explosion explosion : explosions) {
//            explosion.render();
//        }

        spriteBatch.end();
    }

    public void onResize() {
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
