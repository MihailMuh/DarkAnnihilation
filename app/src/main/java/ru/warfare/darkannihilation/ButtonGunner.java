package ru.warfare.darkannihilation;

public class ButtonGunner extends Sprite {
    public ButtonGunner(Game g) {
        super(g, ImageHub.buttonGunnerImg.getWidth(), ImageHub.buttonGunnerImg.getHeight());

        y = halfScreenHeight - halfHeight;
        hide();
    }

    public void hide() {
        x = screenWidth;
    }

    public void show() {
        x = halfScreenWidth + game.buttonPlayer.width;
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            AudioPlayer.playClick();
            Game.character = "gunner";
            if (AudioPlayer.menuMusic.isPlaying()) {
                AudioPlayer.menuMusic.pause();
            }
            LoadingScreen.jobs = "newGame";
            game.gameStatus = 41;
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.buttonGunnerImg, x, y, null);
    }
}
