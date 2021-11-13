package com.warfare.darkannihilation.systemd;

import static com.badlogic.gdx.Input.Keys.BACK;
import static com.warfare.darkannihilation.systemd.service.Windows.HARDCORE_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HARDCORE_WIDTH;
import static com.warfare.darkannihilation.systemd.Frontend.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.abstraction.BaseClickListener;

public class OnClickListener extends BaseClickListener {
    private static final Vector3 touchPos = new Vector3();

    private final Player player;

    public OnClickListener(Player player) {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchKey(BACK, true);

        this.player = player;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touchPos.x = Gdx.input.getX();
        touchPos.y = Gdx.input.getY();
        camera.unproject(touchPos, 0, 0, HARDCORE_WIDTH, HARDCORE_HEIGHT);

        player.setCoordinates(touchPos.x, touchPos.y);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
