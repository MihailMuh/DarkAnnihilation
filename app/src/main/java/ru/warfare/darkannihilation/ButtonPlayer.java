package ru.warfare.darkannihilation;

public class ButtonPlayer extends Sprite {
    public ButtonPlayer(Game game) {
        super(game, ImageHub.buttonPlayerImg.getWidth(), ImageHub.buttonPlayerImg.getHeight());

        y = Game.halfScreenHeight - halfHeight;
        hide();
    }

    public void hide() {
        x = Game.screenWidth;
    }

    public void show() {
        x = Game.halfScreenWidth - halfWidth;
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            AudioHub.playClick();
            Game.character = "falcon";
            game.loadingScreen.newJob("newGame");
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.buttonPlayerImg,
                x, y, Game.nicePaint);
    }
}
