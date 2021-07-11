package ru.warfare.darkannihilation.screen;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

public class FightScreen extends Sprite {
    public FightScreen() {
        super(ImageHub.playerVsBoss);

        HardThread.newJob(() -> {
            y = Game.halfScreenHeight;

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
        });
    }

    public void newImg(String character) {
        switch (character)
        {
            case "saturn":
                image = ImageHub.resizeImage(ImageHub.saturnVsBoss, width, height);
                break;
            case "falcon":
                image = ImageHub.resizeImage(ImageHub.playerVsBoss, width, height);
                break;
            case "saturn-vaders":
                image = ImageHub.resizeImage(ImageHub.saturnVsVaders, width, height);
                break;
            case "falcon-vaders":
                image = ImageHub.resizeImage(ImageHub.playerVsVaders, width, height);
                break;
            case "emerald":
                image = ImageHub.resizeImage(ImageHub.emeraldVsBoss, width, height);
                break;
            case "emerald-vaders":
                image = ImageHub.resizeImage(ImageHub.emeraldVsVaders, width, height);
                break;
        }
    }
}
