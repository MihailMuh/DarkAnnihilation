package com.warfare.darkannihilation.systemd.menu;

import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.warfare.darkannihilation.Button;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.screens.FullScreen;
import com.warfare.darkannihilation.utils.AnimationG;
import com.warfare.darkannihilation.utils.GifDecoder;

public class Scene implements Disposable {
    private final AnimationG<TextureRegion> menuScreen;
    private final FullScreen screen;
    private final MenuClickListener clickListener;

    private final Button[] buttons = new Button[4];

    public Scene() {
        menuScreen = GifDecoder.loadGIFAnimation("menu/menu_screen.gif");
        screen = new FullScreen(menuScreen);

        FontHub.resizeFont(FontHub.fontButtons,
                ImageHub.storage.buttonPress.originalWidth - 150,
                "Quit", "Start", "Top Score", "Settings");
        int step = 50;
        buttons[0] = new Button("Top Score", HALF_SCREEN_WIDTH + step / 2f, 10);
        buttons[1] = new Button("Start", buttons[0].x - buttons[0].width - step, 10);
        buttons[2] = new Button("Settings", buttons[0].right() + step, 10);
        buttons[3] = new Button("Quit", buttons[1].x - buttons[0].width - step, 10);

        clickListener = new MenuClickListener(buttons);
    }

    public void update() {
    }

    public void render() {
        screen.render();
        for (Button button : buttons) {
            button.render();
        }
    }

    @Override
    public void dispose() {
        menuScreen.dispose();
        clickListener.dispose();
    }
}
