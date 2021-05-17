package ru.warfare.darkannihilation;

public class LoadingScreen extends Sprite {
    private static final int frameTime = 45;
    private long lastFrame;
    private int frame = 0;
    private int c = 0;
    private String jobs = "newGame";

    public LoadingScreen(Game g) {
        super(g, ImageHub.loadingImages[0].getWidth(), ImageHub.loadingImages[0].getHeight());
        lastFrame = System.currentTimeMillis();
    }

    public void newJob(String job) {
        c = 0;
        jobs = job;
        Game.gameStatus = 41;
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrame > frameTime) {
            lastFrame = now;
            frame++;
            c++;
            if (frame == 12) {
                frame = 0;
            }
            if (c == 11) {
                Thread thread = new Thread(() -> {
                    switch (jobs) {
                        case "newGame":
                            game.generateNewGame();
                            break;
                        case "topScore":
                            game.generateTopScore();
                            break;
                        case "menu":
                            game.generateMenu();
                            break;
                    }
                }); thread.start();
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.loadingImages[frame], 0, 0, null);
    }
}
