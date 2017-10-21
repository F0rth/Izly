package com.ezeeworld.b4s.android.sdk.server;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Beacon implements Parcelable {
    public static final Creator<Beacon> CREATOR = new Creator<Beacon>() {
        public final Beacon a(Parcel parcel) {
            return new Beacon(parcel);
        }

        public final Beacon[] a(int i) {
            return new Beacon[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public boolean bBattUpdate;
    public int nBeaconRangeIn;
    public int nBeaconRangeOut;
    public int nReqTxInterval;
    public int nReqTxPower;
    public String sCategoryId;
    public String sClientId;
    public String sClientRef;
    public String sGroupId;
    public String sInnerName;
    public String sName;
    public String sPushData;
    public String sPushText;
    public String sShopId;

    protected Beacon(Parcel parcel) {
        boolean z = true;
        this.sInnerName = parcel.readString();
        this.sName = parcel.readString();
        this.sClientId = parcel.readString();
        this.sClientRef = parcel.readString();
        this.sPushText = parcel.readString();
        this.sPushData = parcel.readString();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.bBattUpdate = z;
        this.nBeaconRangeIn = parcel.readInt();
        this.nBeaconRangeOut = parcel.readInt();
        this.sShopId = parcel.readString();
        this.sGroupId = parcel.readString();
        this.sCategoryId = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return this.sName;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sInnerName);
        parcel.writeString(this.sName);
        parcel.writeString(this.sClientId);
        parcel.writeString(this.sClientRef);
        parcel.writeString(this.sPushText);
        parcel.writeString(this.sPushData);
        parcel.writeInt(this.bBattUpdate ? 1 : 0);
        parcel.writeInt(this.nBeaconRangeIn);
        parcel.writeInt(this.nBeaconRangeOut);
        parcel.writeString(this.sShopId);
        parcel.writeString(this.sGroupId);
        parcel.writeString(this.sCategoryId);
    }
}
