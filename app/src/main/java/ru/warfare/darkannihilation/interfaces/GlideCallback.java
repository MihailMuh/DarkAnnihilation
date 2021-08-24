package ru.warfare.darkannihilation.interfaces;

import static ru.warfare.darkannihilation.systemd.service.Py.print;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public interface GlideCallback<R> extends RequestListener<R> {
    @Override
    default boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource) {
        print("Load image " + e);
        return false;
    }

    @Override
    default boolean onResourceReady(R resource, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource) {
        onSuccessful(resource);
        return false;
    }

    void onSuccessful(R resource);
}
