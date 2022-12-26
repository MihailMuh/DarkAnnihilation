package ru.warfare.darkannihilation.interfaces;

import androidx.annotation.Nullable;

import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

public interface SpinnerListener extends OnSpinnerItemSelectedListener<Object> {
    void itemSelected(int id);

    @Override
    default void onItemSelected(int i, @Nullable Object r, int i1, Object t1) {
        itemSelected(i1);
    }
}
