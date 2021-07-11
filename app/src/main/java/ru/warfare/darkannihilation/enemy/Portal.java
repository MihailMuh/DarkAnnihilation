package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.systemd.Service;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.Constants.NUMBER_PORTAL_IMAGES;
import static ru.warfare.darkannihilation.Constants.PORTAL_FRAME;
import static ru.warfare.darkannihilation.Constants.PORTAL_LIFE_TIME;
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

        x = randInt(0, screenWidthWidth);
        y = randInt(50, 250);
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
        Service.runOnUiThread(() -> {
            touch = true;
            AudioHub.portalSound.pause();
            if (Game.level == 2) {
                ImageHub.loadWinImages();
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
        Game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, Game.nicePaint);
    }
}
