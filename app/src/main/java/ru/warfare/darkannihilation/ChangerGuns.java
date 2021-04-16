package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class ChangerGuns extends Sprite {
    public Bitmap image;
    public int mouseX;
    public int mouseY;
    private static final int clickTime = 700;
    private long lastShoot;
    private long now;

    public ChangerGuns(Game g) {
        super(g, ImageHub.gunToNone.getWidth(), ImageHub.gunToNone.getHeight());
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

        hide();
        lastShoot = System.currentTimeMillis();
    }

    public void hide() {
        x = screenWidth * 2;
        lock = true;
    }

    public void unHide() {
        x = 0;
        y = screenHeight - height;
        lock = false;
    }

    public void setCoords(int X, int Y) {
        if (game.gameStatus == 0 | game.gameStatus == 6) {
            mouseX = X;
            mouseY = Y;
            if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + width) {
                now = System.currentTimeMillis();
                if (now - lastShoot > clickTime) {
                    lastShoot = now;
                    mouseX = 0;
                    mouseY = 0;
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
        }
    }

    public void setCoords(int X, int Y, int r) {
        if (game.gameStatus == 0 | game.gameStatus == 6) {
            game.player.dontmove = true;
            mouseX = X;
            mouseY = Y;
            if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + width) {
                now = System.currentTimeMillis();
                if (now - lastShoot > clickTime) {
                    lastShoot = now;
                    mouseX = 0;
                    mouseY = 0;
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
            } else {
                game.player.dontmove = false;
            }
        }
    }

    public void changeGun() {
        now = System.currentTimeMillis();
        if (now - lastShoot > clickTime) {
            lastShoot = now;
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
            game.canvas.drawBitmap(image, x, y, null);
        }
    }
}
