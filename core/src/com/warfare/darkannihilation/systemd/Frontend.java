package com.warfare.darkannihilation.systemd;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.warfare.darkannihilation.Settings.SHOW_FPS;
import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;
import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.warfare.darkannihilation.hub.Resources;
import com.warfare.darkannihilation.utils.Font;
import com.warfare.darkannihilation.utils.ScenesArray;

public class Frontend implements Disposable {
    private final ScenesArray scenesArray;

    private final Font fpsFont = new Font(getFonts().canisMinor, 0.18f);
    private final int fpsX = SCREEN_WIDTH - 150;
    private final int fpsY = SCREEN_HEIGHT - 300;

    private final OrthographicCamera camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
    private final StretchViewport viewport = new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
    private final CpuSpriteBatch spriteBatch = new CpuSpriteBatch(NUMBER_VADER * 80);

    private FrameBuffer blurFrameBuffer;

    public static boolean enableBlurBuffer = false;

    Frontend(ScenesArray scenesArray) {
        this.scenesArray = scenesArray;
        Resources.setBatch(spriteBatch);
        Resources.setViewport(viewport);
    }

    void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(camera.combined);

        blurFrameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        Resources.setBlurFrameBuffer(blurFrameBuffer);
    }

    public void render() {
        if (enableBlurBuffer) {
            blurFrameBuffer.begin();
            gl.glClear(GL_COLOR_BUFFER_BIT);
            spriteBatch.setShader(null);
        } else {
            gl.glClear(GL_COLOR_BUFFER_BIT);
        }

        spriteBatch.disableBlending();
        spriteBatch.begin();

        scenesArray.renderScenes();

        if (SHOW_FPS) {
            fpsFont.draw(SCREEN_WIDTH - 350, fpsY - 100, "RenderCalls: " + spriteBatch.renderCalls);
            fpsFont.draw(SCREEN_WIDTH - 350, fpsY - 200, "Max sprites: " + spriteBatch.maxSpritesInBatch);
            fpsFont.draw(fpsX, fpsY, String.valueOf(Gdx.graphics.getFramesPerSecond()));
        }

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        blurFrameBuffer.dispose();
    }
}
