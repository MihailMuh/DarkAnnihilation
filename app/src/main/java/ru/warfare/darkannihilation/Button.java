package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Button extends Sprite {
    private Bitmap img;
    public int mouseX;
    public int mouseY;
    public static final Paint paint = new Paint();
    public String function;
    private String text;
    private int textWidth;
    private int textHeight;

    public Button(Game g, String t, int X, int Y, String func) {
        super(g, ImageHub.buttonImagePressed.getWidth(), ImageHub.buttonImagePressed.getHeight());

        img = ImageHub.buttonImageNotPressed;

        function = func;

        x = X;
        y = Y;

        text = t;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        textWidth = (int) paint.measureText(text);
        textHeight = (int) (paint.getTextSize() / 4);
    }

    public void newFunc(String t, int X, int Y, String func) {
        function = func;

        x = X;
        y = Y;

        text = t;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        textWidth = (int) paint.measureText(text);
        textHeight = (int) (paint.getTextSize() / 4);
    }

    @Override
    public void update() {
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + height) {
            AudioPlayer.buttonSnd.start();
            img = ImageHub.buttonImagePressed;
            if (function.equals("start")) {
                game.generateNewGame();
            } else {
                if (function.equals("quit")) {
                    System.exit(0);
                } else {
                    if (function.equals("pause")) {
                        Game.lastBoss += game.pauseTimer;
                        game.hardWorker.workOnResume();
                        AudioPlayer.pauseMusic.pause();
                        if (game.bosses.size() == 0) {
                            AudioPlayer.pirateMusic.start();
                        }
                        if (game.pauseButton.oldStatus == 2) {
                            AudioPlayer.readySnd.start();
                        }
                        game.gameStatus = game.pauseButton.oldStatus;
                        game.pauseTimer = 0;
                    } else {
                        if (function.equals("menu")) {
                            game.generateMenu();
                        } else {
                            if (function.equals("top")) {
                                game.score = 0;
                                game.generateTopScore();
                            }
                        }
                    }
                }
            }
        } else {
            img = ImageHub.buttonImageNotPressed;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(img, x, y, null);
        game.canvas.drawText(text, x + (float) ((width - textWidth) / 2),y + (halfHeight + textHeight), paint);
    }
}