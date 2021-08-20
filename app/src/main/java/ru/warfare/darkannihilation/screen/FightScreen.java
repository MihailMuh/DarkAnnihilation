package ru.warfare.darkannihilation.screen;

import static ru.warfare.darkannihilation.systemd.service.Windows.DENSITY;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.systemd.service.Time;
import ru.warfare.darkannihilation.base.Sprite;

public class FightScreen extends Sprite {
    public FightScreen(byte character, byte boss) {
        super(null);

        ImageHub.loadFightScreen(character, boss);

        height = ImageHub._095;

        int min = 10;
        int screenWidth_150 = SCREEN_WIDTH - 150;
        while (height > SCREEN_HEIGHT) {
            height = (int) ((screenWidth_150 - min) * DENSITY);
            min += 10;
        }

        width = (int) (height * 1.05);
        halfHeight = height / 2;
        halfWidth = width / 2;

        x = HALF_SCREEN_WIDTH - halfWidth;
        y = HALF_SCREEN_HEIGHT - halfHeight;

        Time.waitImg();

        image = ImageHub.resizeBitmap(ImageHub.fightScreen, width, height);
    }

    @Override
    public void update() {
    }

    @Override
    public void kill() {
    }
}
