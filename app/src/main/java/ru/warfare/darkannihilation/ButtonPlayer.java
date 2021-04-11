package ru.warfare.darkannihilation;

public class ButtonPlayer extends Sprite {
    public ButtonPlayer(Game g) {
        super(g, ImageHub.buttonPlayerImg.getWidth(), ImageHub.buttonPlayerImg.getHeight());

        y = halfScreenHeight - halfHeight;
        hide();
    }

    public void hide() {
        x = screenWidth;
    }

    public void show() {
        x = halfScreenWidth - width * 2;
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            AudioPlayer.buttonSnd.start();
            Game.character = "ship";
            game.generateNewGame();
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.buttonPlayerImg, x, y, null);
    }
}
