package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.LOADING_SCREEN_FRAME_TIME;
import static ru.warfare.darkannihilation.Constants.NUMBER_LOADING_SCREEN_IMAGES;

public class LoadingScreen extends Sprite {
    private long lastFrame = System.currentTimeMillis();
    private int frame = 0;
    private int c = 0;
    private String jobs = "newGame";
    private boolean sooFast = false;

    public LoadingScreen(Game g) {
        super(g, ImageHub.loadingImages[0].getWidth(), ImageHub.loadingImages[0].getHeight());
    }

    public void newJob(String job) {
        c = 0;
        jobs = job;
        Game.gameStatus = 41;
        if (job.equals("settings")) {
            ImageHub.loadSettingsImages(game.context);
        } else {
            if (ImageHub.needImagesForFirstLevel()) {
                sooFast = true;
            }
        }
    }

    @Override
    public void update() {
        if (sooFast) {
            c = 10;
            sooFast = false;
        }
        long now = System.currentTimeMillis();
        if (now - lastFrame > LOADING_SCREEN_FRAME_TIME) {
            lastFrame = now;
            frame++;
            c++;
            if (frame == NUMBER_LOADING_SCREEN_IMAGES) {
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
