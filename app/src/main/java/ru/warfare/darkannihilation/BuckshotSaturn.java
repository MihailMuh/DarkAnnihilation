package ru.warfare.darkannihilation;

public class BuckshotSaturn extends Sprite {
    private double deg;
    private final Vector vector = new Vector();
    private float X;
    private float Y;
    private boolean orbite = false;
    private float fly = 0;

    public BuckshotSaturn(Game game, int X, int Y) {
        super(game, ImageHub.bulletBuckshotSaturnImg.getWidth(), ImageHub.bulletBuckshotSaturnImg.getHeight());

        damage = 1;
        isPassive = true;
        isBullet = true;

        status = "saturn";

        this.X = X - halfWidth;
        this.Y = Y;

        left = X;
        top = Y;
        right = X + width;
        bottom = Y + height;

        vector.makeVector(X, Y, X, 0, 2);
    }

    @Override
    public int getDistance() {
        return getDistance((int) X - game.player.x, (int) Y - game.player.y);
    }

    @Override
    public Sprite getRect() {
        right += X - left;
        bottom += Y - top;
        left = (int) X;
        top = (int) Y;
        return this;
    }

    @Override
    public void intersection() {
        for (int i = numberMediumExplosionsDefault; i < numberSmallExplosionsDefault; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start((int) X + halfWidth, (int) Y + halfHeight);
                break;
            }
        }
        game.bullets.remove(this);
        game.allSprites.remove(this);
    }

    @Override
    public void update() {
        double[] speeds = vector.rotateVector(deg);

        X = (float) (X + speeds[0] + game.player.speedX);
        Y = (float) (Y + speeds[1] + game.player.speedY);

        if (!orbite) {
            if (getDistance((int) X - game.player.x, (int) Y - game.player.y) > 80) {
                vector.len += 0.035;
                deg += 0.03;
            } else {
                deg += 0.0025;
            }
            if (deg >= 4) {
                orbite = true;
                vector.len += 2;
            }
        } else {
            deg = deg + 0.035 - fly;
            fly += 0.000007;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.bulletBuckshotSaturnImg, X, Y, null);
    }
}