package ru.warfare.darkannihilation.base;

import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;

public abstract class BaseCharacterButton extends BaseButton {
    private final byte name;

    public BaseCharacterButton(Game game, Bitmap bitmap, byte name) {
        super(game, bitmap);

        y = HALF_SCREEN_HEIGHT - halfHeight;
        this.name = name;

        hide();
    }

    public void hide() {
        x = SCREEN_WIDTH;
    }

    public abstract void show();

    @Override
    public void setCoords(int X, int Y) {
        if (checkCoords(X, Y)) {
            AudioHub.playClick();
            game.onLoading(() -> {
                Game.character = name;
                game.generateNewGame();
            });
        }
    }
}