package com.warfare.darkannihilation.systemd.loading;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.MainGameManager;

public class DarkScene extends Scene {
    private final Runnable runnable;
    private final float time;
    private float alpha;

    public DarkScene(MainGameManager mainGameManager, Runnable runnable, float secs) {
        super(mainGameManager, new DarkClickListener());
        isShadow = true;

        this.runnable = runnable;
        time = 1f / (secs * 60);
    }

    @Override
    public void render() {
        spriteBatch.setColor(0, 0, 0, alpha);
        spriteBatch.draw(mainGameManager.imageHub.blackColor, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        spriteBatch.setColor(1, 1, 1, 1);

        alpha += time;
        if (alpha > 1)
            mainGameManager.finishAllScenes(new Loading(mainGameManager, runnable));
    }
}
