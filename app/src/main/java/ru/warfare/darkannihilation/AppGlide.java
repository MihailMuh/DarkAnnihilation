package ru.warfare.darkannihilation;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public final class AppGlide extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}