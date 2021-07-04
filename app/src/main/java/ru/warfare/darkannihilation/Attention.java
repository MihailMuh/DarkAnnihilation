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
        x = MATH.randInt(0, screenWidthWidth);
        y = -height;
    }

    public void fire() {
        game.rocket.start(centerX());
        hide();
    }

    public void start() {
        y = 10;
        lock = false;
        game.context.runOnUiThread(() -> AudioHub.attentionSnd.setPlayWhenReady(true));
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.attentionImg, x, y, null);
    }
}
