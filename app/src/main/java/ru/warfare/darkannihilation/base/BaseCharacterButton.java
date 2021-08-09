package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;

public abstract class BaseCharacterButton extends BaseButton {
    private final byte name;

    public BaseCharacterButton(Game game, Bitmap bitmap, byte name) {
        super(game, bitmap);

        y = Game.halfScreenHeight - halfHeight;
        this.name = name;

        hide();
    }

    public void hide() {
        x = Game.screenWidth;
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