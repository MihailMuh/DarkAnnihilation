package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.NUMBER_PORTAL_IMAGES;
import static ru.warfare.darkannihilation.Constants.PORTAL_FRAME;
import static ru.warfare.darkannihilation.MATH.randInt;

public class Portal extends Sprite {
    private int frame = 0;
    private long lastFrame = System.currentTimeMillis();
    public boolean touch = false;
    private static final int len = NUMBER_PORTAL_IMAGES - 1;
    private int cycles;
    private boolean waited;

    public Portal(Game game) {
        super(game, ImageHub.portalImages[0].getWidth(), ImageHub.portalImages[0].getHeight());

        x = randInt(0, screenWidthWidth);
        y = randInt(50, 250);
        isPassive = true;

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);

        AudioHub.portalSound.seekTo(0);
        AudioHub.portalSound.start();
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
        if (Game.level == 2) {
            touch = true;

            ImageHub.loadWinImages(game.context);
            game.generateWin();
        } else {
            AudioHub.timeMachineFirstSnd.start();
            AudioHub.timeMachineNoneSnd.start();

            touch = true;
            game.player.god = true;
            game.player.lock = true;
            ImageHub.loadSecondLevelImages();
        }
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

            if (!AudioHub.timeMachineNoneSnd.isPlaying()) {
                AudioHub.timeMachineSecondSnd.start();
                Game.level++;
                game.loadingScreen.newJob("newGame");
                kill();
            }
        } else {
            game.player.checkIntersections(this);

            long now = System.currentTimeMillis();
            if (now - lastFrame > PORTAL_FRAME) {
                lastFrame = now;
                film();
                if (!waited) {
                    cycles++;
                    if (cycles == 90) {
                        waited = true;
                    }
                }
            }

            if (waited) {
                if (!AudioHub.portalSound.isPlaying() & Game.gameStatus != 7) {
                    Game.gameStatus = 0;
                    AudioHub.resumeBackgroundMusic();
                    kill();
                }
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, Game.nicePaint);
    }
}
