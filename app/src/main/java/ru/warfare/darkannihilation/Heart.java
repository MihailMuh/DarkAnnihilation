package ru.warfare.darkannihilation;

public class Heart extends Sprite {
    public Heart(int X) {
        super(0, 0);
        x = X;
        y = 10;
    }

    public void render(String type) {
        switch (type)
        {
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
}
