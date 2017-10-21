package com.nhaarman.listviewanimations.util;

import android.support.annotation.NonNull;

public interface Insertable<T> {
    void add(int i, @NonNull T t);
}
