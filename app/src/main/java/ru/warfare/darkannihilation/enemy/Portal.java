package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_PORTAL_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.PORTAL_FRAME;
import static ru.warfare.darkannihilation.constant.Constants.PORTAL_LIFE_TIME;
import static ru.warfare.darkannihilation.math.Math.randInt;

public class Portal extends Sprite {
    private int frame = 0;
    private long lastFrame = System.currentTimeMillis();
    private final long lifeTime = System.currentTimeMillis();
    public boolean touch = false;
    private static final int len = NUMBER_PORTAL_IMAGES - 1;

    public Portal(Game game) {
        super(game, ImageHub.portalImages[0]);
        AudioHub.loadPortalSounds();

        calculateBarriers();

        x = randInt(0, screenWidthWidth);
        y = randInt(20, height);
        isPassive = true;

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    public void kill() {
        ImageHub.deletePortalImages();
        game.portal = null;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void intersectionPlayer() {
        HardThread.doInUI(() -> {
            touch = true;
            AudioHub.deletePortalSnd();
            if (Game.level == 2) {
                ImageHub.loadWinImages(game.mainActivity);
            } else {
                AudioHub.playTimeMachine();

                game.player.god = true;
                game.player.lock = true;
                ImageHub.loadSecondLevelImages();
            }
        });
    }

    private void film() {
        if (frame != len) {
            frame++;
        } else {
            frame = 0;
        }
    }

    @Override
    public void update() {
        if (touch) {
            film();
        } else {
            game.player.checkIntersections(this);

            long now = System.currentTimeMillis();
            if (now - lastFrame > PORTAL_FRAME) {
                lastFrame = now;
                film();
            }

            if (now - lifeTime > PORTAL_LIFE_TIME) {
                Game.gameStatus = 0;
                AudioHub.resumeBackgroundMusic();
                kill();
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, null);
    }
}
