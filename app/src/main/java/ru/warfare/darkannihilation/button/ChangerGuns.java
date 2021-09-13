package ru.warfare.darkannihilation.button;

import static ru.warfare.darkannihilation.constant.Constants.CHANGER_GUNS_CLICK_TIME;
import static ru.warfare.darkannihilation.constant.NamesConst.GUN;
import static ru.warfare.darkannihilation.constant.NamesConst.SHOTGUN;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import ru.warfare.darkannihilation.CustomPaint;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.BaseButton;
import ru.warfare.darkannihilation.systemd.Game;

public class ChangerGuns extends BaseButton {
    private final CustomPaint alphaPaint = new CustomPaint();
    private boolean isInvisible;
    private long lastClick = System.currentTimeMillis();
    private byte gun = GUN;

    public ChangerGuns(Game g) {
        super(g, ImageHub.gunsImage[0]);
        y = SCREEN_HEIGHT - height;
    }

    @Override
    public void start() {
        work();
        if (game.shotgunKit.picked) {
            if (gun == SHOTGUN) {
                image = ImageHub.gunsImage[1];
                game.player.setShotgun();
            } else {
                image = ImageHub.gunsImage[2];
                game.player.setGun();
            }
        } else {
            game.player.setGun();
        }
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
        if (check(X, Y)) {
            make();
        }
    }

    @Override
    public boolean checkCoords(int X, int Y) {
        if (!isInvisible) {
            return check(X, Y);
        }
        return false;
    }

    private boolean check(int X, int Y) {
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
        if (System.currentTimeMillis() - lastClick > CHANGER_GUNS_CLICK_TIME) {
            lastClick = System.currentTimeMillis();
            if (game.shotgunKit.picked) {
                AudioHub.playReload();
                if (gun == SHOTGUN) {
                    gun = GUN;
                    game.player.setGun();
                    image = ImageHub.gunsImage[2];
                } else {
                    gun = SHOTGUN;
                    game.player.setShotgun();
                    image = ImageHub.gunsImage[1];
                }
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, alphaPaint);
    }
}
