package com.google.android.gms.location.internal;

import android.annotation.SuppressLint;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

public class ParcelableGeofence implements SafeParcelable, Geofence {
    public static final zzo CREATOR = new zzo();
    private final int mVersionCode;
    private final String zzEY;
    private final int zzaNC;
    private final short zzaNE;
    private final double zzaNF;
    private final double zzaNG;
    private final float zzaNH;
    private final int zzaNI;
    private final int zzaNJ;
    private final long zzaOZ;

    public ParcelableGeofence(int i, String str, int i2, short s, double d, double d2, float f, long j, int i3, int i4) {
        zzek(str);
        zze(f);
        zza(d, d2);
        int zzhF = zzhF(i2);
        this.mVersionCode = i;
        this.zzaNE = s;
        this.zzEY = str;
        this.zzaNF = d;
        this.zzaNG = d2;
        this.zzaNH = f;
        this.zzaOZ = j;
        this.zzaNC = zzhF;
        this.zzaNI = i3;
        this.zzaNJ = i4;
    }

    public ParcelableGeofence(String str, int i, short s, double d, double d2, float f, long j, int i2, int i3) {
        this(1, str, i, s, d, d2, f, j, i2, i3);
    }

    private static void zza(double d, double d2) {
        if (d > 90.0d || d < -90.0d) {
            throw new IllegalArgumentException("invalid latitude: " + d);
        } else if (d2 > 180.0d || d2 < -180.0d) {
            throw new IllegalArgumentException("invalid longitude: " + d2);
        }
    }

    private static void zze(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("invalid radius: " + f);
        }
    }

    private static void zzek(String str) {
        if (str == null || str.length() > 100) {
            throw new IllegalArgumentException("requestId is null or too long: " + str);
        }
    }

    private static int zzhF(int i) {
        int i2 = i & 7;
        if (i2 != 0) {
            return i2;
        }
        throw new IllegalArgumentException("No supported transition specified: " + i);
    }

    @SuppressLint({"DefaultLocale"})
    private static String zzhG(int i) {
        switch (i) {
            case 1:
                return "CIRCLE";
            default:
                return null;
        }
    }

    public static ParcelableGeofence zzo(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        ParcelableGeofence zzeZ = CREATOR.zzeZ(obtain);
        obtain.recycle();
        return zzeZ;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || !(obj instanceof ParcelableGeofence)) {
                return false;
            }
            ParcelableGeofence parcelableGeofence = (ParcelableGeofence) obj;
            if (this.zzaNH != parcelableGeofence.zzaNH || this.zzaNF != parcelableGeofence.zzaNF || this.zzaNG != parcelableGeofence.zzaNG) {
                return false;
            }
            if (this.zzaNE != parcelableGeofence.zzaNE) {
                return false;
            }
        }
        return true;
    }

    public long getExpirationTime() {
        return this.zzaOZ;
    }

    public double getLatitude() {
        return this.zzaNF;
    }

    public double getLongitude() {
        return this.zzaNG;
    }

    public int getNotificationResponsiveness() {
        return this.zzaNI;
    }

    public String getRequestId() {
        return this.zzEY;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.zzaNF);
        int i = (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
        long doubleToLongBits2 = Double.doubleToLongBits(this.zzaNG);
        return ((((((((i + 31) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.zzaNH)) * 31) + this.zzaNE) * 31) + this.zzaNC;
    }

    public String toString() {
        return String.format(Locale.US, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", new Object[]{zzhG(this.zzaNE), this.zzEY, Integer.valueOf(this.zzaNC), Double.valueOf(this.zzaNF), Double.valueOf(this.zzaNG), Float.valueOf(this.zzaNH), Integer.valueOf(this.zzaNI / 1000), Integer.valueOf(this.zzaNJ), Long.valueOf(this.zzaOZ)});
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzo.zza(this, parcel, i);
    }

    public short zzyT() {
        return this.zzaNE;
    }

    public float zzyU() {
        return this.zzaNH;
    }

    public int zzyV() {
        return this.zzaNC;
    }

    public int zzyW() {
        return this.zzaNJ;
    }
}
