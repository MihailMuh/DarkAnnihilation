package com.warfare.darkannihilation.scenes.pause;

import static com.badlogic.gdx.utils.TimeUtils.millis;
import static com.warfare.darkannihilation.hub.Resources.getAssetManager;
import static com.warfare.darkannihilation.hub.Resources.getBatch;
import static com.warfare.darkannihilation.hub.Resources.getBlurFrameBuffer;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;
import static java.lang.Math.min;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.hub.Resources;
import com.warfare.darkannihilation.scenes.firstlevel.FirstLevel;
import com.warfare.darkannihilation.scenes.menu.Menu;
import com.warfare.darkannihilation.systemd.Frontend;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.widgets.Button;

public class Pause extends Scene {
    private final Array<Button> buttons = new Array<>(true, 3, Button.class);
    private final TextureRegion frameBufferRegion;
    private final FrameBuffer blurTarget;

    private ShaderProgram blurShader;

    private float currentBlur;
    private long lastBlur;

    public Pause(MainGameManager mainGameManager) {
        super(mainGameManager);

        ShaderProgram.pedantic = false;
        Frontend.enableBlurBuffer = true;
        Watch.stopTime();

        initBlurShader();

        blurTarget = new FrameBuffer(Pixmap.Format.RGBA8888, SCREEN_WIDTH, SCREEN_HEIGHT, false);

        frameBufferRegion = new TextureRegion(Resources.getBlurFrameBuffer().getColorBufferTexture());
        frameBufferRegion.flip(false, true);
    }

    private void initBlurShader() {
        blurShader = new ShaderProgram(Gdx.files.internal("shaders/blur.vert"), Gdx.files.internal("shaders/blur.frag"));
        if (!blurShader.isCompiled() || blurShader.getLog().length() != 0) {
            Processor.postTask(() -> {
                throw new RuntimeException(blurShader.getLog());
            });
        }

        blurShader.bind();
        blurShader.setUniformf("dir", 0, 0);
        blurShader.setUniformf("resolution", SCREEN_WIDTH);
        blurShader.setUniformf("radius", 0);
    }

    @Override
    public void create() {
        super.create();

        loadPauseMusic();

        Button buttonContinue = new Button(getLocales().continueStr, 0, 0, () -> Gdx.app.postRunnable(() -> mainGameManager.finishScene(this)));
        buttonContinue.setCenter(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT + 200);

        Button buttonRestart = new Button(getLocales().restart, 0, 0, () -> mainGameManager.startScene(true, FirstLevel.class, mainGameManager));
        buttonRestart.setCenter(HALF_SCREEN_WIDTH, buttonContinue.getY() - 120);

        Button buttonToMenu = new Button(getLocales().toMenu, 0, 0, () -> mainGameManager.startScene(true, Menu.class, mainGameManager));
        buttonToMenu.setCenter(HALF_SCREEN_WIDTH, buttonRestart.getY() - 120);

        Button buttonExit = new Button(getLocales().quit, 0, 0, Gdx.app::exit);
        buttonExit.setCenter(HALF_SCREEN_WIDTH, buttonToMenu.getY() - 120);

        buttons.addAll(buttonContinue, buttonRestart, buttonToMenu, buttonExit);

        clickListener = new PauseClickListener(() -> Gdx.app.postRunnable(() -> mainGameManager.finishScene(this)), buttons);
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    private void loadPauseMusic() {
        getSounds().loadPauseMusic();
        getAssetManager().finishLoading();
        getSounds().getPauseMusic();
    }

    @Override
    public void pause() {
        super.pause();
        getSounds().pauseMusic.pause();
    }

    @Override
    public void resume() {
        super.resume();
        getSounds().pauseMusic.play();
    }

    @Override
    public void render() {
        getBatch().flush();
        getBlurFrameBuffer().end();
        getBatch().setShader(blurShader);

        //ensure the direction is along the X-axis only
        blurShader.setUniformf("dir", 1, 0);
        updateBlurLevel();

        blurTarget.begin();

        drawTextureFromFrameBuffer(getBlurFrameBuffer());

        getBatch().flush();
        blurTarget.end();

        //ensure the direction is along the Y-axis only
        blurShader.setUniformf("dir", 0, 1);

        drawTextureFromFrameBuffer(blurTarget);

        getBatch().setShader(null);

        for (Button button : buttons) {
            button.render();
        }
    }

    private void drawTextureFromFrameBuffer(FrameBuffer frameBuffer) {
        frameBufferRegion.setTexture(frameBuffer.getColorBufferTexture());
        getBatch().draw(frameBufferRegion, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void updateBlurLevel() {
        // не испольую time, т.к. Watch.stopTime();
        if (millis() - lastBlur >= 17) {
            lastBlur = millis();
            currentBlur += 0.1f;

            blurShader.setUniformf("radius", min(currentBlur, 10));
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        blurShader.dispose();
        getBatch().setShader(null);
        getSounds().disposePauseMusic();

        ShaderProgram.pedantic = true;
        Frontend.enableBlurBuffer = false;

        Watch.resetTime();
    }
}
