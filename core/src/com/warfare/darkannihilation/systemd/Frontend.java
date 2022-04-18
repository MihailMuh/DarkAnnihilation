package com.warfare.darkannihilation.systemd;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.utils.FontWrap;
import com.warfare.darkannihilation.utils.ScenesStack;
import com.warfare.darkannihilation.utils.SpriteBatchSuper;

public class Frontend implements Disposable {
    private final ScenesStack scenesStack;

    private final FontWrap fontWrap = new FontWrap(getFonts().canisMinor, 0.5f);
    private final int x = SCREEN_WIDTH - 200;
    private final int y = SCREEN_HEIGHT - 100;

    public static final OrthographicCamera camera = new OrthographicCamera();
    public static final SpriteBatchSuper spriteBatch = new SpriteBatchSuper(NUMBER_VADER * 80);

    Frontend(MainGame mainGame) {
        scenesStack = mainGame.scenesStack;

        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void render() {
        gl.glClear(GL_COLOR_BUFFER_BIT);

        spriteBatch.disableBlending();
        spriteBatch.begin();

        for (Scene scene : scenesStack) {
            scene.render();
        }

//        fontWrap.draw(x, y, String.valueOf(Gdx.graphics.getFramesPerSecond()));
//        fontWrap.draw(HALF_SCREEN_WIDTH, y, spriteBatch.renderCalls + " " + spriteBatch.maxSpritesInBatch);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
