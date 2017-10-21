package org.spongycastle.util;

public interface Selector extends Cloneable {
    Object clone();

    boolean match(Object obj);
}
