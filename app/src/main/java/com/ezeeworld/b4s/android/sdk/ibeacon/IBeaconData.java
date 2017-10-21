package com.ezeeworld.b4s.android.sdk.ibeacon;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.Comparator;
import java.util.Locale;

public final class IBeaconData implements Parcelable {
    public static final Creator<IBeaconData> CREATOR = new Creator<IBeaconData>() {
        public final IBeaconData a(Parcel parcel) {
            return new IBeaconData(parcel);
        }

        public final IBeaconData[] a(int i) {
            return new IBeaconData[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public static final Comparator<IBeaconData> DISTANCE_COMPARATOR = new Comparator<IBeaconData>() {
        public final int a(IBeaconData iBeaconData, IBeaconData iBeaconData2) {
            return Double.compare(iBeaconData.e, iBeaconData2.e);
        }

        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return a((IBeaconData) obj, (IBeaconData) obj2);
        }
    };
    private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private IBeaconID b;
    private int c;
    private int d;
    private double e;

    private IBeaconData() {
    }

    protected IBeaconData(Parcel parcel) {
        this.b = (IBeaconID) parcel.readParcelable(IBeaconID.class.getClassLoader());
        this.d = parcel.readInt();
        this.c = parcel.readInt();
        this.e = parcel.readDouble();
    }

    static IBeaconData a(String str, String str2, byte[] bArr, int i) {
        int i2;
        int i3 = 2;
        while (i3 <= 5) {
            if ((bArr[i3 + 2] & 255) == 2 && (bArr[i3 + 3] & 255) == 21) {
                i2 = 1;
                break;
            }
            i3++;
        }
        i2 = 0;
        if (i2 == 0) {
            return null;
        }
        IBeaconData iBeaconData = new IBeaconData();
        byte b = bArr[i3 + 20];
        byte b2 = bArr[i3 + 21];
        byte b3 = bArr[i3 + 22];
        byte b4 = bArr[i3 + 23];
        iBeaconData.d = bArr[i3 + 24];
        iBeaconData.c = i;
        byte[] bArr2 = new byte[16];
        System.arraycopy(bArr, i3 + 4, bArr2, 0, 16);
        String a = a(bArr2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(a.substring(0, 8));
        stringBuilder.append("-");
        stringBuilder.append(a.substring(8, 12));
        stringBuilder.append("-");
        stringBuilder.append(a.substring(12, 16));
        stringBuilder.append("-");
        stringBuilder.append(a.substring(16, 20));
        stringBuilder.append("-");
        stringBuilder.append(a.substring(20, 32));
        String toUpperCase = (str == null || str.length() <= 13 || !str.startsWith("B4S")) ? str : str.substring(0, 13).toUpperCase(Locale.US);
        iBeaconData.b = new IBeaconID(stringBuilder.toString(), (b2 & 255) + ((b & 255) * 256), (b4 & 255) + ((b3 & 255) * 256), toUpperCase, str2);
        return iBeaconData;
    }

    private static String a(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            cArr[i * 2] = a[i2 >>> 4];
            cArr[(i * 2) + 1] = a[i2 & 15];
        }
        return new String(cArr);
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        return obj instanceof String ? this.b.equals(obj) : obj instanceof IBeaconData ? this.b.equals(((IBeaconData) obj).getID()) : obj instanceof IBeaconID ? this.b.equals(obj) : false;
    }

    public final double getDistance() {
        return this.e;
    }

    public final IBeaconID getID() {
        return this.b;
    }

    public final int getRssi() {
        return this.c;
    }

    public final int getTxPower() {
        return this.d;
    }

    public final int hashCode() {
        return this.b.hashCode();
    }

    public final void setDistance(double d) {
        this.e = d;
    }

    public final String toString() {
        return String.format(Locale.US, "%1$s @ %2$.2fm", new Object[]{this.b.toString(), Double.valueOf(this.e)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.b, i);
        parcel.writeInt(this.d);
        parcel.writeInt(this.c);
        parcel.writeDouble(this.e);
    }
}
