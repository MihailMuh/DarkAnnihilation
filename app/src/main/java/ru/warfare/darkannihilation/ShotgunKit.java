package ru.warfare.darkannihilation;

public class ShotgunKit extends Sprite {
    public boolean picked = false;

    public ShotgunKit(Game g) {
        super(g, ImageHub.shotgunKitImg.getWidth(), ImageHub.shotgunKitImg.getHeight());
        speedY = 2;
        lock = true;

        x = randInt(0, screenWidth);
        y = -height;
    }

    public void hide() {
        x = randInt(0, screenWidth);
        y = -height;
        lock = true;
    }

    @Override
    public void check_intersectionPlayer() {
        if (x + 5 < game.player.x + 10 & game.player.x + 10 < x + width - 5 &
                y + 5 < game.player.y + 10 & game.player.y + 10 < y + height - 5 |
                game.player.x + 10 < x + 5 & x + 5 < game.player.x + game.player.width - 10 &
                        game.player.y + 10 < y + 5 & y + 5 < game.player.y + game.player.height - 10) {
            hide();
            picked = true;
            game.changerGuns.setCoords(game.changerGuns.x + 50, game.changerGuns.y + 50, 2);
        }
    }

    @Override
    public void update() {
        y += speedY;
        check_intersectionPlayer();
        if (y > screenHeight) {
            hide();
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.shotgunKitImg, x, y, null);
    }
}
