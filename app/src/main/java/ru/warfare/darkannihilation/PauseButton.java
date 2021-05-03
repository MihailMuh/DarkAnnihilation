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

    public PauseButton(Game game, boolean fun) {
        super(game, 0, 0);
    }

    public void show() {
        x = game.screenWidth - width * 2;
    }

    public void setCoords(int X, int Y) {
        mouseX = X;
        mouseY = Y;
        if ((game.gameStatus == 6 | game.gameStatus == 2 | game.gameStatus == 0 | game.gameStatus == 3)
            & x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + width) {
            game.player.dontmove = true;
            AudioPlayer.buttonSnd.start();
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

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.pauseButtonImg, x, y, null);
    }
}
