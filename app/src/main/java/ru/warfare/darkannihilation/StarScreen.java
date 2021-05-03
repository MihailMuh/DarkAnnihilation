package ru.warfare.darkannihilation;

public class StarScreen extends BaseScreen {
    public StarScreen(Game g) {
        super(g, ImageHub.screenImage[0].getWidth(), ImageHub.screenImage[0].getHeight(), ImageHub.screenImage.length);
    }

    @Override
    public void update() {
        frame += 1;
        if (frame == screenImageLength) {
            frame = 0;
        }
    }
}
