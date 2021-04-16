package ru.warfare.darkannihilation;

public class Rocket extends Sprite{
    public Rocket(Game g) {
        super(g, ImageHub.rocketImg.getWidth(), ImageHub.rocketImg.getHeight());
        speedY = 35;
        lock = true;
        damage = 100;
        isBullet = true;

        y = -height;
    }

    public void hide() {
        lock = true;
        y = -height;
    }

    public void start(int X) {
        x = X - halfWidth;
        lock = false;
    }

    @Override
    public void intersectionPlayer() {
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        AudioPlayer.playMegaBoom();
        hide();
    }


    @Override
    public void update() {
        y += speedY;

        if (y > screenHeight) {
            hide();
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.rocketImg, x, y, null);
    }
}
