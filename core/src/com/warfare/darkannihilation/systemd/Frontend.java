package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.FontHub;

public class Frontend implements Disposable {
    private static final float fpsX = SCREEN_WIDTH - 250;
    private static final float fpsY = SCREEN_HEIGHT - 100;
    static final OrthographicCamera camera = new OrthographicCamera();
    public static final CpuSpriteBatch spriteBatch = new CpuSpriteBatch(300);

    private final MainGame game;

    Frontend(MainGame mainGame) {
        game = mainGame;

        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void render() {
        spriteBatch.begin();

        for (Scene scene : game.scenes) {
            scene.render();
        }
        FontHub.canisMinor.draw(spriteBatch, String.valueOf(Gdx.graphics.getFramesPerSecond()), fpsX, fpsY);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
