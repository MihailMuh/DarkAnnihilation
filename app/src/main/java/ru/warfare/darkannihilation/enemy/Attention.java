package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.systemd.Service;
import ru.warfare.darkannihilation.base.Sprite;

public class Attention extends Sprite {
    public Attention(Game game) {
        super(game, ImageHub.attentionImg);
        isPassive = true;
        isBullet = true;

        hide();
    }

    public void hide() {
        lock = true;
        x = Math.randInt(0, screenWidthWidth);
        y = -height;
    }

    public void fire() {
        game.rocket.start(centerX());
        hide();
    }

    public void start() {
        Service.runOnUiThread(() -> AudioHub.attentionSnd.play());
        y = 10;
        lock = false;
    }
}
