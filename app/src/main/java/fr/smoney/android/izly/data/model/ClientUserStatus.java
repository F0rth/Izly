package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ClientUserStatus implements Parcelable {
    public static final Creator<ClientUserStatus> CREATOR = new Creator<ClientUserStatus>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ClientUserStatus(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ClientUserStatus[i];
        }
    };
    public int a;
    public double b;
    public double c;
    public double d;
    public double e;
    public double f;
    public double g;

    private ClientUserStatus(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readDouble();
        this.c = parcel.readDouble();
        this.d = parcel.readDouble();
        this.e = parcel.readDouble();
        this.f = parcel.readDouble();
        this.g = parcel.readDouble();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeDouble(this.b);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.d);
        parcel.writeDouble(this.e);
        parcel.writeDouble(this.f);
        parcel.writeDouble(this.g);
    }
}
