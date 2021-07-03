package ru.warfare.darkannihilation;

public class Heart extends Sprite {
    public final boolean isArmor;

    public Heart(Game game, int X, int Y, boolean isArmor) {
        super(game);
        x = X;
        y = Y;
        this.isArmor = isArmor;
    }

    public void render(String type) {
        if (!isArmor) {
            switch (type) {
                case "full":
                    Game.canvas.drawBitmap(ImageHub.imageHeartFull, x, y, null);
                    break;
                case "half":
                    Game.canvas.drawBitmap(ImageHub.imageHeartHalf, x, y, null);
                    break;
                case "non":
                    Game.canvas.drawBitmap(ImageHub.imageHeartNon, x, y, null);
                    break;
            }
        }
        else {
            switch (type) {
                case "full":
                    Game.canvas.drawBitmap(ImageHub.imageBlueHeartFull, x, y, null);
                    break;
                case "half":
                    Game.canvas.drawBitmap(ImageHub.imageBlueHeartHalf, x, y, null);
                    break;
                case "non":
                    game.player.killArmorHeart(this);
                    break;
            }
        }
    }
}
