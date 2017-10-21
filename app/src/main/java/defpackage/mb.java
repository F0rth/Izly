package defpackage;

import android.content.Context;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

public class mb implements lw {
    private final Context a;
    private final File b;
    private final String c;
    private final File d;
    private ky e = new ky(this.d);
    private File f = new File(this.b, this.c);

    public mb(Context context, File file, String str, String str2) throws IOException {
        this.a = context;
        this.b = file;
        this.c = str2;
        this.d = new File(this.b, str);
        if (!this.f.exists()) {
            this.f.mkdirs();
        }
    }

    public final int a() {
        return this.e.a();
    }

    public OutputStream a(File file) throws IOException {
        return new FileOutputStream(file);
    }

    public final List<File> a(int i) {
        List<File> arrayList = new ArrayList();
        for (Object add : this.f.listFiles()) {
            arrayList.add(add);
            if (arrayList.size() > 0) {
                break;
            }
        }
        return arrayList;
    }

    public final void a(String str) throws IOException {
        Throwable th;
        Throwable th2;
        this.e.close();
        File file = this.d;
        File file2 = new File(this.f, str);
        Closeable fileInputStream;
        Closeable a;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                a = a(file2);
                try {
                    kp.a((InputStream) fileInputStream, (OutputStream) a, new byte[PKIFailureInfo.badRecipientNonce]);
                    kp.a(fileInputStream, "Failed to close file input stream");
                    kp.a(a, "Failed to close output stream");
                    file.delete();
                    this.e = new ky(this.d);
                } catch (Throwable th3) {
                    th = th3;
                    kp.a(fileInputStream, "Failed to close file input stream");
                    kp.a(a, "Failed to close output stream");
                    file.delete();
                    throw th;
                }
            } catch (Throwable th4) {
                th2 = th4;
                a = null;
                th = th2;
                kp.a(fileInputStream, "Failed to close file input stream");
                kp.a(a, "Failed to close output stream");
                file.delete();
                throw th;
            }
        } catch (Throwable th5) {
            a = null;
            th2 = th5;
            fileInputStream = null;
            th = th2;
            kp.a(fileInputStream, "Failed to close file input stream");
            kp.a(a, "Failed to close output stream");
            file.delete();
            throw th;
        }
    }

    public final void a(List<File> list) {
        for (File file : list) {
            kp.a(this.a, String.format("deleting sent analytics file %s", new Object[]{file.getName()}));
            file.delete();
        }
    }

    public final void a(byte[] bArr) throws IOException {
        this.e.a(bArr);
    }

    public final boolean a(int i, int i2) {
        return (this.e.a() + 4) + i <= i2;
    }

    public final boolean b() {
        return this.e.b();
    }

    public final List<File> c() {
        return Arrays.asList(this.f.listFiles());
    }

    public final void d() {
        try {
            this.e.close();
        } catch (IOException e) {
        }
        this.d.delete();
    }
}
