package com.warfare.darkannihilation.systemd.game;

import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.abstraction.BaseBullet;
import com.warfare.darkannihilation.abstraction.BaseScreen;
import com.warfare.darkannihilation.abstraction.Warrior;
import com.warfare.darkannihilation.bullet.Bullet;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.utils.FontWrap;

class Frontend {
    private final Game game;
    private final StringBuilder stringBuilder = new StringBuilder(30);

    private final FontWrap font;
    private final float textX, textY;

    private final Player player;
    private final BaseScreen screen;

    private final Array<Explosion> explosions;
    private final Array<Bullet> bullets;
    private final Array<Warrior> empire;
    private final Array<BaseBullet> bulletsEnemy;

    Frontend(Game game, BitmapFont bitmapFont, Player player, BaseScreen screen, Array<Explosion> explosions,
             Array<Bullet> bullets, Array<Warrior> empire, Array<BaseBullet> bulletsEnemy) {
        this.game = game;
        this.player = player;
        this.screen = screen;
        this.explosions = explosions;
        this.bullets = bullets;
        this.empire = empire;
        this.bulletsEnemy = bulletsEnemy;

        font = new FontWrap(bitmapFont, FontHub.resizeFont(bitmapFont, HALF_SCREEN_WIDTH / 1.8f, "Current score: 0"));
        textX = HALF_SCREEN_WIDTH - font.getTextWidth("Current score: 0") / 2f;
        textY = SCREEN_HEIGHT - font.getTextHeight("Current score: 0");
    }

    public void render() {
        screen.render();
        player.render();

        for (Warrior enemy : empire) {
            enemy.render();
        }
        for (BaseBullet baseBullet : bulletsEnemy) {
            baseBullet.render();
        }
        for (Bullet bullet : bullets) {
            bullet.render();
        }
        for (Explosion explosion : explosions) {
            explosion.render();
        }

        font.draw(textX, textY, stringBuilder.append("Current score: ").append(game.score).toString());
        stringBuilder.length = 0;
    }
}
