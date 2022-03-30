package com.warfare.darkannihilation.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;

public abstract class OpponentPool extends Pool<Opponent> {
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
