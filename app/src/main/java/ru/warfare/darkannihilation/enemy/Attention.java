package ru.warfare.darkannihilation.enemy;

import static ru.warfare.darkannihilation.math.Randomize.randInt;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.SpriteWrapper;
import ru.warfare.darkannihilation.systemd.Game;

public class Attention extends SpriteWrapper {
    public Attention(Game game) {
        super(game, ImageHub.attentionImg);

        y = 10;
        lock = true;
        calculateBarriers();
    }

    public void fire() {
        game.rocket.start(centerX());
        lock = true;
    }

    @Override
    public void start() {
        x = randInt(0, screenWidthWidth);
        lock = false;
        AudioHub.playAttentionSnd();
    }

    @Override
    public void turn() {
        render();
    }
}
