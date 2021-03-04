package ru.warfare.darkannihilation;

public class FightBg extends Sprite {
    public FightBg(Game g) {
        super(g, ImageHub.fightBgImg.getWidth(), ImageHub.fightBgImg.getHeight());

        x = game.halfScreenWidth;
        y = game.halfScreenHeight;
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.fightBgImg, x - halfWidth, y - halfHeight, null);
    }
}
