package com.warfare.darkannihilation.abstraction;

import com.warfare.darkannihilation.hub.ResourcesManager;

public abstract class BaseHub {
    protected final ResourcesManager resourcesManager;

    public BaseHub(ResourcesManager resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    public abstract void boot();
}
