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

    public void setCoords(int X, int Y) {
        mouseX = X;
        mouseY = Y;
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + width & game.gameStatus != 4 & game.gameStatus != 5) {
            game.player[0].dontmove = true;
            AudioPlayer.buttonSnd.start();
            mouseX = 0;
            mouseY = 0;
            oldStatus = game.gameStatus;
            game.generatePause();
        } else {
            if (game.gameStatus != 4) {
                game.player[0].dontmove = false;
            }
        }
    }

    public void setCoords(int X, int Y, int count) {
        mouseX = X;
        mouseY = Y;
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + width & game.gameStatus != 4 & game.gameStatus != 5) {
            AudioPlayer.buttonSnd.start();
            mouseX = 0;
            mouseY = 0;
            oldStatus = game.gameStatus;
            game.generatePause();
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.pauseButtonImg, x, y, null);
    }
}
