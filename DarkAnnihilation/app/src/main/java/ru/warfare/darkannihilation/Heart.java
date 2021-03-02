package ru.warfare.darkannihilation;

public class Heart extends Sprite {
    public Heart(Game g, int X, int Y) {
        super(g, 0, 0);
        x = X;
        y = Y;
    }

    public void render(String type) {
        switch (type)
        {
            case "full":
                game.canvas.drawBitmap(ImageHub.imageHeartFull, x, y, null);
                break;
            case "half":
                game.canvas.drawBitmap(ImageHub.imageHeartHalf, x, y, null);
                break;
            case "non":
                game.canvas.drawBitmap(ImageHub.imageHeartNon, x, y, null);
                break;
        }
    }
}
