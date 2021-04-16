package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Button extends Sprite {
    private Bitmap img;
    public static final Paint paint = new Paint();
    public String function;
    private String text;
    private int textWidth;
    private int textHeight;
    public int mouseX;
    public int mouseY;

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

    public void setCoords(int X, int Y) {
        mouseX = X;
        mouseY = Y;
        if (x < mouseX & mouseX < x + width & y < mouseY & mouseY < y + height) {
            AudioPlayer.buttonSnd.start();
            img = ImageHub.buttonImagePressed;

            mouseX = 0;
            mouseY = 0;

            switch (function)
            {
                case "start":
                    game.buttonPlayer.show();
                    game.buttonGunner.show();
                    img = ImageHub.buttonImageNotPressed;
                    break;
                case "quit":
                    System.exit(0);
                    break;
                case "pause":
                    game.player.dontmove = true;
                    Game.lastBoss += game.pauseTimer;
                    game.hardWorker.workOnResume();
                    AudioPlayer.pauseMusic.pause();
                    if (game.bosses.size() == 0) {
                        AudioPlayer.pirateMusic.start();
                    }
                    if (game.pauseButton.oldStatus == 2) {
                        AudioPlayer.readySnd.start();
                    }
                    if (game.pauseButton.oldStatus != 0) {
                        game.gameStatus = game.pauseButton.oldStatus;
                        game.pauseButton.show();
                    } else {
                        game.gameStatus = 9;
                    }
                    game.pauseTimer = 0;

                    game.buttonMenu.x = screenWidth;
                    x = screenWidth;
                    img = ImageHub.buttonImageNotPressed;
                    game.buttonQuit.x = screenWidth;
                    break;
                case "menu":
                    game.generateMenu();
                    break;
                case "top":
                    game.buttonPlayer.hide();
                    game.buttonGunner.hide();
                    game.score = 0;
                    game.generateTopScore();
                    break;
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
