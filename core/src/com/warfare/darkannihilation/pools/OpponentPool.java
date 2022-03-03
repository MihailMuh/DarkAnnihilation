package com.warfare.darkannihilation.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.utils.PoolSuper;

public abstract class OpponentPool extends PoolSuper<Opponent> {
    private final Array<Opponent> empire;

    public OpponentPool(Array<Opponent> empire, int size) {
        super(size);
        this.empire = empire;
    }

    @Override
    public Opponent obtain() {
        Opponent opponent = super.obtain();
        opponent.start();
        Gdx.app.postRunnable(() -> empire.add(opponent));
        return opponent;
    }
}
