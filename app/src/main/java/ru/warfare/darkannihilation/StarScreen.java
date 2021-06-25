package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.NUMBER_STAR_SCREEN_IMAGES;

public class StarScreen extends BaseScreen {
    public StarScreen() {
        super(ImageHub.screenImage[0].getWidth(), ImageHub.screenImage[0].getHeight());
    }

    @Override
    public void update() {
        frame++;
        if (frame == NUMBER_STAR_SCREEN_IMAGES) {
            frame = 0;
        }
    }
}
