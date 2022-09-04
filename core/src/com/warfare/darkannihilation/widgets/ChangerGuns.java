package com.warfare.darkannihilation.widgets;

import static com.warfare.darkannihilation.constants.Constants.CHANGER_GUNS_CLICK_TIME;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.hub.Resources.getPlayer;
import static com.warfare.darkannihilation.hub.Resources.getSounds;
import static com.warfare.darkannihilation.systemd.service.Watch.time;

import com.badlogic.gdx.math.MathUtils;
import com.warfare.darkannihilation.abstraction.BaseButton;
import com.warfare.darkannihilation.support.GunKit;

public class ChangerGuns extends BaseButton {
    private boolean activeAlternativeGun = false;
    private float tapTime;

    public ChangerGuns() {
        super(getImages().changerGunsImages[0]);

        visible = false;
        translate(30, 30);
    }

    public void changeGun() {
        if (MathUtils.randomBoolean()) {
            getSounds().reloadSound0.play(true);
        } else {
            getSounds().reloadSound1.play(true);
        }

        activeAlternativeGun = !activeAlternativeGun;
        reset();
    }

    @Override
    public void reset() {
        getPlayer().activateAlternativeGun(activeAlternativeGun);

        if (GunKit.COLLECTED) {
            if (activeAlternativeGun) {
                setRegion(getImages().changerGunsImages[1]);
            } else {
                setRegion(getImages().changerGunsImages[2]);
            }
        } else {
            setRegion(getImages().changerGunsImages[0]);
        }
        visible = true;
    }

    @Override
    public void onClick(float x, float y) {
        if (time - tapTime >= CHANGER_GUNS_CLICK_TIME) {
            tapTime = time;

            if (GunKit.COLLECTED && checkClick(x, y)) {
                changeGun();
            }
        }
    }

    @Override
    public boolean checkClick(float x, float y) {
        return super.checkClick(x, y) && visible;
    }

    @Override
    public void render() {
        if (visible) super.render();
    }
}
