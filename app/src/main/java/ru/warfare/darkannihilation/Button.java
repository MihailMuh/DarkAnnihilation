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

        img = ImageHub.buttonImageNotPressed;

        function = func;

        x = X;
        y = Y;

        text = t;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        textWidth = (int) paint.measureText(text);
        textHeight = (int) (paint.getTextSize() / 4);

        lastClick = System.currentTimeMillis();
    }

    public void hide() {
        x = -width;
    }

    public void newFunc(String name, int X, int Y, String func) {
        function = func;

        x = X;
        y = Y;

        text = name;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        textWidth = (int) paint.measureText(text);
        textHeight = (int) (paint.getTextSize() / 4);

        img = ImageHub.buttonImageNotPressed;
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            long now = System.currentTimeMillis();
            if (now - lastClick > clickTime) {
                lastClick = now;
                AudioPlayer.playClick();
                img = ImageHub.buttonImagePressed;

                Thread thread = new Thread(() -> {
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
                            game.hardWorker.workOnResume();
                            AudioPlayer.pausePauseMusic();
                            AudioPlayer.resumeBackgroundMusic();
                            if (PauseButton.oldStatus == 2) {
                                AudioPlayer.readySnd.start();
                            }
                            if (PauseButton.oldStatus != 0) {
                                Game.gameStatus = PauseButton.oldStatus;
                                if (game.portal.touch) {
                                    AudioPlayer.timeMachineSnd.start();
                                }
                            } else {
                                Game.gameStatus = 9;
                            }
                            game.pauseTimer = 0;
                            break;
                        case "menu":
                            game.saveScore();
                            Game.gameStatus = 41;
                            LoadingScreen.jobs = "menu";
                            break;
                        case "top":
                            LoadingScreen.jobs = "topScore";
                            Game.gameStatus = 41;
                            ClientServer.postBestScore(Clerk.nickname, game.bestScore);
                            ClientServer.getStatistics();
                            break;
                        case "restart":
                            game.saveScore();
                            game.getMaxScore();
                            Game.level = 1;
                            LoadingScreen.jobs = "newGame";
                            Game.gameStatus = 41;
                            break;
                    }
                });
                thread.start();
            }
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(img, x, y, null);
        Game.canvas.drawText(text, x + (float) ((width - textWidth) / 2),y + (halfHeight + textHeight), paint);
    }
}
