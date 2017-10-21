package org.spongycastle.util.io.pem;

public class PemHeader {
    private String name;
    private String value;

    public PemHeader(String str, String str2) {
        this.name = str;
        this.value = str2;
    }

    private int getHashCode(String str) {
        return str == null ? 1 : str.hashCode();
    }

    private boolean isEqual(String str, String str2) {
        return str == str2 ? true : (str == null || str2 == null) ? false : str.equals(str2);
    }

    public boolean equals(Object obj) {
        if (obj instanceof PemHeader) {
            PemHeader pemHeader = (PemHeader) obj;
            if (pemHeader == this || (isEqual(this.name, pemHeader.name) && isEqual(this.value, pemHeader.value))) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return getHashCode(this.name) + (getHashCode(this.value) * 31);
    }
}
