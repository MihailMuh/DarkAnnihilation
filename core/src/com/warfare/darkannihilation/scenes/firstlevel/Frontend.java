package com.warfare.darkannihilation.scenes.firstlevel;

import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.hub.Resources.getPlayer;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.utils.Font;

class Frontend {
    private final FirstLevel firstLevel;

    private final Font font;
    private final float textX, textY;

    private BaseSprite screen;

    private final Array<Explosion> explosions;
    private final Array<Bullet> bullets;
    private final Array<Opponent> empire;
    private final Array<BaseBullet> bulletsEnemy;

    Frontend(FirstLevel firstLevel, BaseSprite screen, Array<Explosion> explosions,
             Array<Bullet> bullets, Array<Opponent> empire, Array<BaseBullet> bulletsEnemy) {
        this.firstLevel = firstLevel;
        this.screen = screen;
        this.explosions = explosions;
        this.bullets = bullets;
        this.empire = empire;
        this.bulletsEnemy = bulletsEnemy;

        font = Font.scaledFontWrap(getFonts().canisMinor, HALF_SCREEN_WIDTH / 1.75f, getLocales().currentScore + "10");
        textX = HALF_SCREEN_WIDTH - font.getHalfTextWidth(getLocales().currentScore + "10");
        textY = SCREEN_HEIGHT - font.getTextHeight(getLocales().currentScore + "10");
    }

    void setScreen(BaseSprite screen) {
        this.screen = screen;
    }

    void render() {
        screen.render();

        for (Opponent opponent : empire) {
            opponent.render();
        }
        for (BaseBullet baseBullet : bulletsEnemy) {
            baseBullet.render();
        }

        for (Bullet bullet : bullets) {
            bullet.render();
        }
        getPlayer().render();

        for (Explosion explosion : explosions) {
            explosion.render();
        }

        font.draw(textX, textY, firstLevel.scoreForFrontend);
    }
}
