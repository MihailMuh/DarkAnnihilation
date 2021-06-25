package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.NUMBER_THUNDER_SCREEN_IMAGES;
import static ru.warfare.darkannihilation.Constants.THUNDER_SCREEN_FRAME_TIME;

public class ThunderScreen extends BaseScreen {
    private long lastFrame = System.currentTimeMillis();

    public ThunderScreen() {
        super(ImageHub.thunderScreen[0].getWidth(), ImageHub.thunderScreen[0].getHeight());
        frame = 0;
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