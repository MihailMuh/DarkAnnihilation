package ru.warfare.darkannihilation;

import android.graphics.Paint;

public class Heart {

    public int x;
    public int y;
    public static final Paint color = new Paint();
    private final Game game;

    public Heart(Game g, int X, int Y) {
        game = g;

        x = X;
        y = Y;
    }

    public void update(String type) {
        switch (type)
        {
            case "full":
                game.canvas.drawBitmap(ImageHub.imageHeartFull, x, y, color);
                break;
            case "half":
                game.canvas.drawBitmap(ImageHub.imageHeartHalf, x, y, color);
                break;
            case "non":
                game.canvas.drawBitmap(ImageHub.imageHeartNon, x, y, color);
                break;
        }
    }
}
