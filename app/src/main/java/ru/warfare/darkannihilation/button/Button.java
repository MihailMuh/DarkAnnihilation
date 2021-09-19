package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.systemd.service.Clerk;
import ru.warfare.darkannihilation.systemd.service.ClientServer;
import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.systemd.service.Time;
import ru.warfare.darkannihilation.base.BaseButton;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.service.Service;

import static ru.warfare.darkannihilation.constant.Constants.BUTTON_CLICK_TIME;
import static ru.warfare.darkannihilation.constant.Modes.AFTER_SETTINGS;
import static ru.warfare.darkannihilation.constant.Modes.GAME;
import static ru.warfare.darkannihilation.constant.Modes.MENU;
import static ru.warfare.darkannihilation.constant.Modes.PAUSE;
import static ru.warfare.darkannihilation.constant.Modes.QUIT;
import static ru.warfare.darkannihilation.constant.Modes.RESTART;
import static ru.warfare.darkannihilation.constant.Modes.SETTINGS;
import static ru.warfare.darkannihilation.constant.Modes.TOP;
import static ru.warfare.darkannihilation.systemd.Game.now;

public class Button extends BaseButton {
    public byte function;
    private String text = " ";
    private float textX;
    private float textY;
    private long lastClick = now;
    private boolean isPressed = false;

    public Button(Game game) {
        super(game, ImageHub.buttonImagePressed);
    }

    public void setText(String text) {
        newFunc(text, x, y, function);
    }

    public void newFunc(String name, int X, int Y, byte func) {
        function = func;

        y = Y;
        x = X;

        text = name;

        width = ImageHub.buttonImageNotPressed.getWidth();
        halfWidth = width / 2;

        textX = centerX() - Game.buttonsPaint.measureText(text) / 2f;
        textY = centerY() + Game.buttonsPaint.getTextSize() / 4f;
    }

    public void sweep(int X, int Y) {
        isPressed = checkCoords(X, Y);
    }

    @Override
    public void setCoords(int X, int Y) {
        if (checkCoords(X, Y)) {
            if (now - lastClick > BUTTON_CLICK_TIME) {
                lastClick = now;
                AudioHub.playClick();
                isPressed = false;
                Time.sleep(160);
                switch (function) {
                    case GAME:
                        game.buttonPlayer.show();
                        game.buttonSaturn.show();
                        game.buttonEmerald.show();
                        break;
                    case QUIT:
                        AudioHub.releaseAP();
                        game.saveSettings();
                        game.onPause();
                        Service.systemExit();
                        break;
                    case PAUSE:
                        game.generateAfterPauseScene();
                        break;
                    case MENU:
                        game.onLoading(() -> {
                            game.saveScore();
                            game.generateMenu();
                        });
                        break;
                    case TOP:
                        game.onLoading(game::generateTopScore);

                        HardThread.doInBackGround(() -> {
                            ClientServer.postBestScore(Clerk.nickname, game.bestScore);
                            ClientServer.getStatistics();
                            game.generateTable();
                        });
                        break;
                    case RESTART:
                        game.onLoading(() -> {
                            game.saveScore();
                            game.getMaxScore();
                            Game.level = 1;
                            game.generateNewGame();
                        });
                        break;
                    case AFTER_SETTINGS:
                        game.hideSettings();
                        game.onLoading(game::generateMenu);
                        break;
                    case SETTINGS:
                        ImageHub.loadSettingsImages();
                        game.onLoading(game::generateSettings);
                        break;
                }
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
