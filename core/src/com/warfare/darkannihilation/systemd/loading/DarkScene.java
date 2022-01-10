package com.warfare.darkannihilation.systemd.loading;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.abstraction.Scene;
import com.warfare.darkannihilation.systemd.Intent;
import com.warfare.darkannihilation.systemd.Parameters;
import com.warfare.darkannihilation.systemd.service.Processor;

public class DarkScene extends Scene {
    private Runnable runnable;
    private float alpha, time;

    @Override
    public void bootAssets(Intent intent) {
        super.bootAssets(intent);
        runnable = (Runnable) intent.get("runnable");
        time = 1f / ((float) intent.get("secs") * 60);
    }

    @Override
    public void create() {
        isShadow = true;

        clickListener = new DarkClickListener();
        Processor.multiProcessor.insertProcessor(clickListener);
    }

    @Override
    public void render() {
        spriteBatch.setColor(0, 0, 0, alpha);
        spriteBatch.draw(mainGameManager.imageHub.blackColor, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        spriteBatch.setColor(1, 1, 1, 1);

        alpha += time;
        if (alpha > 1)
            mainGameManager.finishAllScenes(new Intent(mainGameManager, Loading.class, new Parameters("runnable", runnable)));
    }
}
