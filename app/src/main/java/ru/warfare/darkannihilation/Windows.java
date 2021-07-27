package ru.warfare.darkannihilation;

import android.content.Context;
import android.graphics.Point;

import static android.content.Context.WINDOW_SERVICE;

public class Windows {
    private static final Point size = new Point();
    private static float resizeLayout;
    private static float resizeK;

    public static void init(Context context) {
        (((android.view.WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay())
                .getRealSize(size);

        resizeLayout = (size.y / 720f) * context.getResources().getDisplayMetrics().density;
        resizeK = size.x / 1920f;
    }

    public static float resizeK() {
        return resizeK;
    }

    public static float resizeLayout() {
        return resizeLayout;
    }

    public static int screenWidth() {
        return size.x;
    }

    public static int screenHeight() {
        return size.y;
    }
}
