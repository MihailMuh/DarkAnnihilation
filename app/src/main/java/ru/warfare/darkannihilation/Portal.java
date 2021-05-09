package ru.warfare.darkannihilation;

public class Portal extends Sprite {
    private int frame = 0;
    private static final int portalImageLength = ImageHub.portalImages.length;
    private float frameTime = 50;
    private long lastFrame;
    public boolean touch = false;
    private float alpha = 0;

    public Portal(Game g) {
        super(g, ImageHub.portalImages[0].getWidth(), ImageHub.portalImages[0].getHeight());

        x = halfScreenWidth - halfWidth;
        y = 100;
        lock = true;
        isPassive = true;

        recreateRect(x + 15, y + 15, x + width - 15, y + height - 15);

        lastFrame = System.currentTimeMillis();
    }

    public void hide() {
        x = -width;
        frame = 0;
        lock = true;
        touch = false;
        frameTime = 50;
        alpha = 0;
    }

    public void start() {
        game.gameStatus = 6;
        x = halfScreenWidth - halfWidth;
        lock = false;
        AudioPlayer.portalSound.seekTo(0);
        AudioPlayer.portalSound.start();
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 15, y + 15);
    }

    @Override
    public void intersectionPlayer() {
        if (Game.level == 2) {
            game.gameStatus = 7;
            AudioPlayer.portalSound.pause();
            AudioPlayer.winMusic.seekTo(0);
            AudioPlayer.winMusic.start();
            game.winScreen = new WinScreen(game);
            hide();
        } else {
            touch = true;
            BaseCharacter.god = true;
            frameTime = 0;
            game.player.lock = true;
            AudioPlayer.timeMachineSnd.start();
        }
    }

    @Override
    public void update() {
        if (!lock) {
            long now = System.currentTimeMillis();
            if (now - lastFrame > frameTime) {
                lastFrame = now;
                if (frame < portalImageLength - 1) {
                    frame += 1;
                } else {
                    frame = 0;
                }
                if (!AudioPlayer.portalSound.isPlaying() & !touch) {
                    if (game.gameStatus != 7) {
                        game.gameStatus = 0;
                        hide();
                        Service.resumeBackgroundMusic();
                    }
                }
            }
        }
    }

    @Override
    public void render() {
        if (!lock) {
            game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, null);
            if (touch & game.gameStatus != 4) {
                Game.blackPaint.setAlpha((int) alpha);
                game.canvas.drawRect(0, 0, screenWidth, screenHeight, Game.blackPaint);
                alpha += 0.5;
                if (alpha >= 256) {
                    hide();
                    Game.level++;
                    LoadingScreen.jobs = "newGame";
                    game.gameStatus = 41;
                }
            }
        }
    }
}
