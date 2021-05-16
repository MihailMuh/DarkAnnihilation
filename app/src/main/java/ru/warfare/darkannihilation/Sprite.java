package ru.warfare.darkannihilation;

public class Sprite {
    public Game game;

    public int x = 0;
    public int y = 0;
    public int speedX = 0;
    public int speedY = 0;

    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;

    public final int screenHeightHeight;
    public final int screenWidthWidth;

    public int health = 0;
    public int damage = 0;

    public boolean lock = false;
    public boolean isPassive = false;
    public boolean isBullet = false;

    public String status = "";
    public boolean buff = false;

    public int left;
    public int top;
    public int right;
    public int bottom;

    public Sprite(Game g, int w, int h) {
        game = g;

        width = w;
        height = h;
        halfWidth = width / 2;
        halfHeight = height / 2;

        left = x;
        top = y;
        right = x + width;
        bottom = y + height;

        screenHeightHeight = Game.screenHeight - height;
        screenWidthWidth = Game.screenWidth - width;
    }

    public Sprite(int w, int h) {
        width = w;
        height = h;
        halfWidth = width / 2;
        halfHeight = height / 2;

        left = x;
        top = y;
        right = x + width;
        bottom = y + height;

        screenHeightHeight = Game.screenHeight - height;
        screenWidthWidth = Game.screenWidth - width;
    }

    public void check_intersectionBullet(Sprite bullet) {}
    public void update() {}
    public void render() {}
    public void intersection() {}
    public void intersectionPlayer() {}
    public void empireStart() {}
    public void buff() {}
    public void stopBuff() {}

    public void createLargeExplosion() {
        for (int i = Game.numberSmallExplosionsTriple; i < Game.numberMediumExplosionsDefault; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
    }

    public void createSmallExplosion() {
        for (int i = Game.numberMediumExplosionsDefault; i < Game.numberSmallExplosionsDefault; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
    }

    public void createLargeTripleExplosion() {
        for (int i = 0; i < Game.numberMediumExplosionsTriple; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
    }

    public void createSmallTripleExplosion() {
        for (int i = Game.numberMediumExplosionsTriple; i < Game.numberSmallExplosionsTriple; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
    }

    public void createSkullExplosion() {
        for (int i = Game.numberSmallExplosionsDefault; i < Game.numberExplosionsALL; i++) {
            if (Game.allExplosions[i].lock) {
                Game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
    }

    public int centerX() {
        return x + halfWidth;
    }

    public int centerY() {
        return y + halfHeight;
    }

    public int right() {
        return x + width;
    }

    public int bottom() {
        return y + height;
    }

    public void recreateRect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean intersect(Sprite sprite) {
        return left < sprite.right && sprite.left < right && top < sprite.bottom && sprite.top < bottom;
    }

    public Sprite goTO(int newLeft, int newTop) {
        right += newLeft - left;
        bottom += newTop - top;
        left = newLeft;
        top = newTop;
        return this;
    }

    public Sprite goTO() {
        right += x - left;
        bottom += y - top;
        left = x;
        top = y;
        return this;
    }

    public Sprite getRect() {
        return goTO();
    }

    public static int randInt(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public static int getDistance(int a, int b) {
        return (int) Math.sqrt((a * a) + (b * b));
    }

    public int getDistance() {return 0;}
}
