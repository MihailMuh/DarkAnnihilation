package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

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
            Game.character = "falcon";
            game.loadingScreen.newJob("newGame");
        }
    }
}
