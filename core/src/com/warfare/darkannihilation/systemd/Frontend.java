package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.abstraction.BaseEnemy;

public class Frontend implements Disposable {
    static final OrthographicCamera camera = new OrthographicCamera();
    public static final SpriteBatch spriteBatch = new SpriteBatch(300);

    private final MainGame game;

    Frontend(MainGame mainGame) {
        game = mainGame;
    }

    public void render() {
        spriteBatch.begin();

        game.screen.render();

        for (BaseEnemy enemy : game.empire) {
            enemy.render();
        }

        game.player.render();

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
