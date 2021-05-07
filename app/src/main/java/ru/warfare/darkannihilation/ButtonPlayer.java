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
            AudioPlayer.playClick();
            Game.character = "falcon";
            if (AudioPlayer.menuMusic.isPlaying()) {
                AudioPlayer.menuMusic.pause();
            }
            LoadingScreen.jobs = "newGame";
            game.gameStatus = 41;
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.buttonPlayerImg, x, y, null);
    }
}
