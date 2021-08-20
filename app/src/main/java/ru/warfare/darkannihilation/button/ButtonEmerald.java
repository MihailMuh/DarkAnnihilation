package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseCharacterButton;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.NamesConst.EMERALD;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

public class ButtonEmerald extends BaseCharacterButton {
    public ButtonEmerald(Game game) {
        super(game, ImageHub.buttonEmeraldImg, EMERALD);
    }

    @Override
    public void show() {
        x = HALF_SCREEN_WIDTH + (game.buttonPlayer.width * 2);
    }
}