package ru.warfare.darkannihilation;

public class Emerald extends BaseCharacter {
    public Emerald(Game g) {
        super(g, ImageHub.emeraldImg.getWidth(), ImageHub.emeraldImg.getHeight(), 80);
        recreateRect(x + 25, y + 25, right() - 25, bottom() - 25);

        shootTime = 1_650;
        shotgunTime = 1_400;

        int X = 280;
        int Y = 10;
        hearts = new Heart[8];
        for (int j = 0; j <= 4; j += 4) {
            for (int i = 0; i < 4; i++) {
                hearts[i + j] = new Heart(X, Y);
                X -= 90;
            }
            X = 280;
            Y += 10 + ImageHub.imageHeartHalf.getHeight();
        }

        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void shoot() {
        now = System.currentTimeMillis();
        if (gun.equals("shotgun")) {
            if (now - lastShoot > shotgunTime) {
                lastShoot = now;
                AudioHub.playThunderStorm();
                BulletThunder bulletThunder = new BulletThunder(centerX(), y);
                Game.bullets.add(bulletThunder);
                Game.allSprites.add(bulletThunder);
            }
        } else {
            if (now - lastShoot > shootTime) {
                if (HardThread.job == 0) {
                    HardThread.job = 9;
                    lastShoot = now;
                }
            }
        }
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 25, y + 25);
    }

    @Override
    public void checkIntersections(Sprite sprite) {
        if (getRect().intersect(sprite.getRect())) {
            damage(sprite.damage);
            sprite.intersectionPlayer();
        }
    }

    @Override
    public void update() {
        if (!lock) {
            shoot();
        }

        x += speedX;
        y += speedY;

        speedX = (endX - x) / 10;
        speedY = (endY - y) / 10;
    }

    @Override
    public void render () {
        renderHearts();
        Game.canvas.drawBitmap(ImageHub.emeraldImg, x, y, null);
    }

    @Override
    public void renderHearts() {
        switch (health) {
            case 80:
                hearts[0].render("full");
                hearts[1].render("full");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 75:
                hearts[0].render("half");
                hearts[1].render("full");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 70:
                hearts[0].render("non");
                hearts[1].render("full");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 65:
                hearts[0].render("non");
                hearts[1].render("half");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 60:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("full");
                hearts[3].render("full");
                hearts[4].render("full");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 55:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("half");
                hearts[3].render("full");
                hearts[4].render("full");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 50:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("full");
                hearts[4].render("full");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 45:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("half");
                hearts[4].render("full");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 40:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("full");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 35:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("half");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 30:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("non");
                hearts[5].render("full");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 25:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("non");
                hearts[5].render("half");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 20:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("non");
                hearts[5].render("non");
                hearts[6].render("full");
                hearts[7].render("full");
                break;
            case 15:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("non");
                hearts[5].render("non");
                hearts[6].render("half");
                hearts[7].render("full");
                break;
            case 10:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("non");
                hearts[5].render("non");
                hearts[6].render("non");
                hearts[7].render("full");
                break;
            case 5:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("non");
                hearts[5].render("non");
                hearts[6].render("non");
                hearts[7].render("half");
                break;
            default:
                hearts[0].render("non");
                hearts[1].render("non");
                hearts[2].render("non");
                hearts[3].render("non");
                hearts[4].render("non");
                hearts[5].render("non");
                hearts[6].render("non");
                hearts[7].render("non");
                break;
        }
    }
}
