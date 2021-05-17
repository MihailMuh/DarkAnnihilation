package ru.warfare.darkannihilation;

public class Portal extends Sprite {
    private int frame = 0;
    private static final int portalImageLength = ImageHub.portalImages.length;
    private float frameTime = 100;
    private long lastFrame;
    public boolean touch = false;

    public Portal(Game game) {
        super(game, ImageHub.portalImages[0].getWidth(), ImageHub.portalImages[0].getHeight());

        x = Game.halfScreenWidth - halfWidth;
        y = 100;
        isPassive = true;

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);

        lastFrame = System.currentTimeMillis();

        AudioPlayer.portalSound.seekTo(0);
        AudioPlayer.portalSound.start();
    }

    public void kill() {
        ImageHub.deletePortalImages();
        game.portal = null;
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 15, y + 15);
    }

    @Override
    public void intersectionPlayer() {
        if (Game.level == 2) {
            game.generateWin();
            kill();
        } else {
            touch = true;
            BaseCharacter.god = true;
            frameTime = 0;
            game.player.lock = true;
            AudioPlayer.timeMachineSnd.start();
            ImageHub.loadThunderImages(game.context);
        }
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrame > frameTime) {
            lastFrame = now;
            if (frame < portalImageLength - 1) {
                frame += 1;
            } else {
                frame = 0;
            }
            if (!AudioPlayer.portalSound.isPlaying() & !touch) {
                if (Game.gameStatus != 7) {
                    Game.gameStatus = 0;
                    kill();
                    AudioPlayer.resumeBackgroundMusic();
                }
            }
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, null);
        if (touch & !AudioPlayer.timeMachineSnd.isPlaying()) {
            Game.level++;
            game.loadingScreen.newJob("newGame");
            kill();
        }
    }
}
