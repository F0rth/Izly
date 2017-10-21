package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import java.io.IOException;

public final class zzsm {
    private final byte[] buffer;
    private int zzbtZ;
    private int zzbua;
    private int zzbub;
    private int zzbuc;
    private int zzbud;
    private int zzbue = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    private int zzbuf;
    private int zzbug = 64;
    private int zzbuh = 67108864;

    private zzsm(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzbtZ = i;
        this.zzbua = i + i2;
        this.zzbuc = i;
    }

    public static zzsm zzD(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    private void zzJj() {
        this.zzbua += this.zzbub;
        int i = this.zzbua;
        if (i > this.zzbue) {
            this.zzbub = i - this.zzbue;
            this.zzbua -= this.zzbub;
            return;
        }
        this.zzbub = 0;
    }

    public static zzsm zza(byte[] bArr, int i, int i2) {
        return new zzsm(bArr, i, i2);
    }

    public static long zzan(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    public static int zzmp(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    public final int getPosition() {
        return this.zzbuc - this.zzbtZ;
    }

    public final byte[] readBytes() throws IOException {
        int zzJf = zzJf();
        if (zzJf > this.zzbua - this.zzbuc || zzJf <= 0) {
            return zzJf == 0 ? zzsx.zzbuD : zzmt(zzJf);
        } else {
            Object obj = new byte[zzJf];
            System.arraycopy(this.buffer, this.zzbuc, obj, 0, zzJf);
            this.zzbuc = zzJf + this.zzbuc;
            return obj;
        }
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzJi());
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzJh());
    }

    public final String readString() throws IOException {
        int zzJf = zzJf();
        if (zzJf > this.zzbua - this.zzbuc || zzJf <= 0) {
            return new String(zzmt(zzJf), "UTF-8");
        }
        String str = new String(this.buffer, this.zzbuc, zzJf, "UTF-8");
        this.zzbuc = zzJf + this.zzbuc;
        return str;
    }

    public final int zzIX() throws IOException {
        if (zzJl()) {
            this.zzbud = 0;
            return 0;
        }
        this.zzbud = zzJf();
        if (this.zzbud != 0) {
            return this.zzbud;
        }
        throw zzst.zzJv();
    }

    public final void zzIY() throws IOException {
        int zzIX;
        do {
            zzIX = zzIX();
            if (zzIX == 0) {
                return;
            }
        } while (zzmo(zzIX));
    }

    public final long zzIZ() throws IOException {
        return zzJg();
    }

    public final long zzJa() throws IOException {
        return zzJg();
    }

    public final int zzJb() throws IOException {
        return zzJf();
    }

    public final boolean zzJc() throws IOException {
        return zzJf() != 0;
    }

    public final int zzJd() throws IOException {
        return zzmp(zzJf());
    }

    public final long zzJe() throws IOException {
        return zzan(zzJg());
    }

    public final int zzJf() throws IOException {
        byte zzJm = zzJm();
        if (zzJm >= (byte) 0) {
            return zzJm;
        }
        int i = zzJm & TransportMediator.KEYCODE_MEDIA_PAUSE;
        byte zzJm2 = zzJm();
        if (zzJm2 >= (byte) 0) {
            return i | (zzJm2 << 7);
        }
        i |= (zzJm2 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 7;
        zzJm2 = zzJm();
        if (zzJm2 >= (byte) 0) {
            return i | (zzJm2 << 14);
        }
        i |= (zzJm2 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 14;
        zzJm2 = zzJm();
        if (zzJm2 >= (byte) 0) {
            return i | (zzJm2 << 21);
        }
        byte zzJm3 = zzJm();
        i = (i | ((zzJm2 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 21)) | (zzJm3 << 28);
        if (zzJm3 >= (byte) 0) {
            return i;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (zzJm() >= (byte) 0) {
                return i;
            }
        }
        throw zzst.zzJu();
    }

    public final long zzJg() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzJm = zzJm();
            j |= ((long) (zzJm & TransportMediator.KEYCODE_MEDIA_PAUSE)) << i;
            if ((zzJm & 128) == 0) {
                return j;
            }
        }
        throw zzst.zzJu();
    }

    public final int zzJh() throws IOException {
        return (((zzJm() & 255) | ((zzJm() & 255) << 8)) | ((zzJm() & 255) << 16)) | ((zzJm() & 255) << 24);
    }

    public final long zzJi() throws IOException {
        byte zzJm = zzJm();
        return ((((((((((long) zzJm()) & 255) << 8) | (((long) zzJm) & 255)) | ((((long) zzJm()) & 255) << 16)) | ((((long) zzJm()) & 255) << 24)) | ((((long) zzJm()) & 255) << 32)) | ((((long) zzJm()) & 255) << 40)) | ((((long) zzJm()) & 255) << 48)) | ((((long) zzJm()) & 255) << 56);
    }

    public final int zzJk() {
        if (this.zzbue == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
            return -1;
        }
        return this.zzbue - this.zzbuc;
    }

    public final boolean zzJl() {
        return this.zzbuc == this.zzbua;
    }

    public final byte zzJm() throws IOException {
        if (this.zzbuc == this.zzbua) {
            throw zzst.zzJs();
        }
        byte[] bArr = this.buffer;
        int i = this.zzbuc;
        this.zzbuc = i + 1;
        return bArr[i];
    }

    public final void zza(zzsu com_google_android_gms_internal_zzsu) throws IOException {
        int zzJf = zzJf();
        if (this.zzbuf >= this.zzbug) {
            throw zzst.zzJy();
        }
        zzJf = zzmq(zzJf);
        this.zzbuf++;
        com_google_android_gms_internal_zzsu.mergeFrom(this);
        zzmn(0);
        this.zzbuf--;
        zzmr(zzJf);
    }

    public final void zza(zzsu com_google_android_gms_internal_zzsu, int i) throws IOException {
        if (this.zzbuf >= this.zzbug) {
            throw zzst.zzJy();
        }
        this.zzbuf++;
        com_google_android_gms_internal_zzsu.mergeFrom(this);
        zzmn(zzsx.zzF(i, 4));
        this.zzbuf--;
    }

    public final void zzmn(int i) throws zzst {
        if (this.zzbud != i) {
            throw zzst.zzJw();
        }
    }

    public final boolean zzmo(int i) throws IOException {
        switch (zzsx.zzmI(i)) {
            case 0:
                zzJb();
                return true;
            case 1:
                zzJi();
                return true;
            case 2:
                zzmu(zzJf());
                return true;
            case 3:
                zzIY();
                zzmn(zzsx.zzF(zzsx.zzmJ(i), 4));
                return true;
            case 4:
                return false;
            case 5:
                zzJh();
                return true;
            default:
                throw zzst.zzJx();
        }
    }

    public final int zzmq(int i) throws zzst {
        if (i < 0) {
            throw zzst.zzJt();
        }
        int i2 = this.zzbuc + i;
        int i3 = this.zzbue;
        if (i2 > i3) {
            throw zzst.zzJs();
        }
        this.zzbue = i2;
        zzJj();
        return i3;
    }

    public final void zzmr(int i) {
        this.zzbue = i;
        zzJj();
    }

    public final void zzms(int i) {
        if (i > this.zzbuc - this.zzbtZ) {
            throw new IllegalArgumentException("Position " + i + " is beyond current " + (this.zzbuc - this.zzbtZ));
        } else if (i < 0) {
            throw new IllegalArgumentException("Bad position " + i);
        } else {
            this.zzbuc = this.zzbtZ + i;
        }
    }

    public final byte[] zzmt(int i) throws IOException {
        if (i < 0) {
            throw zzst.zzJt();
        } else if (this.zzbuc + i > this.zzbue) {
            zzmu(this.zzbue - this.zzbuc);
            throw zzst.zzJs();
        } else if (i <= this.zzbua - this.zzbuc) {
            Object obj = new byte[i];
            System.arraycopy(this.buffer, this.zzbuc, obj, 0, i);
            this.zzbuc += i;
            return obj;
        } else {
            throw zzst.zzJs();
        }
    }

    public final void zzmu(int i) throws IOException {
        if (i < 0) {
            throw zzst.zzJt();
        } else if (this.zzbuc + i > this.zzbue) {
            zzmu(this.zzbue - this.zzbuc);
            throw zzst.zzJs();
        } else if (i <= this.zzbua - this.zzbuc) {
            this.zzbuc += i;
        } else {
            throw zzst.zzJs();
        }
    }

    public final byte[] zzz(int i, int i2) {
        if (i2 == 0) {
            return zzsx.zzbuD;
        }
        Object obj = new byte[i2];
        System.arraycopy(this.buffer, this.zzbtZ + i, obj, 0, i2);
        return obj;
    }
}
