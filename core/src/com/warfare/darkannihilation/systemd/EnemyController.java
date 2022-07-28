package com.warfare.darkannihilation.systemd;

import static com.warfare.darkannihilation.constants.Constants.MIN_NUMBER_VADER;
import static com.warfare.darkannihilation.constants.Names.TRIPLE;
import static com.warfare.darkannihilation.constants.Names.VADER;
import static com.warfare.darkannihilation.hub.Resources.getPools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.warfare.darkannihilation.abstraction.sprite.Opponent;
import com.warfare.darkannihilation.support.HealthKit;
import com.warfare.darkannihilation.systemd.service.Processor;
import com.warfare.darkannihilation.systemd.service.Service;

public class EnemyController {
    private final Array<Opponent> empire;
    private HealthKit healthKit;

    public EnemyController(Array<Opponent> empire) {
        this.empire = empire;
    }

    public void setHealthKit(HealthKit healthKit) {
        this.healthKit = healthKit;
    }

    public void runHealthKit() {
        if (!healthKit.visible) healthKit.reset();
    }

    public void newTriple(int count) {
        for (int i = 0; i < count; i++) {
            getPools().triplePool.obtain();
        }
    }

    public void newVader(int count) {
        for (int i = 0; i < count; i++) {
            getPools().vaderPool.obtain();
        }
    }

    public void killTriple() {
        kill(TRIPLE);
    }

    public void killVader() {
        long numVaders = getNumberVaders();

        if (numVaders <= MIN_NUMBER_VADER) {
            newVader((int) (MIN_NUMBER_VADER - numVaders));
            killTriple();
        } else {
            kill(VADER);
        }
    }

    public void killEmpire() {
        Processor.postTask(() -> {
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < empire.size; i++) {
                    Opponent opponent = empire.get(i);
                    if (!opponent.shouldKill && (opponent.name == VADER || opponent.name == TRIPLE)) {
                        opponent.shouldKill = true;
                    }
                }
                Service.sleep(2000);
            }
        });
    }

    private void kill(int name) {
        for (int i = 0; i < empire.size; i++) {
            Opponent opponent = empire.get(i);
            if (opponent.name == name && !opponent.shouldKill) {
                opponent.shouldKill = true;
                return;
            }
        }
    }

    private int getNumberVaders() {
        int numVaders = 0;

        for (int i = 0; i < empire.size; i++) {
            Opponent opponent = empire.get(i);
            if (opponent.name == VADER && !opponent.shouldKill) {
                numVaders++;
            }
        }
        return numVaders;
    }

    public void addEnemy(Opponent opponent) {
        Gdx.app.postRunnable(() -> empire.add(opponent));
    }
    public void removeEnemy(Opponent opponent) {
        Gdx.app.postRunnable(() -> empire.removeValue(opponent, true));
    }
}
