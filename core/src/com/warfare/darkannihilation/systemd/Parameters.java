package com.warfare.darkannihilation.systemd;

import java.util.HashMap;

public class Parameters {
    final HashMap<String, Object> map = new HashMap<>(5);

    public Parameters() {
    }

    public Parameters(String key, Object value) {
        put(key, value);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }
}
