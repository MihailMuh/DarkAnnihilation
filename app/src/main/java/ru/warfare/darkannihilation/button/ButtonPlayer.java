package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.NamesConst.MILLENNIUM_FALCON;

public class ButtonPlayer extends Sprite {
    public ButtonPlayer(Game game) {
        super(game, ImageHub.buttonPlayerImg);

        y = Game.halfScreenHeight - halfHeight;
        hide();
    }

    public void hide() {
        x = Game.screenWidth;
    }

    public void show() {
        x = Game.halfScreenWidth - halfWidth;
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            AudioHub.playClick();
            game.onLoading(() -> {
                Game.character = MILLENNIUM_FALCON;
                game.generateNewGame();
            });
        }
    }
}
