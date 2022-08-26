package com.warfare.darkannihilation.enemy.deathstar;

import static com.badlogic.gdx.graphics.g2d.Batch.X1;
import static com.badlogic.gdx.graphics.g2d.Batch.X2;
import static com.badlogic.gdx.graphics.g2d.Batch.X3;
import static com.badlogic.gdx.graphics.g2d.Batch.X4;
import static com.badlogic.gdx.graphics.g2d.Batch.Y1;
import static com.badlogic.gdx.graphics.g2d.Batch.Y2;
import static com.badlogic.gdx.graphics.g2d.Batch.Y3;
import static com.badlogic.gdx.graphics.g2d.Batch.Y4;
import static com.warfare.darkannihilation.constants.Constants.STAR_LASER_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.STAR_LASER_SHOOT_TIME;
import static com.warfare.darkannihilation.constants.Names.STAR_LASER;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.bullet.BaseBullet;

public class Laser extends BaseBullet {
    private final Array<Vector2> polygon = new Array<>(true, 4, Vector2.class);
    private final Vector2 spriteCenter = new Vector2();
    private final Animation<AtlasRegion> animation;

    private boolean finished;
    private float lastShoot;
    private float timer;

    public Laser() {
        super(getImages().deathStarLaser.getKeyFrame(0), STAR_LASER_DAMAGE, 0);
        polygon.addAll(new Vector2(), new Vector2(), new Vector2(), new Vector2());

        animation = getImages().deathStarLaser;
        setOrigin(47, halfHeight); // подобрал магический originX для красивого поворота
        name = STAR_LASER;
    }

    public void start(float x, float y, float degrees) {
        timer = 0;
        visible = true;
        finished = false;

        setRotation(degrees);
        updateRegion();
        setCenter(x, y);
    }

    private void updateRegion() {
        AtlasRegion frame = animation.getKeyFrame(timer);
        setRegion(frame);
        setSize(frame.originalWidth, frame.originalHeight);
    }

    @Override
    public void updateInThread() {
        if (finished) {
            if (time - lastShoot > STAR_LASER_SHOOT_TIME) {
                visible = false;
            }
            return;
        }

        updateRegion();

        timer += delta;
        if (animation.isAnimationFinished(timer)) {
            finished = true;
            lastShoot = time;
        }
    }

    @Override
    public boolean intersect(BaseSprite sprite) {
        final float[] vertices = getVertices();

        polygon.get(0).set(vertices[X1], vertices[Y1]);
        polygon.get(1).set(vertices[X2], vertices[Y2]);
        polygon.get(2).set(vertices[X3], vertices[Y3]);
        polygon.get(3).set(vertices[X4], vertices[Y4]);

        spriteCenter.set(sprite.centerX(), sprite.centerY());

        return Intersector.isPointInPolygon(polygon, spriteCenter);
    }

    @Override
    public void update() {
    }

    @Override
    public void kill() {
    }

    @Override
    public void killFromPlayer() {

    }
}
