package ru.warfare.darkannihilation.screen;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.Time;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;

public class FightScreen extends Sprite {
    public FightScreen(byte character, byte boss) {
        super(null);

        ImageHub.loadFightScreen(character, boss);

        y = Game.halfScreenHeight;
        height = ImageHub._095;

        int min = 10;
        while (bottom() - Game.halfScreenHeight > Game.screenHeight) {
            height = (int) ((Game.screenWidth-150-min) * Game.resizeK);
            min += 10;
        }

        width = (int) (height * 1.05);
        halfHeight = height / 2;
        halfWidth = width / 2;

        x = Game.halfScreenWidth - halfWidth;
        y = Game.halfScreenHeight - halfHeight;

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
