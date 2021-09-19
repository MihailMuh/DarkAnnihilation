package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BUFFER_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BUFFER_HEALTH;
import static ru.warfare.darkannihilation.math.Randomize.randInt;

public class Buffer extends Sprite {
    private boolean startBuff = false;

    public Buffer(Game game) {
        super(game, ImageHub.bufferImg);
        damage = BUFFER_DAMAGE;

        calculateBarriers();
        hide();

        recreateRect(x + 70, y + 70, right() - 70, bottom() - 35);
    }

    @Override
    public void hide() {
        lock = true;
        health = BUFFER_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        speedY = randInt(5, 10);
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 70, y + 70);
    }

    private void stopBFF() {
        if (startBuff) {
            startBuff = false;
            for (int i = 0; i < game.enemies.size(); i++) {
                game.enemies.get(i).stopBuff();
            }
        }
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        hide();

        HardThread.doInBackGround(this::stopBFF);
    }

    @Override
    public void kill() {
        Game.score += 100;
        intersectionPlayer();
    }

    @Override
    public void update() {
        if (y < 35) {
            y += speedY;
        } else {
            if (!startBuff) {
                startBuff = true;
                HardThread.doInBackGround(() -> {
                    for (int i = 0; i < game.enemies.size(); i++) {
                        game.enemies.get(i).buff();
                    }
                });
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);

        Game.canvas.drawRect(centerX() - 75, y + 50, centerX() + 75, y + 65, Game.scorePaint);
        Game.canvas.drawRect(centerX() - 73, y + 52, centerX() - 77 + (health / (float) BUFFER_HEALTH) * 150, y + 63, Game.fpsPaint);
    }
}

