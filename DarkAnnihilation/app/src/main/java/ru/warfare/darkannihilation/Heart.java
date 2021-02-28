package ru.warfare.darkannihilation;

public class Heart {

    public int x;
    public int y;
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
