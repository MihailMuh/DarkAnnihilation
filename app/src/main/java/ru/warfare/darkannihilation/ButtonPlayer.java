package ru.warfare.darkannihilation;

public class ButtonPlayer extends Sprite {
    public ButtonPlayer(Game g) {
        super(g, ImageHub.buttonPlayerImg.getWidth(), ImageHub.buttonPlayerImg.getHeight());

        y = Game.halfScreenHeight - halfHeight;
        hide();
    }

    public void hide() {
        x = Game.screenWidth;
    }

    public void show() {
        x = Game.halfScreenWidth - width * 2;
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            AudioPlayer.playClick();
            Game.character = "falcon";
            Service.pauseMenuMusic();
            LoadingScreen.jobs = "newGame";
            Game.gameStatus = 41;
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.buttonPlayerImg, x, y, null);
    }
}
