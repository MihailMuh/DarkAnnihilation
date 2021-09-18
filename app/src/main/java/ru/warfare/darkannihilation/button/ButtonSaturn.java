package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseCharacterButton;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.NamesConst.SATURN;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

public class ButtonSaturn extends BaseCharacterButton {
    public ButtonSaturn(Game g) {
        super(g, ImageHub.buttonSaturnImg, SATURN);
    }

    @Override
    public void show() {
        x = HALF_SCREEN_WIDTH - (game.buttonPlayer.width * 2) - width;
    }
}
