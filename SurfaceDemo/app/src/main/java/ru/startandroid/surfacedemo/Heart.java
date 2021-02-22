package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

public class Heart {
    public Bitmap imageFull;
    public Bitmap imageHalf;
    public Bitmap imageNon;

    public int x;
    public int y;
    private static final boolean isFilter = true;
    public static final Paint color = new Paint();
    private final Game game;

    public Heart(Game g, int X, int Y) {
        game = g;
        imageFull = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.full_heart);
        imageFull = Bitmap.createScaledBitmap(imageFull, (int) (70 * game.resizeK), (int) (60 * game.resizeK), isFilter);

        imageHalf = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.half_heart);
        imageHalf = Bitmap.createScaledBitmap(imageHalf, (int) (70 * game.resizeK), (int) (60 * game.resizeK), isFilter);

        imageNon = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.non_heart);
        imageNon = Bitmap.createScaledBitmap(imageNon, (int) (70 * game.resizeK), (int) (60 * game.resizeK), isFilter);

        x = X;
        y = Y;
    }

    public void update(String type) {
        switch (type)
        {
            case "full":
                game.canvas.drawBitmap(imageFull, x, y, color);
                break;
            case "half":
                game.canvas.drawBitmap(imageHalf, x, y, color);
                break;
            case "non":
                game.canvas.drawBitmap(imageNon, x, y, color);
                break;
        }
    }
}
