package ru.warfare.darkannihilation;

public class ThunderScreen extends BaseScreen {
    private static final int frameTime = 135;
    private long lastFrame;

    public ThunderScreen() {
        super(ImageHub.thunderScreen[0].getWidth(), ImageHub.thunderScreen[0].getHeight(), ImageHub.thunderScreen.length);
        lastFrame = System.currentTimeMillis();
        frame = 0;
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrame > frameTime) {
            lastFrame = now;
            frame++;
            if (frame == screenImageLength) {
                frame = 0;
            }
        }
    }
}