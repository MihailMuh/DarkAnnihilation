package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

import static ru.warfare.darkannihilation.Constants.CHANGER_GUNS_CLICK_TIME;

public class ChangerGuns extends Sprite {
    private Bitmap image;
    private long lastClick = System.currentTimeMillis();

    public ChangerGuns(Game g) {
        super(g, ImageHub.gunToNone.getWidth(), ImageHub.gunToNone.getHeight());
        hide();
    }

    public void hide() {
        x = Game.screenWidth;
        lock = true;

        if (!game.shotgunKit.picked) {
            image = ImageHub.gunToNone;
        } else {
            if (game.player.gun.equals("shotgun")) {
                game.player.gun = "gun";
                image = ImageHub.gunToShotgun;
            } else {
                game.player.gun = "shotgun";
                image = ImageHub.shotgunToGun;
            }
        }
    }

    public void start() {
        x = 0;
        y = Game.screenHeight - height;
        lock = false;
    }

    public void setCoords(int X, int Y) {
        if ((Game.gameStatus == 0 | Game.gameStatus == 6) & checkCoords(X, Y)) {
            make();
        }
    }

    public boolean checkCoords(int X, int Y) {
        if (x < X) {
            if (X < right()) {
                if (y < Y) {
                    return Y < bottom();
                }
            }
        }
        return false;
    }

    public void make() {
        long now = System.currentTimeMillis();
        if (now - lastClick > CHANGER_GUNS_CLICK_TIME) {
            lastClick = now;
            if (!game.shotgunKit.picked) {
                image = ImageHub.gunToNone;
            } else {
                AudioHub.playReload();
                if (game.player.gun.equals("shotgun")) {
                    game.player.gun = "gun";
                    image = ImageHub.gunToShotgun;
                } else {
                    game.player.gun = "shotgun";
                    image = ImageHub.shotgunToGun;
                }
            }
        }
    }

    @Override
    public void render() {
        if (!lock) {
            Game.canvas.drawBitmap(image, x, y, null);
        }
    }
}
