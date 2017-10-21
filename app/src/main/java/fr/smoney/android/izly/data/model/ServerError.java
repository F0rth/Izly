package fr.smoney.android.izly.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ServerError implements Parcelable {
    public static final Creator<ServerError> CREATOR = new Creator<ServerError>() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new ServerError(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new ServerError[i];
        }
    };
    public String a;
    public int b;
    public String c;
    public String d;
    public int e;

    private ServerError(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readInt();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.readInt();
    }

    public static int a(String str) {
        return str.compareTo("invalid_request") == 0 ? 572 : str.compareTo("invalid_client") == 0 ? 573 : str.compareTo("unauthorized_client") == 0 ? 574 : str.compareTo("access_denied") == 0 ? 575 : str.compareTo("invalid_grant") == 0 ? 576 : str.compareTo("unsupported_grant_type") == 0 ? 577 : str.compareTo("unsupported_response_type") == 0 ? 578 : str.compareTo("invalid_scope") == 0 ? 579 : str.compareTo("server_error") == 0 ? 580 : str.compareTo("temporarily_unavailable") == 0 ? 581 : 582;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeInt(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
    }
}
