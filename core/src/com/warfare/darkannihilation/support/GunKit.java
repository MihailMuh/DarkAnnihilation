package com.warfare.darkannihilation.support;

import static com.warfare.darkannihilation.constants.Names.GUN_KIT;
import static com.warfare.darkannihilation.hub.Resources.getImages;

public class GunKit extends BaseSupport {
    public static boolean COLLECTED;

    public GunKit() {
        super(getImages().gunKit, GUN_KIT);

        COLLECTED = false;
    }
}
