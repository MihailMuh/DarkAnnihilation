package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

public class Button extends Sprite {
    private Bitmap img;
    private static final Paint paint = new Paint();
    private String function;
    private String text;
    private int textWidth;
    private int textHeight;
    private static final int clickTime = 500;
    private long lastClick;

    public Button(Game g, String t, int X, int Y, String func) {
        super(g, ImageHub.buttonImagePressed.getWidth(), ImageHub.buttonImagePressed.getHeight());

        paint.setColor(Color.WHITE);
        paint.setTextSize(35);

        newFunc(t, X, Y, func);

        lastClick = System.currentTimeMillis();
    }

    public void newFunc(String name, int X, int Y, String func) {
        function = func;

        x = X;
        y = Y;

        text = name;
        textWidth = (int) paint.measureText(text);
        textHeight = (int) (paint.getTextSize() / 4);

        img = ImageHub.buttonImageNotPressed;
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
                    AudioPlayer.playClick();
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
                            System.exit(0);
                            break;
                        case "pause":
                            game.player.dontmove = true;
                            Game.lastBoss += game.pauseTimer;
                            game.hardThread.workOnResume();
                            AudioPlayer.pausePauseMusic();
                            if (Game.bosses.size() == 0) {
                                AudioPlayer.resumeBackgroundMusic();
                            }
                            if (PauseButton.oldStatus == 2) {
                                AudioPlayer.readySnd.start();
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
                            game.settings.hideSettings();
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
        Game.canvas.drawText(text, x + (float) ((width - textWidth) / 2), y + (halfHeight + textHeight), paint);
    }
}
