package ru.warfare.darkannihilation;

public class BulletGunner extends BulletBase {
    private double speedx;

    public BulletGunner(Game g, int X, int Y) {
        super(g, ImageHub.bulletGunnerImg.getWidth(), ImageHub.bulletGunnerImg.getHeight());

        speedY = randInt(6, 13);
        speedx = random.nextDouble() * 6.5;
        if (randInt(1, 2) == 1) {
            speedx = -speedx;
        }
        damage = 1;

        x = X;
        y = Y;
    }

    @Override
    public void intersection() {
        for (int i = numberMediumExplosionsDefault; i < numberSmallExplosionsDefault; i++) {
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
        x += speedx;

        if (y < -height | x < -width | x > screenWidth) {
            game.bullets.remove(this);
            game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.bulletGunnerImg, x, y, null);
    }
}
