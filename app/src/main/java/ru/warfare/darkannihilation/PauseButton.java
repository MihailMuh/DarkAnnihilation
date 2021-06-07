package ru.warfare.darkannihilation;

public class PauseButton extends Sprite {
    public static int oldStatus;

    public PauseButton(Game g) {
        super(g, ImageHub.pauseButtonImg.getWidth(), ImageHub.pauseButtonImg.getHeight());

        y = 20;
        show();
    }

    public void show() {
        x = Game.screenWidth - (width * 2);
    }

    public void make() {
        AudioHub.playClick();
        oldStatus = Game.gameStatus;
        game.generatePause();
    }

    public boolean checkCoords(int X, int Y) {
        if (x < X) {
            if (X < right()) {
                if (y < Y) {
                    return Y < bottom();
                }
            }
        }
        return false;
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.pauseButtonImg, x, y, null);
    }
}
