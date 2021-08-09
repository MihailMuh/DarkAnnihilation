package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.Service;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_PORTAL_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.PORTAL_FRAME;
import static ru.warfare.darkannihilation.constant.Constants.PORTAL_LIFE_TIME;
import static ru.warfare.darkannihilation.constant.Modes.GAME;
import static ru.warfare.darkannihilation.math.Randomize.randInt;

public class Portal extends Sprite {
    private int frame = 0;
    private long lastFrame = System.currentTimeMillis();
    private final long lifeTime = System.currentTimeMillis();
    public boolean touch = false;

    public Portal(Game game) {
        super(game, ImageHub.portalImages[0]);
        AudioHub.loadPortalSounds();

        calculateBarriers();

        x = randInt(0, screenWidthWidth);
        y = randInt(20, height);

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    @Override
    public void hide() {
        game.intersectOnlyPlayer.remove(this);
        ImageHub.deletePortalImages();
        game.portal = null;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void intersectionPlayer() {
        touch = true;
        game.intersectOnlyPlayer.remove(this);
        game.ghosts.add(this);
        Service.runOnUiThread(() -> {
            AudioHub.deletePortalSnd();
            if (Game.level == 2) {
                ImageHub.loadWinImages(game.mainActivity);
            } else {
                AudioHub.playTimeMachine();

                game.player.god = true;
                game.player.lock = true;
            }
        });
    }

    @Override
    public void kill() {
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
            long now = System.currentTimeMillis();
            if (now - lastFrame > PORTAL_FRAME) {
                lastFrame = now;
                film();
            }

            if (now - lifeTime > PORTAL_LIFE_TIME) {
                Game.gameStatus = GAME;
                AudioHub.resumeBackgroundMusic();
                hide();
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, null);
    }
}
