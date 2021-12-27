package com.warfare.darkannihilation.systemd.game;

import static com.warfare.darkannihilation.hub.FontHub.canisMinorHuge;
import static com.warfare.darkannihilation.hub.FontHub.countdownFont;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_WIDTH;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import com.warfare.darkannihilation.FontWrap;
import com.warfare.darkannihilation.Player;
import com.warfare.darkannihilation.SceneTop;
import com.warfare.darkannihilation.abstraction.BaseScreen;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.systemd.MainGameManager;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;

public class Countdown extends SceneTop {
    private final Player player;
    private String text = "3";
    private float textX, textY;

    public Countdown(MainGameManager mainGameManager, Player player, BaseScreen screen) {
        super(mainGameManager);

        this.player = player;
        this.screen = screen;
        countdownFont = new FontWrap(canisMinorHuge, FontHub.resizeFont(canisMinorHuge, SCREEN_WIDTH - 400, "SHOOT!"));
    }

    private void setTextXY() {
        textX = HALF_SCREEN_WIDTH - countdownFont.getTextWidth(text) / 2f;
        textY = HALF_SCREEN_HEIGHT + countdownFont.getTextHeight(text) / 2f;
        Service.sleep(1000);
    }

    @Override
    public void resume() {
        Processor.post(() -> {
            for (int i = 3; i >= 1; i--) {
                text = String.valueOf(i);
                setTextXY();
            }
            text = "SHOOT!";
            setTextXY();
            stop();
        });
    }

    @Override
    public void update() {
        float moveAll = player.speedX / 3;

        screen.x -= moveAll;

        player.update();
    }

    @Override
    public void render() {
        countdownFont.draw(textX, textY, text);
    }
}
