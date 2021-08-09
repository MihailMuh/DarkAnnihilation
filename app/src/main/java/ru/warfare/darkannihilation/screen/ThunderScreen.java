package ru.warfare.darkannihilation.screen;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseScreen;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_THUNDER_SCREEN_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.THUNDER_SCREEN_FRAME_TIME;

public class ThunderScreen extends BaseScreen {
    private long lastFrame = System.currentTimeMillis();

    public ThunderScreen() {
        super(ImageHub.thunderScreen);
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrame > THUNDER_SCREEN_FRAME_TIME) {
            lastFrame = now;
            frame++;
            if (frame == NUMBER_THUNDER_SCREEN_IMAGES) {
                frame = 0;
            }
        }
    }
}