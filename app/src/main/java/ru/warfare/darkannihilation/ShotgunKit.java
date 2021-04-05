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
    public void update() {
        y += speedY;
<<<<<<< HEAD
=======
        game.player.check_intersectionShotgunKit(this);
>>>>>>> 3dbe4906d883a868d20d3cf9308dbfca612e674e
        if (y > screenHeight) {
            hide();
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.shotgunKitImg, x, y, null);
    }
}
