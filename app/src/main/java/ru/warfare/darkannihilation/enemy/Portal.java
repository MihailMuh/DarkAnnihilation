package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.base.SpriteWrapper;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.service.Service;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_PORTAL_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.PORTAL_FRAME;
import static ru.warfare.darkannihilation.constant.Constants.PORTAL_LIFE_TIME;
import static ru.warfare.darkannihilation.constant.Modes.GAME;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.Game.now;

public class Portal extends SpriteWrapper {
    private int frame;
    private long lastFrame;
    private long lifeTime;
    private boolean touch;

    public Portal(Game game) {
        super(game);
        calculateBarriers();

        lock = true;

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    @Override
    public void start() {
        AudioHub.loadPortalSounds();

        image = ImageHub.portalImages[0];
        makeParams();

        x = randInt(0, screenWidthWidth);
        y = randInt(20, height);

        touch = false;
        frame = 0;

        lock = false;
        lifeTime = now;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void intersectionPlayer() {
        if (!touch) {
            touch = true;
            if (Game.level == 2) {
                ImageHub.loadWinImages(game);
            } else {
                AudioHub.playTimeMachine();

                game.player.god = true;
                game.player.stop();
            }
            Service.runOnUiThread(AudioHub::deletePortalSnd);
        }
    }

    private void film() {
        frame++;
        if (frame == NUMBER_PORTAL_IMAGES) {
            frame = 0;
        }
    }

    @Override
    public void update() {
        if (touch) {
            film();
        } else {
            if (now - lastFrame > PORTAL_FRAME) {
                lastFrame = now;
                film();
            }

            if (now - lifeTime > PORTAL_LIFE_TIME) {
                Game.gameStatus = GAME;
                AudioHub.resumeBackgroundMusic();
                game.killPortal();
                game.gameTask.start();
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, null);
    }
}
