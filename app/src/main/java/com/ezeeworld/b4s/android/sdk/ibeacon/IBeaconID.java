package com.ezeeworld.b4s.android.sdk.ibeacon;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import java.nio.ByteBuffer;
import java.util.UUID;

public final class IBeaconID implements Parcelable {
    public static final Creator<IBeaconID> CREATOR = new Creator<IBeaconID>() {
        public final IBeaconID a(Parcel parcel) {
            return new IBeaconID(parcel);
        }

        public final IBeaconID[] a(int i) {
            return new IBeaconID[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    private final String a;
    private final int b;
    private final int c;
    private final String d;
    private final String e;

    protected IBeaconID(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        this.d = parcel.readString();
        this.e = parcel.readString();
    }

    IBeaconID(String str, int i, int i2, String str2, String str3) {
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = str2;
        this.e = str3;
    }

    public static byte[] getUdidAsBytes(String str) {
        UUID fromString = UUID.fromString(str);
        ByteBuffer wrap = ByteBuffer.wrap(new byte[16]);
        wrap.putLong(fromString.getMostSignificantBits());
        wrap.putLong(fromString.getLeastSignificantBits());
        return wrap.array();
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return ((String) obj).equalsIgnoreCase(this.d);
            }
            if (obj instanceof IBeaconID) {
                IBeaconID iBeaconID = (IBeaconID) obj;
                if (iBeaconID.getMajor() == this.b && iBeaconID.getMinor() == this.c && iBeaconID.getUuid().equals(this.a)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final String getInnerName() {
        return this.d;
    }

    public final String getMacAddress() {
        return this.e;
    }

    public final int getMajor() {
        return this.b;
    }

    public final int getMinor() {
        return this.c;
    }

    public final String getUuid() {
        return this.a;
    }

    public final int hashCode() {
        return this.b * this.c;
    }

    public final String toString() {
        return this.d;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
        parcel.writeString(this.e);
    }
}
