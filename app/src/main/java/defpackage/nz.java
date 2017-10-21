package defpackage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.spongycastle.crypto.tls.CipherSuite;

public class nz implements Serializable, Comparable<nz> {
    static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final nz b = nz.a(new byte[0]);
    final byte[] c;
    transient int d;
    transient String e;

    nz(byte[] bArr) {
        this.c = bArr;
    }

    public static nz a(String str) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        nz nzVar = new nz(str.getBytes(op.a));
        nzVar.e = str;
        return nzVar;
    }

    public static nz a(byte... bArr) {
        if (bArr != null) {
            return new nz((byte[]) bArr.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    public static nz b(String str) {
        if (str == null) {
            throw new IllegalArgumentException("base64 == null");
        }
        byte[] a = nv.a(str);
        return a != null ? new nz(a) : null;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        int readInt = objectInputStream.readInt();
        if (objectInputStream == null) {
            throw new IllegalArgumentException("in == null");
        } else if (readInt < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + readInt);
        } else {
            byte[] bArr = new byte[readInt];
            int i = 0;
            while (i < readInt) {
                int read = objectInputStream.read(bArr, i, readInt - i);
                if (read == -1) {
                    throw new EOFException();
                }
                i += read;
            }
            nz nzVar = new nz(bArr);
            try {
                Field declaredField = nz.class.getDeclaredField("c");
                declaredField.setAccessible(true);
                declaredField.set(this, nzVar.c);
            } catch (NoSuchFieldException e) {
                throw new AssertionError();
            } catch (IllegalAccessException e2) {
                throw new AssertionError();
            }
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.c.length);
        objectOutputStream.write(this.c);
    }

    public byte a(int i) {
        return this.c[i];
    }

    public String a() {
        String str = this.e;
        if (str != null) {
            return str;
        }
        str = new String(this.c, op.a);
        this.e = str;
        return str;
    }

    public nz a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("beginIndex < 0");
        } else if (i2 > this.c.length) {
            throw new IllegalArgumentException("endIndex > length(" + this.c.length + ")");
        } else {
            int i3 = i2 - i;
            if (i3 < 0) {
                throw new IllegalArgumentException("endIndex < beginIndex");
            } else if (i == 0 && i2 == this.c.length) {
                return this;
            } else {
                Object obj = new byte[i3];
                System.arraycopy(this.c, i, obj, 0, i3);
                return new nz(obj);
            }
        }
    }

    void a(nw nwVar) {
        nwVar.b(this.c, 0, this.c.length);
    }

    public boolean a(int i, byte[] bArr, int i2, int i3) {
        return i >= 0 && i <= this.c.length - i3 && i2 >= 0 && i2 <= bArr.length - i3 && op.a(this.c, i, bArr, i2, i3);
    }

    public String b() {
        return nv.a(this.c);
    }

    public String c() {
        int i = 0;
        char[] cArr = new char[(this.c.length * 2)];
        for (byte b : this.c) {
            int i2 = i + 1;
            cArr[i] = a[(b >> 4) & 15];
            i = i2 + 1;
            cArr[i2] = a[b & 15];
        }
        return new String(cArr);
    }

    public /* synthetic */ int compareTo(Object obj) {
        nz nzVar = (nz) obj;
        int e = e();
        int e2 = nzVar.e();
        int min = Math.min(e, e2);
        for (int i = 0; i < min; i++) {
            int a = a(i) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            int a2 = nzVar.a(i) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            if (a != a2) {
                if (a >= a2) {
                    return 1;
                }
                return -1;
            }
        }
        if (e == e2) {
            return 0;
        }
        if (e >= e2) {
            return 1;
        }
        return -1;
    }

    public nz d() {
        int i = 0;
        while (i < this.c.length) {
            byte b = this.c[i];
            if (b < (byte) 65 || b > (byte) 90) {
                i++;
            } else {
                byte[] bArr = (byte[]) this.c.clone();
                bArr[i] = (byte) (b + 32);
                for (i++; i < bArr.length; i++) {
                    b = bArr[i];
                    if (b >= (byte) 65 && b <= (byte) 90) {
                        bArr[i] = (byte) (b + 32);
                    }
                }
                return new nz(bArr);
            }
        }
        return this;
    }

    public int e() {
        return this.c.length;
    }

    public boolean equals(Object obj) {
        return obj == this ? true : (obj instanceof nz) && ((nz) obj).e() == this.c.length && ((nz) obj).a(0, this.c, 0, this.c.length);
    }

    public byte[] f() {
        return (byte[]) this.c.clone();
    }

    public int hashCode() {
        int i = this.d;
        if (i != 0) {
            return i;
        }
        i = Arrays.hashCode(this.c);
        this.d = i;
        return i;
    }

    public String toString() {
        if (this.c.length == 0) {
            return "[size=0]";
        }
        String a = a();
        int length = a.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            if (i != 64) {
                int codePointAt = a.codePointAt(i2);
                if ((Character.isISOControl(codePointAt) && codePointAt != 10 && codePointAt != 13) || codePointAt == 65533) {
                    i2 = -1;
                    break;
                }
                i++;
                i2 += Character.charCount(codePointAt);
            } else {
                break;
            }
        }
        i2 = a.length();
        if (i2 == -1) {
            return this.c.length <= 64 ? "[hex=" + c() + "]" : "[size=" + this.c.length + " hex=" + a(0, 64).c() + "…]";
        } else {
            String replace = a.substring(0, i2).replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
            return i2 < a.length() ? "[size=" + this.c.length + " text=" + replace + "…]" : "[text=" + replace + "]";
        }
    }
}
