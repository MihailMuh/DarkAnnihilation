package com.warfare.darkannihilation.pools;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.enemy.Minion;
import com.warfare.darkannihilation.utils.PoolWrap;

public class MinionPool extends PoolWrap<Opponent> {
    private final Array<Opponent> empire;

    public MinionPool(Array<Opponent> empire) {
        super(NUMBER_VADER);
        this.empire = empire;
    }

    public void obtain(float x, float y) {
        Minion minion = (Minion) obtain();
        minion.start(x, y);
        Gdx.app.postRunnable(() -> empire.add(minion));
    }

    @Override
    protected Minion newObject() {
        return new Minion();
    }
}
