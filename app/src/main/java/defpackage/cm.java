package defpackage;

import android.util.SparseArray;
import java.util.EnumSet;
import java.util.Iterator;

public enum cm {
    France(2131231950, 33),
    Allemagne(2131231947, 49),
    Belgique(2131231948, 32),
    Espagne(2131231949, 34),
    Irlande(2131231951, 353),
    Italie(2131231952, 39),
    Luxembourg(2131231953, 352),
    Portugal(2131231954, 351),
    RoyaumeUni(2131231955, 44),
    Suisse(2131231956, 41);
    
    private static final SparseArray<cm> m = null;
    public int k;
    public int l;

    static {
        m = new SparseArray();
        Iterator it = EnumSet.allOf(cm.class).iterator();
        while (it.hasNext()) {
            cm cmVar = (cm) it.next();
            m.put(cmVar.k, cmVar);
        }
    }

    private cm(int i, int i2) {
        this.k = i2;
        this.l = i;
    }
}
