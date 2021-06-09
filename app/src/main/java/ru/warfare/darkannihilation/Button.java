package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class Button extends Sprite {
    private Bitmap img;
    private static final Paint paint = new Paint();
    private String function;
    private String text;
    private int textX;
    private int textY;
    private static final int clickTime = 500;
    private long lastClick;

    public Button(Game g, String t, int X, int Y, String func) {
        super(g, ImageHub.buttonImagePressed.getWidth(), ImageHub.buttonImagePressed.getHeight());

        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        paint.setAntiAlias(true);

        newFunc(t, X, Y, func);

        lastClick = System.currentTimeMillis();
    }

    public void newFunc(String name, int X, int Y, String func) {
        function = func;

        x = X;
        y = Y;

        text = name;

        float len = paint.measureText(text);
        while (len > width - 40) {
            width += 2;
        }

        ImageHub.buttonImagePressed = ImageHub.resizeImage(ImageHub.buttonImagePressed, width, height);
        ImageHub.buttonImageNotPressed = ImageHub.resizeImage(ImageHub.buttonImageNotPressed, width, height);;
        img = ImageHub.buttonImageNotPressed;

        halfWidth = width / 2;

        textX = (int) (x + (width - paint.measureText(text)) / 2);
        textY = (int) (y + (halfHeight + paint.getTextSize() / 4));
    }

    public boolean checkCoords(int X, int Y) {
        if (x < X) {
            if (X < right()) {
                if (y < Y) {
                    return Y < bottom();
                }
            }
        }
        return false;
    }

    public void setCoords(int X, int Y) {
        if (checkCoords(X, Y)) {
            long now = System.currentTimeMillis();
            if (now - lastClick > clickTime) {
                lastClick = now;

                new Thread(() -> {
                    AudioHub.playClick();
                    img = ImageHub.buttonImagePressed;
                    try {
                        Thread.sleep(90);
                        img = ImageHub.buttonImageNotPressed;
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Service.print(e.toString());
                    }
                    switch (function) {
                        case "start":
                            game.buttonPlayer.show();
                            game.buttonSaturn.show();
                            break;
                        case "quit":
                            game.onPause();
                            game.saveScore();
                            game.settings.saveSettings();
                            AudioHub.releaseAP();
                            System.exit(0);
                            break;
                        case "pause":
                            Game.lastBoss += game.pauseTimer;
                            game.hardThread.workOnResume();
                            AudioHub.pausePauseMusic();
                            if (Game.bosses.size() == 0) {
                                AudioHub.resumeBackgroundMusic();
                            }
                            if (PauseButton.oldStatus == 2) {
                                AudioHub.readySnd.start();
                            }
                            if (PauseButton.oldStatus != 0) {
                                Game.gameStatus = PauseButton.oldStatus;
                            } else {
                                Game.gameStatus = 9;
                            }
                            game.pauseTimer = 0;
                            break;
                        case "menu":
                            game.saveScore();
                            game.loadingScreen.newJob(function);
                            break;
                        case "top":
                            ClientServer.postAndGetBestScore(Clerk.nickname, game.bestScore);
                            game.loadingScreen.newJob(function);
                            break;
                        case "restart":
                            game.saveScore();
                            game.getMaxScore();
                            Game.level = 1;
                            game.loadingScreen.newJob("newGame");
                            break;
                        case "fromSetting":
                            game.settings.confirmSettings();
                            game.loadingScreen.newJob("menu");
                            break;
                        case "settings":
                            game.loadingScreen.newJob(function);
                            break;
                    }
                }).start();
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(img, x, y, null);
        Game.canvas.drawText(text, textX, textY, paint);
    }
}
