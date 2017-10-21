package com.ezeeworld.b4s.android.sdk.ibeacon;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.util.Locale;

public final class B4SBeacon implements Parcelable {
    public static final Creator<B4SBeacon> CREATOR = new Creator<B4SBeacon>() {
        public final B4SBeacon a(Parcel parcel) {
            return new B4SBeacon(parcel);
        }

        public final B4SBeacon[] a(int i) {
            return new B4SBeacon[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    private final IBeaconData a;
    private final Integer b;
    private final Integer c;
    private final Integer d;
    private final Integer e;

    protected B4SBeacon(Parcel parcel) {
        Integer num = null;
        this.a = (IBeaconData) parcel.readValue(IBeaconData.class.getClassLoader());
        this.b = parcel.readByte() == (byte) 0 ? null : Integer.valueOf(parcel.readInt());
        this.c = parcel.readByte() == (byte) 0 ? null : Integer.valueOf(parcel.readInt());
        this.d = parcel.readByte() == (byte) 0 ? null : Integer.valueOf(parcel.readInt());
        if (parcel.readByte() != (byte) 0) {
            num = Integer.valueOf(parcel.readInt());
        }
        this.e = num;
    }

    private B4SBeacon(IBeaconData iBeaconData, Integer num, Integer num2, Integer num3, Integer num4) {
        this.a = iBeaconData;
        this.b = num;
        this.c = num2;
        this.d = num3;
        this.e = num4;
    }

    public static B4SBeacon fromScanData(String str, String str2, byte[] bArr, int i) {
        Integer num = null;
        IBeaconData a = IBeaconData.a(str, str2, bArr, i);
        if (a == null || str == null || !str.startsWith("B4S")) {
            return null;
        }
        Integer num2;
        Integer num3;
        Integer num4;
        a.getID().getInnerName();
        if (str.length() <= 13 || !str.startsWith("B4S") || str.length() < 18) {
            num2 = null;
            num3 = null;
            num4 = null;
        } else {
            num2 = Integer.valueOf(Integer.parseInt(str.substring(14, 18), 16));
            if (str.length() >= 21) {
                num3 = Integer.valueOf(Integer.parseInt(str.substring(19, 21), 16));
                if (str.length() >= 24) {
                    num4 = Integer.valueOf(Integer.parseInt(str.substring(22, 25), 16));
                    if (str.length() >= 28) {
                        num = Integer.valueOf((Integer.parseInt(str.substring(26, 28), 16) & 14) >> 1);
                    }
                } else {
                    num4 = null;
                }
            } else {
                num3 = null;
                num4 = null;
            }
        }
        return new B4SBeacon(a, num2, num3, num4, num);
    }

    public static String getB4SName(String str) {
        return (str == null || str.length() <= 13 || !str.startsWith("B4S")) ? str : str.substring(0, 13);
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        return obj instanceof String ? this.a.getID().equals(obj) : obj instanceof B4SBeacon ? this.a.getID().equals(((B4SBeacon) obj).a.getID()) : obj instanceof IBeaconData ? this.a.getID().equals(((IBeaconData) obj).getID()) : obj instanceof IBeaconID ? this.a.getID().equals(obj) : false;
    }

    public final Integer getBatteryLevel() {
        return this.b;
    }

    public final IBeaconData getData() {
        return this.a;
    }

    public final Integer getEmittingFrequency() {
        return this.d;
    }

    public final Integer getEmittingLevel() {
        return this.c;
    }

    public final Integer getHardwareId() {
        return this.e;
    }

    public final IBeaconID getID() {
        return this.a == null ? null : this.a.getID();
    }

    public final String getInnerName() {
        return this.a == null ? null : this.a.getID().getInnerName();
    }

    public final String getMacAddress() {
        return this.a == null ? null : this.a.getID().getMacAddress();
    }

    public final int getMajor() {
        return this.a == null ? 0 : this.a.getID().getMajor();
    }

    public final int getMinor() {
        return this.a == null ? 0 : this.a.getID().getMinor();
    }

    public final String getUuid() {
        return this.a == null ? null : this.a.getID().getUuid();
    }

    public final int hashCode() {
        return this.a == null ? 0 : this.a.getID().hashCode();
    }

    public final String toString() {
        return String.format(Locale.US, "%1$s @ %2$.2fm", new Object[]{this.a.getID().toString(), Double.valueOf(this.a.getDistance())});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.a);
        if (this.b == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(this.b.intValue());
        }
        if (this.c == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(this.c.intValue());
        }
        if (this.d == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(this.d.intValue());
        }
        if (this.e == null) {
            parcel.writeByte((byte) 0);
            return;
        }
        parcel.writeByte((byte) 1);
        parcel.writeInt(this.e.intValue());
    }
}
