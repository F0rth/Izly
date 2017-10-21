package defpackage;

import java.util.Comparator;

final class ji$2 implements Comparator<ji$a> {
    ji$2() {
    }

    public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
        ji$a ji_a = (ji$a) obj;
        ji$a ji_a2 = (ji$a) obj2;
        if (ji_a.b >= ji_a2.b) {
            if (ji_a.b > ji_a2.b || ji_a.c < ji_a2.c) {
                return 1;
            }
            if (ji_a.c <= ji_a2.c) {
                return 0;
            }
        }
        return -1;
    }
}
