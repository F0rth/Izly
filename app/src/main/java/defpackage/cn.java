package defpackage;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;

public enum cn {
    Food(2130838059, 2130838060, 2130837631, 2131231418, 1),
    HomeStuff(2130838063, 2130838064, 2130837634, 2131231420, 2),
    Hotel(2130838065, 2130838066, 2130837635, 2131231423, 3),
    Hoobies(2130838061, 2130838062, 2130837633, 2131231422, 4),
    Fashion(2130838067, 2130838068, 2130837636, 2131231417, 5),
    HealthCare(2130838071, 2130838072, 2130837638, 2131231419, 6),
    HomeServices(2130838073, 2130838074, 2130837639, 2131231421, 7),
    ProximityServices(2130838075, 2130838076, 2130837640, 2131231428, 8),
    BodyCare(2130838077, 2130838078, 2130837641, 2131231414, 9),
    MarketStore(2130838079, 2130838080, 2130837642, 2131231426, 10),
    Transport(2130838081, 2130838082, 2130837643, 2131231429, 11),
    Caritative(-1, -1, 2130837632, 2131231415, 12),
    All(2131231413, 0),
    Promo(-1, -1, 2130837630, 2131231427, -1);
    
    private static final SparseArray<cn> t = null;
    private static final ArrayList<cn> u = null;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;

    static {
        t = new SparseArray();
        u = new ArrayList();
        Iterator it = EnumSet.allOf(cn.class).iterator();
        while (it.hasNext()) {
            cn cnVar = (cn) it.next();
            t.put(cnVar.p, cnVar);
        }
        u.add(All);
        u.add(Promo);
        u.add(Food);
        u.add(Caritative);
        u.add(HomeStuff);
        u.add(Hotel);
        u.add(Hoobies);
        u.add(Fashion);
        u.add(HealthCare);
        u.add(HomeServices);
        u.add(ProximityServices);
        u.add(BodyCare);
        u.add(MarketStore);
        u.add(Transport);
    }

    private cn(int i, int i2) {
        this.q = -1;
        this.o = 2131231413;
        this.p = 0;
    }

    private cn(int i, int i2, int i3, int i4, int i5) {
        this.q = -1;
        this.r = i;
        this.s = i2;
        this.o = i4;
        this.p = i5;
        this.q = i3;
    }

    public static int a() {
        return u.size();
    }

    public static cn a(int i) {
        return (cn) t.get(i);
    }

    public static final cn b(int i) {
        return (cn) u.get(i);
    }
}
