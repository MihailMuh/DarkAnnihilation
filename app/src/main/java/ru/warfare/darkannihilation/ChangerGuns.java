package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class ChangerGuns extends Sprite {
    private Bitmap image;
    private static final int clickTime = 700;
    private long lastClick;

    public ChangerGuns(Game g) {
        super(g, ImageHub.gunToNone.getWidth(), ImageHub.gunToNone.getHeight());
        hide();
        lastClick = System.currentTimeMillis();
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
        return (x < X & X < x + width & y < Y & Y < y + width);
    }

    public void make() {
        long now = System.currentTimeMillis();
        if (now - lastClick > clickTime) {
            lastClick = now;
            if (!game.shotgunKit.picked) {
                image = ImageHub.gunToNone;
            } else {
                AudioPlayer.playReload();
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
