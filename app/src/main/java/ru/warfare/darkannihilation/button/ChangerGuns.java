package ru.warfare.darkannihilation.button;

import static ru.warfare.darkannihilation.constant.Constants.CHANGER_GUNS_CLICK_TIME;
import static ru.warfare.darkannihilation.constant.Modes.GAME;
import static ru.warfare.darkannihilation.constant.NamesConst.GUN;
import static ru.warfare.darkannihilation.constant.NamesConst.SHOTGUN;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import android.graphics.Paint;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseButton;
import ru.warfare.darkannihilation.systemd.Game;

public class ChangerGuns extends BaseButton {
    private final Paint alphaPaint = new Paint();
    private boolean isInvisible;
    private long lastClick = System.currentTimeMillis();

    public ChangerGuns(Game g) {
        super(g, ImageHub.gunsImage[0]);
        y = SCREEN_HEIGHT - height;
        x = SCREEN_WIDTH;

        alphaPaint.setFilterBitmap(true);
        alphaPaint.setDither(true);
        alphaPaint.setAntiAlias(true);

        if (game.shotgunKit.picked) {
            if (game.player.gun == SHOTGUN) {
                image = ImageHub.gunsImage[1];
            } else {
                image = ImageHub.gunsImage[2];
            }
        }
    }

    public ChangerGuns() {
        super(null, ImageHub.pauseButtonImg);

        x = SCREEN_WIDTH;
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
        if (Game.gameStatus == GAME && checkCoords(X, Y)) {
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
                    image = ImageHub.gunsImage[2];
                } else {
                    game.player.gun = SHOTGUN;
                    image = ImageHub.gunsImage[1];
                }
            } else {
                image = ImageHub.gunsImage[0];
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, alphaPaint);
    }
}
