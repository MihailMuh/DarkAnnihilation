package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Paint;

import static ru.warfare.darkannihilation.Constants.CHANGER_GUNS_CLICK_TIME;

public class ChangerGuns extends Sprite {
    private Bitmap image;
    private static final Paint alphaPaint = new Paint();
    private boolean isInvisible;
    private long lastClick = System.currentTimeMillis();

    public ChangerGuns(Game g) {
        super(g, ImageHub.gunToNone.getWidth(), ImageHub.gunToNone.getHeight());
        y = Game.screenHeight - height;

        alphaPaint.setFilterBitmap(true);
        alphaPaint.setDither(true);
        alphaPaint.setAntiAlias(true);

        hide();
    }

    public void hide() {
        x = Game.screenWidth;
        lock = true;
        isInvisible = false;

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
        lock = false;
    }

    @Override
    public void intersectionPlayer() {
        if (!isInvisible) {
            isInvisible = true;
            alphaPaint.setAlpha(80);
        }
    }

    public void work() {
        if (isInvisible) {
            isInvisible = false;
            alphaPaint.setAlpha(255);
        }
    }

    public void setCoords(int X, int Y) {
        if ((Game.gameStatus == 0 | Game.gameStatus == 6) & checkCoords(X, Y)) {
            make();
        }
    }

    public boolean checkCoords(int X, int Y) {
        if (!isInvisible) {
            if (x < X) {
                if (X < right()) {
                    if (y < Y) {
                        return Y < bottom();
                    }
                }
            }
        }
        return false;
    }

    public void make() {
        if (System.currentTimeMillis() - lastClick > CHANGER_GUNS_CLICK_TIME) {
            lastClick = System.currentTimeMillis();
            if (game.shotgunKit.picked) {
                AudioHub.playReload();
                if (game.player.gun.equals("shotgun")) {
                    game.player.gun = "gun";
                    image = ImageHub.gunToShotgun;
                } else {
                    game.player.gun = "shotgun";
                    image = ImageHub.shotgunToGun;
                }
                width = image.getWidth();
                height = image.getHeight();
            } else {
                image = ImageHub.gunToNone;
            }
        }
    }

    @Override
    public void render() {
        if (!lock) {
            Game.canvas.drawBitmap(image, x, y, alphaPaint);
        }
    }
}
