package com.warfare.darkannihilation.systemd;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.warfare.darkannihilation.Settings.SHOW_FPS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.systemd.service.Windows.DEVICE_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.DEVICE_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.Resources;
import com.warfare.darkannihilation.utils.Font;
import com.warfare.darkannihilation.utils.ScenesStack;
import com.warfare.darkannihilation.utils.SpriteBatchSuper;

public class Frontend implements Disposable {
    private final ScenesStack scenesStack;

    private static final Font fpsFont = new Font(getFonts().canisMinor, 0.25f);
    private static final int fpsX = SCREEN_WIDTH - 200;
    private static final int fpsY = SCREEN_HEIGHT - 100;

    private static final OrthographicCamera camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
    private static final StretchViewport viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
    public static final SpriteBatchSuper spriteBatch = new SpriteBatchSuper(NUMBER_VADER * 80);

    Frontend(ScenesStack scenesStack) {
        this.scenesStack = scenesStack;
        Resources.setBatch(spriteBatch);
    }

    void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public static void unproject(Vector3 vector3) {
        camera.unproject(vector3, 0, 0, DEVICE_WIDTH, DEVICE_HEIGHT);
    }

    public void render() {
        gl.glClear(GL_COLOR_BUFFER_BIT);

        spriteBatch.disableBlending();
        spriteBatch.begin();

        for (Scene scene : scenesStack) {
            scene.render();
        }

        if (SHOW_FPS) {
            fpsFont.draw(SCREEN_WIDTH - 450, fpsY - 100, "RenderCalls: " + spriteBatch.renderCalls);
            fpsFont.draw(SCREEN_WIDTH - 450, fpsY - 200, "Max sprites: " + spriteBatch.maxSpritesInBatch);
            fpsFont.draw(fpsX, fpsY, String.valueOf(Gdx.graphics.getFramesPerSecond()));
        }

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
