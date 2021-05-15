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

        x = Game.halfScreenWidth - halfWidth;
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
        Game.blackPaint.setAlpha(0);
    }

    public void start() {
        Game.gameStatus = 6;
        x = Game.halfScreenWidth - halfWidth;
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
            Game.gameStatus = 7;
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
                    if (Game.gameStatus != 7) {
                        Game.gameStatus = 0;
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
            Game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, null);
            if (touch & Game.gameStatus != 4) {
                Game.blackPaint.setAlpha((int) alpha);
                alpha += 0.49;
                if (alpha >= 255) {
                    hide();
                    Game.level++;
                    LoadingScreen.jobs = "newGame";
                    Game.gameStatus = 41;
                }
            }
            Game.canvas.drawRect(0, 0, Game.screenWidth, Game.screenHeight, Game.blackPaint);
        }
    }
}
