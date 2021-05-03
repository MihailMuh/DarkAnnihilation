package ru.warfare.darkannihilation;

public class ThunderScreen extends BaseScreen {
    private static final int frameTime = 80;
    private long lastFrame;

    public ThunderScreen(Game g) {
        super(g, ImageHub.thunderScreen[0].getWidth(), ImageHub.thunderScreen[0].getHeight(), ImageHub.thunderScreen.length);
        lastFrame = System.currentTimeMillis();
        frame = 0;
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrame > frameTime) {
            lastFrame = now;
            frame += 1;
            if (frame == screenImageLength) {
                frame = 0;
            }
        }
    }
}