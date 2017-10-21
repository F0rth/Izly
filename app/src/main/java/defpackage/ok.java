package defpackage;

final class ok {
    static oj a;
    static long b;

    private ok() {
    }

    static oj a() {
        synchronized (ok.class) {
            try {
                if (a != null) {
                    oj ojVar = a;
                    a = ojVar.f;
                    oj ojVar2 = null;
                    ojVar.f = null;
                    b -= 8192;
                    return ojVar;
                }
            } finally {
                while (true) {
                    break;
                }
                ojVar2 = ok.class;
            }
        }
        return new oj();
    }

    static void a(oj ojVar) {
        if (ojVar.f != null || ojVar.g != null) {
            throw new IllegalArgumentException();
        } else if (!ojVar.d) {
            synchronized (ok.class) {
                try {
                    Object obj = ((b + 8192) > 65536 ? 1 : ((b + 8192) == 65536 ? 0 : -1));
                    if (obj > null) {
                        return;
                    }
                    b += 8192;
                    ojVar.f = a;
                    ojVar.c = 0;
                    ojVar.b = 0;
                    a = ojVar;
                } finally {
                    Class cls = ok.class;
                }
            }
        }
    }
}
