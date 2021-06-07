package ru.warfare.darkannihilation;

public class ButtonSaturn extends Sprite {
    public ButtonSaturn(Game g) {
        super(g, ImageHub.buttonSaturnImg.getWidth(), ImageHub.buttonSaturnImg.getHeight());

        y = Game.halfScreenHeight - halfHeight;
        hide();
    }

    public void hide() {
        x = Game.screenWidth;
    }

    public void show() {
        x = Game.halfScreenWidth + game.buttonPlayer.width;
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            AudioHub.playClick();
            Game.character = "saturn";
            AudioHub.pauseMenuMusic();
            game.loadingScreen.newJob("newGame");
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.buttonSaturnImg, x, y, null);
    }
}
