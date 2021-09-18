package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.math.Randomize.randInt;

public class Attention extends Sprite {
    public Attention(Game game) {
        super(game, ImageHub.attentionImg);

        calculateBarriers();
        kill();
    }

    @Override
    public void kill() {
        x = randInt(0, screenWidthWidth);
        y = -height;
        lock = true;
    }

    public void fire() {
        game.rocket.start(centerX());
        kill();
    }

    @Override
    public void start() {
        AudioHub.playAttentionSnd();
        y = 10;
        lock = false;
    }

    @Override
    public void update() {
    }

    @Override
    public void turn() {
        render();
    }
}
