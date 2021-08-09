package ru.warfare.darkannihilation.button;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseCharacterButton;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.NamesConst.EMERALD;

public class ButtonEmerald extends BaseCharacterButton {
    public ButtonEmerald(Game game) {
        super(game, ImageHub.buttonEmeraldImg, EMERALD);
    }

    @Override
    public void show() {
        x = Game.halfScreenWidth + (game.buttonPlayer.width * 2);
    }
}