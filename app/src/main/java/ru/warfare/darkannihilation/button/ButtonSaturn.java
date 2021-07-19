package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

public class ButtonSaturn extends Sprite {
    public ButtonSaturn(Game g) {
        super(g, ImageHub.buttonSaturnImg);

        y = Game.halfScreenHeight - halfHeight;
        hide();
    }

    public void hide() {
        x = Game.screenWidth;
    }

    public void show() {
        x = Game.halfScreenWidth - (game.buttonPlayer.width * 2) - width;
    }

    public void setCoords(int X, int Y) {
        if (x < X & X < x + width & y < Y & Y < y + height) {
            AudioHub.playClick();
            Game.character = "saturn";
            game.loadingScreen.newJob("newGame");
        }
    }
}
