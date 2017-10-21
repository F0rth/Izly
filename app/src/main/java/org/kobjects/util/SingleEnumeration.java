package org.kobjects.util;

import java.util.Enumeration;

public class SingleEnumeration implements Enumeration {
    Object object;

    public SingleEnumeration(Object obj) {
        this.object = obj;
    }

    public boolean hasMoreElements() {
        return this.object != null;
    }

    public Object nextElement() {
        Object obj = this.object;
        this.object = null;
        return obj;
    }
}
