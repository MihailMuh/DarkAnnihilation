package ru.warfare.darkannihilation.screen;

import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

public class FightScreen extends Sprite {
    public FightScreen() {
        super(null, ImageHub.fightScreen);

        x = HALF_SCREEN_WIDTH - halfWidth;
        y = HALF_SCREEN_HEIGHT - halfHeight;
    }

    @Override
    public void update() {
    }

    @Override
    public void kill() {
    }
}
