package com.warfare.darkannihilation.pools;

import static com.warfare.darkannihilation.constants.Constants.NUMBER_VADER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.enemy.Minion;

public abstract class MinionPool extends Pool<Opponent> {
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
}
