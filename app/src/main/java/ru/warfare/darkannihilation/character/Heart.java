package ru.warfare.darkannihilation.character;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

public class Heart extends Sprite {
    private final boolean isArmor;

    public Heart(Game game, int X, int Y, boolean isArmor) {
        super(game);
        x = X;
        y = Y;
        this.isArmor = isArmor;
    }

    public void render(int type) {
        if (!isArmor) {
            switch (type) {
                case 2:
                    Game.canvas.drawBitmap(ImageHub.imageHeartFull, x, y, null);
                    break;
                case 1:
                    Game.canvas.drawBitmap(ImageHub.imageHeartHalf, x, y, null);
                    break;
                case 0:
                    Game.canvas.drawBitmap(ImageHub.imageHeartNon, x, y, null);
                    break;
            }
        }
        else {
            switch (type) {
                case 2:
                    Game.canvas.drawBitmap(ImageHub.imageBlueHeartFull, x, y, null);
                    break;
                case 1:
                    Game.canvas.drawBitmap(ImageHub.imageBlueHeartHalf, x, y, null);
                    break;
                case 0:
                    game.player.killArmorHeart(this);
                    break;
            }
        }
    }
}
