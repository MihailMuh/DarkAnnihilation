package ru.warfare.darkannihilation;

public class PauseButton {
    public int x;
    public int y;
    public int mouseX;
    public int mouseY;
    public int width;
    public final Game game;
    public int oldStatus;

    public PauseButton(Game g) {
        game = g;

        width = ImageHub.pauseButtonImg.getWidth();

        y = 20;
        x = game.screenWidth - width * 2;
    }

    public void setCoords(int X, int Y) {
        mouseX = X;
        mouseY = Y;
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + width & game.gameStatus != 4) {
            mouseX = 0;
            mouseY = 0;
            oldStatus = game.gameStatus;
            game.generatePause();
        } else {
            if (game.gameStatus != 4) {
                game.player.dontmove = false;
            }
        }
    }

    public void update() {
        game.canvas.drawBitmap(ImageHub.pauseButtonImg, x, y, null);
    }
}
