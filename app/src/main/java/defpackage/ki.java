package defpackage;

final class ki {
    public final String a;
    public final boolean b;

    ki(String str, boolean z) {
        this.a = str;
        this.b = z;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ki kiVar = (ki) obj;
            if (this.b != kiVar.b) {
                return false;
            }
            if (this.a != null) {
                if (!this.a.equals(kiVar.a)) {
                    return false;
                }
            } else if (kiVar.a != null) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = this.a != null ? this.a.hashCode() : 0;
        if (this.b) {
            i = 1;
        }
        return (hashCode * 31) + i;
    }
}
