package ru.warfare.darkannihilation;


public class Buckshot extends BaseBullet {
    public Buckshot(Game g, int X, int Y, int speed) {
        super(g, ImageHub.buckshotImg.getWidth(), ImageHub.buckshotImg.getHeight());

        x = X - halfWidth;
        y = Y;
        damage = 2;
        isPassive = true;

        speedX = speed;
        speedY = 8;
    }

    @Override
    public void intersection() {
        for (int i = numberMediumExplosionsTriple; i < numberSmallExplosionsTriple; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        game.bullets.remove(this);
        game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedX;

        if (y < -height | x < -width | x > screenWidth) {
            game.bullets.remove(this);
            game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.buckshotImg, x, y, null);
    }
}
