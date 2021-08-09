package ru.warfare.darkannihilation.button;

import android.graphics.Paint;

import ru.warfare.darkannihilation.base.BaseButton;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.CHANGER_GUNS_CLICK_TIME;
import static ru.warfare.darkannihilation.constant.NamesConst.GUN;
import static ru.warfare.darkannihilation.constant.NamesConst.SHOTGUN;

public class ChangerGuns extends BaseButton {
    private static final Paint alphaPaint = new Paint();
    private boolean isInvisible;
    private long lastClick = System.currentTimeMillis();

    public ChangerGuns(Game g) {
        super(g, ImageHub.gunToNone);
        y = Game.screenHeight - height;

        alphaPaint.setFilterBitmap(true);
        alphaPaint.setDither(true);

        hide();
    }

    @Override
    public void hide() {
        x = Game.screenWidth;
        isInvisible = false;
        alphaPaint.setAlpha(255);

        if (!game.shotgunKit.picked) {
            image = ImageHub.gunToNone;
        } else {
            if (game.player.gun == SHOTGUN) {
                image = ImageHub.shotgunToGun;
            } else {
                image = ImageHub.gunToShotgun;
            }
        }

        width = image.getWidth();
        height = image.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;
    }

    @Override
    public void start() {
        x = 0;
        isInvisible = false;
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

    @Override
    public void setCoords(int X, int Y) {
        if ((Game.gameStatus == 0 | Game.gameStatus == 6) & checkCoords(X, Y)) {
            make();
        }
    }

    @Override
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
                if (game.player.gun == SHOTGUN) {
                    game.player.gun = GUN;
                    image = ImageHub.gunToShotgun;
                } else {
                    game.player.gun = SHOTGUN;
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
        Game.canvas.drawBitmap(image, x, y, alphaPaint);
    }
}
