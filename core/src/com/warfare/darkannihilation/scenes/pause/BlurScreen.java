package com.warfare.darkannihilation.scenes.pause;

import static com.warfare.darkannihilation.hub.Resources.getBatch;
import static com.warfare.darkannihilation.hub.Resources.getBlurFrameBuffer;
import static com.warfare.darkannihilation.hub.Resources.getShaders;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.hub.Resources;

public abstract class BlurScreen implements Disposable {
    public static final int MAX_BLUR_RADIUS = 10;

    private final TextureRegion frameBufferRegion;
    private final FrameBuffer blurBuffer;

    public BlurScreen(float initialRadius) {
        blurBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, SCREEN_WIDTH, SCREEN_HEIGHT, false);

        frameBufferRegion = new TextureRegion(Resources.getBlurFrameBuffer().getColorBufferTexture());
        frameBufferRegion.flip(false, true);

        setUniforms(getShaders().blurShader, initialRadius);
    }

    private void setUniforms(ShaderProgram blurShader, float initialRadius) {
        blurShader.bind();
        blurShader.setUniformf("dir", 0, 0);
        blurShader.setUniformf("resolution", SCREEN_WIDTH);
        blurShader.setUniformf("radius", initialRadius);
    }

    public abstract void changeBlurLevel();

    public void apply() {
        getBatch().flush();
        getBlurFrameBuffer().end();
        getBatch().setShader(getShaders().blurShader);

        //ensure the direction is along the X-axis only
        getShaders().blurShader.setUniformf("dir", 1, 0);
        changeBlurLevel();

        blurBuffer.begin();

        drawTextureFromFrameBuffer(getBlurFrameBuffer());

        getBatch().flush();
        blurBuffer.end();

        //ensure the direction is along the Y-axis only
        getShaders().blurShader.setUniformf("dir", 0, 1);

        drawTextureFromFrameBuffer(blurBuffer);

        getBatch().setShader(null);
    }

    private void drawTextureFromFrameBuffer(FrameBuffer frameBuffer) {
        frameBufferRegion.setTexture(frameBuffer.getColorBufferTexture());
        getBatch().draw(frameBufferRegion, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void dispose() {
        blurBuffer.dispose();
    }
}
