package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.NamesConst.EMERALD;

public class ButtonEmerald extends Sprite {
    public ButtonEmerald(Game game) {
        super(game, ImageHub.buttonEmeraldImg);

        y = Game.halfScreenHeight - halfHeight;
        hide();
    }

    public void hide() {
        x = Game.screenWidth;
    }

    public void show() {
        x = Game.halfScreenWidth + (game.buttonPlayer.width * 2);
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            AudioHub.playClick();
            game.onLoading(() -> {
                Game.character = EMERALD;
                game.generateNewGame();
            });
        }
    }
}