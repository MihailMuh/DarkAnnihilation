package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.systemd.Game;

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
        AudioHub.playAttentionSnd();
        y = 10;
        lock = false;
    }
}
