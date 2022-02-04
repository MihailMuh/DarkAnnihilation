package com.warfare.darkannihilation.enemy;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.sprite.movement.Opponent;
import com.warfare.darkannihilation.player.Player;
import com.warfare.darkannihilation.utils.audio.MusicWrap;

public class Attention extends Opponent {
    private final MusicWrap music;

    public Attention(TextureAtlas.AtlasRegion texture, MusicWrap music, Rocket rocket) {
        super(null, texture, 0, 0, 0);
        this.music = music;
        music.setLooping(false);
        music.setOnCompletionListener(m -> {
            m.stop();
            visible = false;

            rocket.start(x);
        });

        y = SCREEN_HEIGHT - height - 10;
        visible = false;
    }

    @Override
    public boolean killedByPlayer(Player player) {
        return true;
    }

    @Override
    public void reset() {
        x = MathUtils.random(SCREEN_WIDTH);
        music.play();

        visible = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        if (visible) super.render();
    }
}
