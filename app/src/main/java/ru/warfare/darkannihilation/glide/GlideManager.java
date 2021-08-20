package ru.warfare.darkannihilation.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import ru.warfare.darkannihilation.interfaces.BoxBitmap;
import ru.warfare.darkannihilation.interfaces.BoxDrawable;

import static ru.warfare.darkannihilation.systemd.service.Service.activity;

public class GlideManager {
    public void run(int id, int width, int height, BoxBitmap boxBitmap) {
        GlideApp.with(activity)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(id)
                .override(width, height)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  boxBitmap.unPack(resource);
                                  return true;
                              }
                          }
                ).submit();
    }

    public void run(int id, int len, BoxBitmap boxBitmap) {
        run(id, len, len, boxBitmap);
    }

    public void runCrop(int id, int width, int height, BoxBitmap boxBitmap) {
        GlideApp.with(activity)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(id)
                .centerCrop()
                .override(width, height)
                .listener(new RequestListener<Bitmap>() {
                              @Override
                              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                  return false;
                              }

                              @Override
                              public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                  boxBitmap.unPack(resource);
                                  return true;
                              }
                          }
                ).submit();
    }

    public void runDrawable(int id, int width, int height, BoxDrawable boxDrawable) {
        GlideApp.with(activity)
                .asDrawable()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(id)
                .override(width, height)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        boxDrawable.unPack(resource);
                        return false;
                    }

                }).submit();
    }
}