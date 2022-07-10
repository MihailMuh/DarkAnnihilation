package com.warfare.darkannihilation.scenes.versus;

import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.warfare.darkannihilation.screens.Screen;
import com.warfare.darkannihilation.utils.Image;

class VersusScreen extends Screen {
    public VersusScreen(Image image) {
        super(image, SCREEN_HEIGHT, SCREEN_HEIGHT);

        x = HALF_SCREEN_WIDTH - halfWidth;
    }
}
