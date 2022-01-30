package com.warfare.darkannihilation.hub;

import com.warfare.darkannihilation.utils.AssetManagerSuper;

public abstract class BaseHub {
    protected final AssetManagerSuper assetManager;

    public BaseHub(AssetManagerSuper assetManager) {
        this.assetManager = assetManager;
    }

    public void boot() {

    }

    public void lazyLoading() {
        
    }
}
