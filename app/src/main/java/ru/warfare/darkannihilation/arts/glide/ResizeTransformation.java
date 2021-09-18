package ru.warfare.darkannihilation.arts.glide;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ResizeTransformation extends BitmapTransformation {
    private static final String ID = "ru.warfare.darkannihilation.arts.glide";
    private static final byte[] ID_BYTES = ID.getBytes(StandardCharsets.UTF_8);

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return Bitmap.createScaledBitmap(toTransform, outWidth, outHeight, true);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ResizeTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
