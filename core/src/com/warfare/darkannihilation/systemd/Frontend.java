package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.utils.ScenesStack;
import com.warfare.darkannihilation.utils.SpriteBatchSuper;

public class Frontend implements Disposable {
    public static final OrthographicCamera camera = new OrthographicCamera();
    public static final SpriteBatchSuper spriteBatch = new SpriteBatchSuper(40);

    private final ScenesStack scenesStack;

    Frontend(MainGame mainGame) {
        scenesStack = mainGame.scenesStack;

        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.disableBlending();
        spriteBatch.begin();

        for (Scene scene : scenesStack) {
            scene.render();
        }

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
