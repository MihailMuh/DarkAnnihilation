package ru.warfare.darkannihilation;

public class Button extends Sprite {
    public String function;
    private String text = " ";
    private int textX;
    private int textY;
    private static final int clickTime = 500;
    private long lastClick = System.currentTimeMillis();
    public boolean isPressed = false;

    public Button(Game game) {
        super(game, ImageHub.eX300, ImageHub.eX70);
    }

    public void setText(String text) {
        newFunc(text, x, y, function);
    }

    public void newFunc(String name, int X, int Y, String func) {
        function = func;

        y = Y;
        x = X;

        text = name;

        float len = Game.buttonsPaint.measureText(text);
        if (len > width - 40) {
            while (len > width - 40) {
                width += 5;
            }
            ImageHub.buttonImagePressed = ImageHub.resizeImage(ImageHub.buttonImagePressed, width, height);
            ImageHub.buttonImageNotPressed = ImageHub.resizeImage(ImageHub.buttonImageNotPressed, width, height);
            if (game.buttonMenu != null) {
                if (!func.equals(game.buttonMenu.function)) {
                    game.buttonMenu.updateFrontEnd();
                }
            }
            if (game.buttonQuit != null) {
                if (!func.equals(game.buttonQuit.function)) {
                    game.buttonQuit.updateFrontEnd();
                }
            }
            if (game.buttonRestart != null) {
                if (!func.equals(game.buttonRestart.function)) {
                    game.buttonRestart.updateFrontEnd();
                }
            }
            if (game.buttonStart != null) {
                if (!func.equals(game.buttonStart.function)) {
                    game.buttonStart.updateFrontEnd();
                }
            }
        } else {
            width = ImageHub.buttonImageNotPressed.getWidth();
        }
        halfWidth = width / 2;

        textX = (int) (x + (width - len) / 2);
        textY = (int) (y + (halfHeight + Game.buttonsPaint.getTextSize() / 4));

        isPressed = false;
    }

    public void updateFrontEnd() {
        width = ImageHub.buttonImageNotPressed.getWidth();
        halfWidth = width / 2;

        textX = (int) (x + (width - Game.buttonsPaint.measureText(text)) / 2);
        textY = (int) (y + (Game.buttonsPaint.getTextSize() / 4));
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

    public void sweep(int X, int Y) {
        isPressed = checkCoords(X, Y);
    }

    public void setCoords(int X, int Y) {
        if (checkCoords(X, Y)) {
            long now = System.currentTimeMillis();
            if (now - lastClick > clickTime) {
                lastClick = now;

                new Thread(() -> {
                    AudioHub.playClick();
                    isPressed = true;
                    try {
                        Thread.sleep(90);
                        isPressed = false;
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Service.print(e.toString());
                    }
                    switch (function) {
                        case "start":
                            game.buttonPlayer.show();
                            game.buttonSaturn.show();
                            game.buttonEmerald.show();
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
        if (isPressed) {
            Game.canvas.drawBitmap(ImageHub.buttonImagePressed, x, y, null);
        } else {
            Game.canvas.drawBitmap(ImageHub.buttonImageNotPressed, x, y, null);
        }
        Game.canvas.drawText(text, textX, textY, Game.buttonsPaint);
    }
}
