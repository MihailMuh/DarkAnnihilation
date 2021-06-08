package ru.warfare.darkannihilation;

public class LoadingScreen extends Sprite {
    private static final int frameTime = 45;
    private long lastFrame;
    private int frame = 0;
    private int c = 0;
    private String jobs = "newGame";
    private boolean sooFast = false;

    public LoadingScreen(Game g) {
        super(g, ImageHub.loadingImages[0].getWidth(), ImageHub.loadingImages[0].getHeight());
        lastFrame = System.currentTimeMillis();
    }

    public void newJob(String job) {
        c = 0;
        jobs = job;
        Game.gameStatus = 41;
        if (ImageHub.needImagesForFirstLevel()) {
            sooFast = true;
        }
        if (job.equals("settings")) {
            ImageHub.loadLayoutImages(game.context);
        }
    }

    @Override
    public void update() {
        if (sooFast) {
            c = 10;
            sooFast = false;
        }
        long now = System.currentTimeMillis();
        if (now - lastFrame > frameTime) {
            lastFrame = now;
            frame++;
            c++;
            if (frame == 12) {
                frame = 0;
            }
            if (c == 11) {
                new Thread(() -> {
                    switch (jobs) {
                        case "newGame":
                            game.generateNewGame();
                            break;
                        case "top":
                            game.generateTopScore();
                            break;
                        case "menu":
                            game.generateMenu();
                            break;
                        case "settings":
                            game.generateSettings();
                            break;
                    }
                }).start();
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.loadingImages[frame], 0, 0, null);
    }
}
