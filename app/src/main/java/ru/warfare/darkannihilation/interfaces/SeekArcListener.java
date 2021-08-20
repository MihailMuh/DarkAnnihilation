package ru.warfare.darkannihilation.interfaces;

import com.triggertrap.seekarc.SeekArc;

public interface SeekArcListener extends SeekArc.OnSeekArcChangeListener {
    @Override
    default void onProgressChanged(SeekArc seekArc, int i, boolean b) {

    }

    @Override
    default void onStartTrackingTouch(SeekArc seekArc) {

    }

    @Override
    default void onStopTrackingTouch(SeekArc seekArc) {

    }
}
