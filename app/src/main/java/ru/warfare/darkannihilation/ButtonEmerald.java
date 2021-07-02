package ru.warfare.darkannihilation;

public class ButtonEmerald extends Sprite {
    public ButtonEmerald(Game game) {
        super(game, ImageHub.buttonEmeraldImg.getWidth(), ImageHub.buttonEmeraldImg.getHeight());

        y = Game.halfScreenHeight - halfHeight;
        hide();
    }

    public void hide() {
        x = Game.screenWidth;
    }

    public void show() {
        x = Game.halfScreenWidth + (game.buttonPlayer.width * 2);
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            AudioHub.playClick();
            Game.character = "emerald";
            game.loadingScreen.newJob("newGame");
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.buttonEmeraldImg,
                x, y, Game.nicePaint);
    }
}