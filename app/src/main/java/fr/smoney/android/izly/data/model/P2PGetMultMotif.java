package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class P2PGetMultMotif implements Parcelable {
    public static final Creator<P2PGetMultMotif> CREATOR = new Creator<P2PGetMultMotif>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new P2PGetMultMotif(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new P2PGetMultMotif[i];
        }
    };
    public String a;
    public int b;

    public P2PGetMultMotif() {
        this.b = -1;
        this.a = "";
    }

    public P2PGetMultMotif(Parcel parcel) {
        this.b = -1;
        this.a = parcel.readString();
        this.b = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
    }
}
