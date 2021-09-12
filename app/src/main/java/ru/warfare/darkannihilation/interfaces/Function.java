package ru.warfare.darkannihilation.interfaces;

import static ru.warfare.darkannihilation.systemd.service.Py.print;

public interface Function {
    void body();
    default void run() {
        try {
            body();
        } catch (Exception e) {
            print("Function: " + e);
        }
    }
}
