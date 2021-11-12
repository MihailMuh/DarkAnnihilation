package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Frontend implements Disposable {
    static final OrthographicCamera camera = new OrthographicCamera();
    public static final SpriteBatch spriteBatch = new SpriteBatch(300);

    private final DarkGame game;

    Frontend(DarkGame darkGame) {
        game = darkGame;
    }

    public void render() {
        spriteBatch.begin();

        game.player.render();
        game.screen.render();

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
