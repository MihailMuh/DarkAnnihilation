package com.warfare.darkannihilation.scenes.pause;

import static com.badlogic.gdx.utils.TimeUtils.millis;
import static com.warfare.darkannihilation.hub.Resources.getAssetManager;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getShaders;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.scenes.pause.BlurScreen.MAX_BLUR_RADIUS;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static java.lang.Math.min;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.scenes.firstlevel.FirstLevel;
import com.warfare.darkannihilation.scenes.menu.Menu;
import com.warfare.darkannihilation.systemd.Frontend;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Watch;
import com.warfare.darkannihilation.widgets.Button;

public class Pause extends Scene {
    private final Array<Button> buttons = new Array<>(true, 3, Button.class);
    private BlurScreen blurScreen;

    private final float firstLevelMusicLoudOld = getSounds().firstLevelMusic.getVolume();
    private float currentBlur, firstLevelMusicLoud = firstLevelMusicLoudOld;
    private long lastBlur;

    public Pause(MainGameManager mainGameManager) {
        super(mainGameManager);

        ShaderProgram.pedantic = false;
        Frontend.enableBlurBuffer = true;
        Watch.stopTime();
    }

    @Override
    public void create() {
        super.create();

        loadResources();
        initButtons();

        blurScreen = new BlurScreen(0) {
            @Override
            public void changeBlurLevel() {
                // не испольую time, т.к. Watch.stopTime();
                if (millis() - lastBlur >= 17) {
                    lastBlur = millis();

                    currentBlur += 0.1f;
                    currentBlur = min(currentBlur, MAX_BLUR_RADIUS);

                    changeFirstLevelMusicLoud();

                    getShaders().blurShader.setUniformf("radius", currentBlur);
                }
            }
        };

        clickListener = new PauseClickListener(this::finish, buttons);
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    private void changeFirstLevelMusicLoud() {
        if (!getSounds().firstLevelMusic.isPlaying()) return;

        firstLevelMusicLoud -= 0.1f / MAX_BLUR_RADIUS;

        if (firstLevelMusicLoud <= 0) {
            getSounds().firstLevelMusic.pause();
        }
        getSounds().firstLevelMusic.setVolume(firstLevelMusicLoud);
    }

    private void initButtons() {
        Button buttonContinue = new Button(getLocales().continueStr, 0, 0, this::finish);
        buttonContinue.setCenter(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT + 200);

        Button buttonRestart = new Button(getLocales().restart, 0, 0, () -> mainGameManager.startScene(true, FirstLevel.class, mainGameManager));
        buttonRestart.setCenter(HALF_SCREEN_WIDTH, buttonContinue.getY() - 120);

        Button buttonToMenu = new Button(getLocales().toMenu, 0, 0, () -> mainGameManager.startScene(true, Menu.class, mainGameManager));
        buttonToMenu.setCenter(HALF_SCREEN_WIDTH, buttonRestart.getY() - 120);

        Button buttonExit = new Button(getLocales().quit, 0, 0, Gdx.app::exit);
        buttonExit.setCenter(HALF_SCREEN_WIDTH, buttonToMenu.getY() - 120);

        buttons.addAll(buttonContinue, buttonRestart, buttonToMenu, buttonExit);
    }

    private void finish() {
        Gdx.app.postRunnable(() -> {
            mainGameManager.insertScene(this, new Countdown(mainGameManager, currentBlur, firstLevelMusicLoudOld), false);
            mainGameManager.finishScene(this);
        });
    }

    private void loadResources() {
        getSounds().loadPauseMusic();
        getShaders().loadBlurShader();

        getAssetManager().finishLoading();

        getShaders().getBlurShader();
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
        blurScreen.apply();

        for (Button button : buttons) {
            button.render();
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        getSounds().disposePauseMusic();
    }
}
