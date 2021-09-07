package ru.warfare.darkannihilation.glide;

import static ru.warfare.darkannihilation.systemd.service.Service.activity;
import static ru.warfare.darkannihilation.systemd.service.Service.runOnUiThread;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;

import ru.warfare.darkannihilation.interfaces.BoxBitmap;
import ru.warfare.darkannihilation.interfaces.BoxDrawable;
import ru.warfare.darkannihilation.interfaces.GlideCallback;

public class GlideManager {
    public void run(Object id, int width, int height, BoxBitmap boxBitmap) {
        GlideApp.with(activity)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(id)
                .override(width, height)
                .listener((GlideCallback<Bitmap>) boxBitmap::unPack)
                .submit();
    }

    public void run(Object id, int len, BoxBitmap boxBitmap) {
        run(id, len, len, boxBitmap);
    }

    public void runCrop(Object id, int width, int height, BoxBitmap boxBitmap) {
        GlideApp.with(activity)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(id)
                .centerCrop()
                .override(width, height)
                .listener((GlideCallback<Bitmap>) boxBitmap::unPack)
                .submit();
    }

    public void runDrawable(Object id, int width, int height, BoxDrawable boxDrawable) {
        GlideApp.with(activity)
                .asDrawable()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(id)
                .override(width, height)
                .listener((GlideCallback<Drawable>) boxDrawable::unPack)
                .submit();
    }

    public void runGif(Object id, int width, int height, BoxDrawable boxDrawable, boolean crop) {
        RequestBuilder<GifDrawable> requestBuilder =
                GlideApp.with(activity)
                        .asGif()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .override(width, height)
                        .load(id)
                        .addListener((GlideCallback<GifDrawable>) resource -> runOnUiThread(() -> boxDrawable.unPack(resource)));
        
        if (crop) {
            requestBuilder.centerCrop().submit();
        } else {
            requestBuilder.submit();
        }
    }
}
