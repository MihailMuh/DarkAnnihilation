package ru.warfare.darkannihilation;

public class StarScreen extends BaseScreen {
    public StarScreen() {
        super(ImageHub.screenImage[0].getWidth(), ImageHub.screenImage[0].getHeight(), ImageHub.screenImage.length);
    }

    @Override
    public void update() {
        frame++;
        if (frame == screenImageLength) {
            frame = 0;
        }
    }
}
