package ru.warfare.darkannihilation;

public class PauseButton extends Sprite {
    public int mouseX;
    public int mouseY;
    public int oldStatus;

    public PauseButton(Game g) {
        super(g, ImageHub.pauseButtonImg.getWidth(), 0);

        y = 20;
        x = game.screenWidth - width * 2;
    }

    public void show() {
        x = game.screenWidth - width * 2;
    }

    public void make() {
        AudioPlayer.playClick();
        mouseX = 0;
        mouseY = 0;
        oldStatus = game.gameStatus;
        game.generatePause();
    }

    public boolean checkCoords(int X, int Y) {
        return (x < X & X < x + width & y < Y & Y < y + width);
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.pauseButtonImg, x, y, null);
    }
}
