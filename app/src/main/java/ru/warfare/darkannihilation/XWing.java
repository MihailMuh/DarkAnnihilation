package ru.warfare.darkannihilation;

public class XWing extends Sprite {
    private static final int shootTripleTime = 180;
    private long lastShoot;
    private int distance = 350;
    private static final Vector vector = new Vector();

    public XWing(Game game) {
        super(game, ImageHub.XWingImg.getWidth(), ImageHub.XWingImg.getHeight());
        health = 5;
        damage = 10;

        x = randInt(0, Game.screenWidth);
        y = -height;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 8);

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);

        if (Game.character.equals("saturn")) {
            distance = 600;
        }

        lastShoot = System.currentTimeMillis();
    }

    private void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootTripleTime) {
            lastShoot = now;

            int myX = centerX();
            int myY = centerY();
            int plX = game.player.centerX();
            int plY = game.player.centerY();
            if (getDistance(myX - plX, myY - plY) < distance) {
                vector.makeVector(myX, myY, plX, plY, 9);
                AudioPlayer.playShoot();
                Game.allSprites.add(new BulletEnemy(myX, myY, vector.getAngle(), vector.getSpeedX(), vector.getSpeedY()));
            }
        }
    }

    public void newStatus() {
        if (Game.bosses.size() != 0) {
            lock = true;
        }
        health = 5;
        x = randInt(0, Game.screenWidth);
        y = -height - 50;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 8);

        if (buff) {
            up();
        }
    }

    private void up() {
        speedX *= 2;
        speedY *= 2;
    }

    @Override
    public void buff() {
        buff = true;
        up();
    }

    @Override
    public void stopBuff() {
        speedX /= 2;
        speedY /= 2;
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 15, y + 15);
    }

    @Override
    public void intersection() {
        createLargeTripleExplosion();
        AudioPlayer.playBoom();
        Game.score += 10;
        newStatus();
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMetal();
        createSmallExplosion();
        newStatus();
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (getRect().intersect(bullet.getRect())) {
            health -= bullet.damage;
            bullet.intersection();
            if (health <= 0) {
                intersection();
            }
        }
    }

    @Override
    public void empireStart() {
        lock = false;
    }

    @Override
    public void update() {
        if (x > 0 & x < screenWidthWidth & y > 0 & y < screenHeightHeight) {
            shoot();
        }

        x += speedX;
        y += speedY;

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight) {
            newStatus();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.XWingImg, x, y, Game.alphaPaint);
    }
}
