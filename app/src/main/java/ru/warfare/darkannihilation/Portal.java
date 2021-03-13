package ru.warfare.darkannihilation;


public class Portal extends Sprite {
    private int frame = 0;
    private static final int portalImageLength = ImageHub.portalImages.length;
    private static final int frameTime = 50;
    private long lastFrame;
    private long now;

    public Portal(Game g) {
        super(g, ImageHub.portalImages[0].getWidth(), ImageHub.portalImages[0].getHeight());

        x = halfScreenWidth - halfWidth;
        y = 100;
        lock = true;

        lastFrame = System.currentTimeMillis();
    }

    public void hide() {
        x = -width;
        frame = 0;
        lock = true;
    }

    public void start() {
        game.gameStatus = 6;
        x = halfScreenWidth - halfWidth;
        lock = false;
        AudioPlayer.portalSound.seekTo(0);
        AudioPlayer.portalSound.start();
        lastFrame = System.currentTimeMillis();
    }

    @Override
    public void check_intersectionPlayer() {
        if (x + 15 < game.player.x + 20 & game.player.x + 20 < x + width - 15 &
                y + 15 < game.player.y + 25 & game.player.y + 25 < y + height - 15 |
                game.player.x + 20 < x + 15 & x + 15 < game.player.x + game.player.width - 20 &
                        game.player.y + 25 < y + 15 & y + 15 < game.player.y + game.player.height - 20) {
            game.gameStatus = 7;
            AudioPlayer.portalSound.pause();
            if (AudioPlayer.bossMusic.isPlaying()) {
                AudioPlayer.bossMusic.pause();
            }
            AudioPlayer.winMusic.seekTo(0);
            AudioPlayer.winMusic.start();
            game.winScreen = new WinScreen(game);
            hide();
        }
    }

    @Override
    public void update() {
        check_intersectionPlayer();

        now = System.currentTimeMillis();
        if (now - lastFrame > frameTime) {
            lastFrame = now;
            if (AudioPlayer.portalSound.isPlaying()) {
                if (frame < portalImageLength - 1) {
                    frame += 1;
                } else {
                    frame = 0;
                }
            } else {
                if (game.gameStatus != 7) {
                    game.gameStatus = 0;
                    hide();
                    AudioPlayer.pirateMusic.start();
                }
            }
        }
    }

    @Override
    public void render() {
        if (!lock) {
            game.canvas.drawBitmap(ImageHub.portalImages[frame], x, y, null);
        }
    }
}
