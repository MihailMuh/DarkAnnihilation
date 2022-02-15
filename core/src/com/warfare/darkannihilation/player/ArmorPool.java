package com.warfare.darkannihilation.player;

import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.utils.PoolSuper;

public class ArmorPool extends PoolSuper<Heart> {
    private final Array<Heart> hearts;

    public ArmorPool(Array<Heart> hearts) {
        super(20);
        this.hearts = hearts;
    }

    public void obtain(int x, int y) {
        ArmorHeart armorHeart = (ArmorHeart) obtain();
        armorHeart.start(x, y);
        hearts.add(armorHeart);
    }

    @Override
    protected ArmorHeart newObject() {
        return new ArmorHeart();
    }
}
