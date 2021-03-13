package ru.warfare.darkannihilation;

public class Attention extends Sprite{
    public Attention(Game g) {
        super(g, ImageHub.attentionImg.getWidth(), ImageHub.attentionImg.getHeight());
        lock = true;

        y = -height;
    }

    public void hide() {
        lock = true;
        x = randInt(0, screenWidth);
        y = -height;
    }

    public void start() {
        y = 10;
        lock = false;
        AudioPlayer.attentionSnd.start();
    }

    @Override
    public void update() {
        if (!AudioPlayer.attentionSnd.isPlaying()) {
            game.rocket.start(x + halfWidth);
            hide();
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(ImageHub.attentionImg, x, y, null);
    }
}
