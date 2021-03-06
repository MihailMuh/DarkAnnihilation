package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class Vader extends Sprite {
    public Bitmap img;
    public Vader(Game g) {
        super(g, ImageHub.vaderImage[0].getWidth(), ImageHub.vaderImage[0].getHeight());
        health = 2;

        img = ImageHub.vaderImage[randInt(0, 2)];

        x = randInt(0, 1920);
        y = -150;
        speedX = randInt(-5, 5);
        speedY = randInt(3, 10);
    }

    public void newStatus() {
        if (game.numberBosses != 0) {
            lock = true;
        }
        health = 2;
        x = randInt(0, 1920);
        y = -150;
        speedX = randInt(-5, 5);
        speedY = randInt(3, 10);
    }

    @Override
    public void check_intersectionBullet(Bullet bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            health -= bullet.damage;
            game.bullets.remove(bullet);
            game.numberBullets -= 1;
            if (health <= 0) {
                for (int i = 0; i < 20; i++) {
                    if (game.explosions[i].lock) {
                        game.explosions[i].start(x + halfWidth, y + halfHeight);
                        break;
                    }
                }
                AudioPlayer.playBoom();
                game.score += 1;
                newStatus();
            }
        }
    }

    public void check_intersectionBullet(Buckshot bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            for (int i = 0; i < 20; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
            AudioPlayer.playBoom();
            game.score += 1;
            newStatus();
        }
    }

    @Override
    public void check_intersectionPlayer() {
        if (x + 15 < game.player.x + 20 & game.player.x + 20 < x + width - 15 &
                y + 15 < game.player.y + 25 & game.player.y + 25 < y + height - 15 |
                game.player.x + 20 < x + 15 & x + 15 < game.player.x + game.player.width - 20 &
                        game.player.y + 25 < y + 15 & y + 15 < game.player.y + game.player.height - 20) {
            AudioPlayer.playMetal();
            game.player.damage(5);
            for (int i = 20; i < 40; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
            newStatus();
        }
    }

    @Override
    public void update() {
        if (!lock) {
            check_intersectionPlayer();

            x += speedX;
            y += speedY;

            if (x < -width | x > game.screenWidth | y > game.screenHeight) {
                newStatus();
            }

        } else {
            if (game.numberBosses == 0 & game.gameStatus != 2 & game.gameStatus != 4) {
                lock = false;
            }
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(img, x, y, null);
    }
}
