package com.warfare.darkannihilation.scenes.firstlevel;

import static com.warfare.darkannihilation.hub.Resources.getFonts;
import static com.warfare.darkannihilation.hub.Resources.getLocales;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.bullet.BaseBullet;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.utils.FontWrap;

class Frontend {
    private final FirstLevel firstLevel;
    private final StringBuilder stringBuilder = new StringBuilder(30);

    private final FontWrap font;
    private final String currentScoreString = getLocales().currentScore;
    private final float textX, textY;

    private final Player player;
    private BaseSprite screen;

    private final Array<Explosion> explosions;
    private final Array<Bullet> bullets;
    private final Array<Opponent> empire;
    private final Array<BaseBullet> bulletsEnemy;

    Frontend(FirstLevel firstLevel, Player player, BaseSprite screen, Array<Explosion> explosions,
             Array<Bullet> bullets, Array<Opponent> empire, Array<BaseBullet> bulletsEnemy) {
        this.firstLevel = firstLevel;
        this.player = player;
        this.screen = screen;
        this.explosions = explosions;
        this.bullets = bullets;
        this.empire = empire;
        this.bulletsEnemy = bulletsEnemy;

        font = FontWrap.scaledFontWrap(getFonts().canisMinor, HALF_SCREEN_WIDTH / 1.75f, currentScoreString + "10");
        textX = HALF_SCREEN_WIDTH - font.getHalfTextWidth(currentScoreString + "10");
        textY = SCREEN_HEIGHT - font.getTextHeight(currentScoreString + "10");
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

        player.render();
        for (Bullet bullet : bullets) {
            bullet.render();
        }
        for (Explosion explosion : explosions) {
            explosion.render();
        }
        player.renderHearts();

        font.draw(textX, textY, stringBuilder.append(currentScoreString).append(firstLevel.score).toString());
        stringBuilder.length = 0;
    }
}
