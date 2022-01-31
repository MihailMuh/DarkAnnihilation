package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.utils.FontWrap;
import com.warfare.darkannihilation.utils.ScenesStack;

public class Frontend implements Disposable {
    private static final int x = (int) (SCREEN_WIDTH - 300);
    private static final int y = SCREEN_HEIGHT - 100;

    public static final OrthographicCamera camera = new OrthographicCamera();
    public static final CpuSpriteBatch spriteBatch = new CpuSpriteBatch(300);

    private final ScenesStack scenesStack;
    private final FontWrap fontWrap;

    Frontend(MainGame mainGame, FontWrap fontWrap) {
        scenesStack = mainGame.scenesStack;
        this.fontWrap = fontWrap;

        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void render() {
        spriteBatch.begin();

        for (Scene scene : scenesStack) {
            scene.render();
        }

        fontWrap.draw(x, y, String.valueOf(Gdx.graphics.getFramesPerSecond()));

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
