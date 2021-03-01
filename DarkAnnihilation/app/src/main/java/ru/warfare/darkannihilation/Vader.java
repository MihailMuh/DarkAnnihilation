package ru.warfare.darkannihilation;

public class Vader {
    public int x;
    public int y;
    public int speedX;
    public int speedY;
    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;
    private final Game game;
    public boolean lock = false;
    public int health = 2;

    public Vader(Game g) {
        game = g;

        width = ImageHub.vaderImage.getWidth();
        height = ImageHub.vaderImage.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;

        x = get_random(0, 1920);
        y = -150;
        speedX = get_random(-5, 5);
        speedY = get_random(3, 10);
    }

    public static int get_random(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void newStatus() {
        if (game.numberBosses != 0) {
            lock = true;
        }
        health = 2;
        x = get_random(0, 1920);
        y = -150;
        speedX = get_random(-5, 5);
        speedY = get_random(3, 10);
    }

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

    public void check_intersectionPlayer() {
        if (x + 15 < game.player.x + 20 & game.player.x + 20 < x + width - 15 &
                y + 15 < game.player.y + 25 & game.player.y + 25 < y + height - 15 |
                game.player.x + 20 < x & x < game.player.x + game.player.width - 20 &
                        game.player.y + 25 < y & y < game.player.y + game.player.height - 20) {
            AudioPlayer.playMetal();
            for (int i = 20; i < 40; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
            newStatus();
            game.player.health -= 5;
        }
    }

    public void update() {
        if (!lock) {
            check_intersectionPlayer();

            x += speedX;
            y += speedY;

            if (x < -width | x > game.screenWidth | y > game.screenHeight) {
                newStatus();
            }

            game.canvas.drawBitmap(ImageHub.vaderImage, x, y, null);
//            game.canvas.drawRect(x + 15, y + 15, x + width - 15, y + height - 15, paint);

        } else {
            if (game.numberBosses == 0 & game.gameStatus != 2 & game.gameStatus != 4) {
                lock = false;
            }
        }
    }
}
