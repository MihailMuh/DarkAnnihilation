package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseCharacterButton;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.NamesConst.MILLENNIUM_FALCON;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

public class ButtonPlayer extends BaseCharacterButton {
    public ButtonPlayer(Game game) {
        super(game, ImageHub.buttonPlayerImg, MILLENNIUM_FALCON);
    }

    @Override
    public void show() {
        x = HALF_SCREEN_WIDTH - halfWidth;
    }
}
