package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.Clerk;
import ru.warfare.darkannihilation.ClientServer;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.Time;
import ru.warfare.darkannihilation.base.BaseButton;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.Service;

import static ru.warfare.darkannihilation.constant.Constants.BUTTON_CLICK_TIME;
import static ru.warfare.darkannihilation.constant.Modes.AFTER_PAUSE;
import static ru.warfare.darkannihilation.constant.Modes.GAME;

public class Button extends BaseButton {
    public String function;
    private String text = " ";
    private int textX;
    private int textY;
    private long lastClick = System.currentTimeMillis();
    public boolean isPressed = false;

    public Button(Game game) {
        super(game, ImageHub.buttonImagePressed);
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

    public void sweep(int X, int Y) {
        isPressed = checkCoords(X, Y);
    }

    @Override
    public void setCoords(int X, int Y) {
        if (checkCoords(X, Y)) {
            long now = System.currentTimeMillis();
            if (now - lastClick > BUTTON_CLICK_TIME) {
                lastClick = now;
                HardThread.doInBackGround(() -> {
                    AudioHub.playClick();
                    isPressed = true;
                    Time.sleep(100);
                    isPressed = false;
                    Time.sleep(50);
                    switch (function) {
                        case "start":
                            game.buttonPlayer.show();
                            game.buttonSaturn.show();
                            game.buttonEmerald.show();
                            break;
                        case "quit":
                            lastClick = now * 10;
                            AudioHub.releaseAP();
                            game.saveSettings();
                            Service.systemExit();
                            break;
                        case "pause":
                            game.BOSS_TIME += System.currentTimeMillis() - game.pauseTimer;
                            AudioHub.deletePauseMusic();
                            AudioHub.whoIsPlayed();
                            if (PauseButton.oldStatus != GAME) {
                                Game.gameStatus = PauseButton.oldStatus;
                            } else {
                                Game.gameStatus = AFTER_PAUSE;
                            }
                            break;
                        case "menu":
                            game.onLoading(() -> {
                                game.saveScore();
                                game.generateMenu();
                            });
                            break;
                        case "top":
                            game.onLoading(game::generateTopScore);

                            ClientServer.postBestScore(Clerk.nickname, game.bestScore);
                            ClientServer.getStatistics();
                            game.generateTable();
                            break;
                        case "restart":
                            game.onLoading(() -> {
                                game.saveScore();
                                game.getMaxScore();
                                Game.level = 1;
                                game.generateNewGame();
                            });
                            break;
                        case "fromSetting":
                            game.hideSettings();
                            game.onLoading(game::generateMenu);
                            break;
                        case "settings":
                            ImageHub.loadSettingsImages();
                            game.onLoading(game::generateSettings);
                            break;
                    }
                });
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
