package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class RequestData implements Parcelable {
    public static final Creator<RequestData> CREATOR = new Creator<RequestData>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new RequestData(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new RequestData[i];
        }
    };
    public int a;
    public int b;
    public boolean c;

    public RequestData(int i, int i2, boolean z) {
        this.a = i;
        this.b = i2;
        this.c = z;
    }

    public RequestData(Parcel parcel) {
        boolean z = true;
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.c = z;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            RequestData requestData = (RequestData) obj;
            if (this.a != requestData.a) {
                return false;
            }
            if (this.b != requestData.b) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return (this.a * 31) + this.b;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c ? 1 : 0);
    }
}
