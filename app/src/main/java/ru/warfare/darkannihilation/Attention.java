package ru.warfare.darkannihilation;

public class Attention extends Sprite {
    public Attention(Game game) {
        super(game, ImageHub.attentionImg.getWidth(), ImageHub.attentionImg.getHeight());
        isPassive = true;
        isBullet = true;

        hide();
    }

    public void hide() {
        lock = true;
        x = randInt(0, Game.screenWidth);
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
            game.rocket.start(centerX());
            hide();
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.attentionImg, x, y, null);
    }
}
