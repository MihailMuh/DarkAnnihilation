package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.NUMBER_PORTAL_IMAGES;
import static ru.warfare.darkannihilation.Constants.PORTAL_FRAME;
import static ru.warfare.darkannihilation.MATH.randInt;

public class Portal extends Sprite {
    private int frame = 0;
    private long lastFrame = System.currentTimeMillis();
    private final long lifeTime = System.currentTimeMillis();
    public boolean touch = false;
    private static final int len = NUMBER_PORTAL_IMAGES - 1;
//    private boolean waited;
//    private int cycles;

    public Portal(Game game) {
        super(game, ImageHub.portalImages[0].getWidth(), ImageHub.portalImages[0].getHeight());
        game.context.runOnUiThread(() -> {
            x = randInt(0, screenWidthWidth);
            y = randInt(50, 250);
            isPassive = true;

            recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);

            AudioHub.loadPortalSounds();
        });
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
        game.context.runOnUiThread(() -> {
            touch = true;
            if (Game.level == 2) {
                ImageHub.loadWinImages(game.context);
            } else {
                AudioHub.timeMachineSnd.play();
                AudioHub.portalSound.pause();

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

//            if (!AudioHub.timeMachineNoneSnd.isPlaying() & Game.level == 1) {
//                AudioHub.timeMachineSecondSnd.play();
//                Game.level++;
//                game.loadingScreen.newJob("newGame");
//                kill();
//            }
        } else {
            game.player.checkIntersections(this);

            long now = System.currentTimeMillis();
            if (now - lastFrame > PORTAL_FRAME) {
                lastFrame = now;
                film();
//                if (!waited) {
//                    cycles++;
//                    if (cycles == 90) {
//                        waited = true;
//                    }
//                }
            }

            if (now - lifeTime > 7_000) {
                Game.gameStatus = 0;
                AudioHub.resumeBackgroundMusic();
                kill();
            }
//
//            if (waited) {
//                if (!AudioHub.portalSound.isPlaying()) {
//                    Game.gameStatus = 0;
//                    AudioHub.resumeBackgroundMusic();
//                    kill();
//                }
//            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, Game.nicePaint);
    }
}
