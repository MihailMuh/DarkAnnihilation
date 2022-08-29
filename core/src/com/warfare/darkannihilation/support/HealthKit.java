package com.warfare.darkannihilation.support;

import static com.warfare.darkannihilation.constants.Names.HEALTH_KIT;
import static com.warfare.darkannihilation.hub.Resources.getImages;

public class HealthKit extends BaseSupport {
    public HealthKit() {
        super(getImages().healthKitImg, HEALTH_KIT);
    }
}
