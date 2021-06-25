package ru.warfare.darkannihilation;

public class DefaultExplosion extends BaseExplosion {
    public DefaultExplosion(String size) {
        super(ImageHub.explosionDefaultImageSmall[0].getWidth(), ImageHub.explosionDefaultImageSmall[0].getHeight());

        switch (size)
        {
            case "small":
                img = ImageHub.explosionDefaultImageSmall.clone();
                break;
            case "default":
                img = ImageHub.explosionDefaultImageMedium.clone();
                width = img[0].getWidth();
                halfWidth = width / 2;
                height = img[0].getHeight();
                halfHeight = height / 2;
                break;
        }
    }

    @Override
    public void update() {
        if (frame != 28) {
            frame++;
        } else {
            frame = 0;
            lock = true;
        }
    }

    @Override
    public void render () {
        if (frame != 28) {
            Game.canvas.drawBitmap(img[frame], x, y, null);
        }
    }
}