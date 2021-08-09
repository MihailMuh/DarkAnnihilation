package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseCharacterButton;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.NamesConst.MILLENNIUM_FALCON;

public class ButtonPlayer extends BaseCharacterButton {
    public ButtonPlayer(Game game) {
        super(game, ImageHub.buttonPlayerImg, MILLENNIUM_FALCON);
    }

    @Override
    public void show() {
        x = Game.halfScreenWidth - halfWidth;
    }
}
