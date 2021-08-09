package ru.warfare.darkannihilation.screen;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.Service;

import static ru.warfare.darkannihilation.constant.Constants.LOADING_SCREEN_FRAME_TIME;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_LOADING_SCREEN_IMAGES;
import static ru.warfare.darkannihilation.constant.Modes.LOADING;

public class LoadingScreen extends Sprite {
    private long lastFrame = System.currentTimeMillis();
    private int frame = 0;
    private int c = 0;
    private Runnable function;

    public LoadingScreen(Game g) {
        super(g, ImageHub.loadingImages[0]);
    }

    public void launch(Runnable function, boolean longLoading) {
        this.function = function;
        if (longLoading) {
            c = 8;
        } else {
            c = 0;
        }
        Game.gameStatus = LOADING;
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrame > LOADING_SCREEN_FRAME_TIME) {
            lastFrame = now;
            frame++;
            c++;
            if (frame == NUMBER_LOADING_SCREEN_IMAGES) {
                frame = 0;
            }
            if (c == 9) {
                Service.runOnUiThread(function);
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.loadingImages[frame], 0, 0, null);
    }

    @Override
    public void kill() {
    }
}
