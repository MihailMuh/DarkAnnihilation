package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseCharacterButton;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.NamesConst.SATURN;

public class ButtonSaturn extends BaseCharacterButton {
    public ButtonSaturn(Game g) {
        super(g, ImageHub.buttonSaturnImg, SATURN);
    }

    @Override
    public void show() {
        x = Game.halfScreenWidth - (game.buttonPlayer.width * 2) - width;
    }
}
