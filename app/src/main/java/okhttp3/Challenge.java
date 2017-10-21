package okhttp3;

import okhttp3.internal.Util;

public final class Challenge {
    private final String realm;
    private final String scheme;

    public Challenge(String str, String str2) {
        this.scheme = str;
        this.realm = str2;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof Challenge) && Util.equal(this.scheme, ((Challenge) obj).scheme) && Util.equal(this.realm, ((Challenge) obj).realm);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = this.realm != null ? this.realm.hashCode() : 0;
        if (this.scheme != null) {
            i = this.scheme.hashCode();
        }
        return ((hashCode + 899) * 31) + i;
    }

    public final String realm() {
        return this.realm;
    }

    public final String scheme() {
        return this.scheme;
    }

    public final String toString() {
        return this.scheme + " realm=\"" + this.realm + "\"";
    }
}
