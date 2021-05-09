package ru.warfare.darkannihilation;

public class LoadingScreen extends Sprite {
    private static final int frameTime = 45;
    private long lastFrame;
    public int frame = 0;
    public int c = 0;
    public static String jobs = "newGame";
    public LoadingScreen(Game g) {
        super(g, ImageHub.loadingImages[0].getWidth(), ImageHub.loadingImages[0].getHeight());
        lastFrame = System.currentTimeMillis();
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
            if (c == 14) {
                c = 0;
                Thread thread = new Thread() {
                    @Override
                    public void run() {
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
                    }
                }; thread.start();
            }
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.loadingImages[frame], 0, 0, null);
    }
}
